package com.html5parser.TokenizerStates;

import com.html5parser.SimplestTreeParser.ParserStacks;
import com.html5parser.SimplestTreeParser.Token;
import com.html5parser.SimplestTreeParser.TokenizerContext;
import com.html5parser.SimplestTreeParser.TreeConstructor;
import com.html5parser.SimplestTreeParser.Token.TokenType;

public class RCDATA_end_tag_name_state implements State {

	public void process(TokenizerContext context) {
		int currentChar = context.getCurrentChar();
		TreeConstructor treeConstructor = context.getTreeConstructor();
		Token currentToken = context.getCurrentToken();
		// U+0041 LATIN CAPITAL LETTER A through to U+005A LATIN CAPITAL LETTER
		// Z
		// Append the lowercase version of the current input character (add
		// 0x0020 to the character's code point) to the current tag token's tag
		// name. Append the current input character to the temporary buffer.
		if (currentChar > 64 && currentChar < 91) {
			// Transfrom to the lowercase ASCII
			currentChar += 20;
		}

		// U+0061 LATIN SMALL LETTER A through to U+007A LATIN SMALL LETTER Z
		// Append the current input character to the current tag token's tag
		// name. Append the current input character to the temporary buffer.
		if (currentChar > 96 && currentChar < 123) {
			currentToken.setValue(currentToken.getValue().concat(
					String.valueOf(Character.toChars(currentChar))));
		} else {
			switch (currentChar) {

			// "tab" (U+0009) "LF" (U+000A) "FF" (U+000C) U+0020 SPACE
			// If the current end tag token is an appropriate end tag token,
			// then switch to the before attribute name state. Otherwise, treat
			// it as per the "anything else" entry below.
			case 0x0009: // TAB
			case 0x000A: // LF
			case 0x000C: // FF
			case 0x0020: // SPACE
				// nextState = new Before_attribute_name_state();
				ParserStacks.parseErrors.push(Character
						.toString((char) currentChar)
						+ " ("
						+ String.valueOf(currentChar)
						+ ") Invalid character encountered.");
				context.setState(new Error_state());
				break;

			// "/" (U+002F)
			// If the current end tag token is an appropriate end tag token,
			// then switch to the self-closing start tag state. Otherwise, treat
			// it as per the "anything else" entry below.
			case 0x002F:
				context.setState(new Self_closing_start_tag_state());
				break;

			// U+003E GREATER-THAN SIGN (>)
			// If the current end tag token is an appropriate end tag token,
			// then switch to the data state and emit the current tag token.
			// Otherwise, treat it as per the "anything else" entry below.
			case 0x0021:
				context.setState(new Data_state());
				treeConstructor.processToken(currentToken);
				break;

			// Anything else
			// Switch to the RCDATA state. Emit a U+003C LESS-THAN SIGN
			// character token, a U+002F SOLIDUS character token, and a
			// character token for each of the characters in the temporary
			// buffer (in the order they were added to the buffer). Reconsume
			// the current input character.
			default:
				context.setState(new RCDATA_state());
				treeConstructor.processToken(new Token(TokenType.character, String
						.valueOf(0x003C)));
				treeConstructor.processToken(new Token(TokenType.character, String
						.valueOf(0x002F)));				
				break;
			}
		}
	}

}
