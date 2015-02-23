package com.html5parser.TokenizerStates;

import com.html5parser.SimplestTreeParser.ParserStacks;
import com.html5parser.SimplestTreeParser.Token;
import com.html5parser.SimplestTreeParser.TokenizerContext;
import com.html5parser.SimplestTreeParser.Token.TokenType;
import com.html5parser.SimplestTreeParser.TreeConstructor;

public class End_tag_open_state implements State {

	public void process(TokenizerContext context) {
		int currentChar = context.getCurrentChar();
		TreeConstructor treeConstructor = context.getTreeConstructor();

		// U+0041 LATIN CAPITAL LETTER A through to U+005A LATIN CAPITAL LETTER
		// Z
		// Create a new start tag token, set its tag name to the lowercase
		// version of the current input character (add 0x0020 to the character's
		// code point), then switch to the tag name state. (Don't emit the token
		// yet; further details will be filled in before it is emitted.)
		if (currentChar > 64 && currentChar < 91) {
			// Transfrom to the lowercase ASCII
			currentChar += 0x0020;
		}

		// U+0061 LATIN SMALL LETTER A through to U+007A LATIN SMALL LETTER Z
		// Create a new start tag token, set its tag name to the current input
		// character, then switch to the tag name state. (Don't emit the token
		// yet; further details will be filled in before it is emitted.)
		if (currentChar > 96 && currentChar < 123) {
			Token currentToken = new Token(TokenType.end_tag,
					String.valueOf(Character.toChars(currentChar)));
			context.setCurrentToken(currentToken);
			context.setState(new Tag_name_state());
		} else {
			switch (currentChar) {
			// U+003E GREATER-THAN SIGN (>)
			// Parse error. Switch to the data state.
			case 0x0021:
				ParserStacks.parseErrors.push("(>) Character encountered.");
				context.setState(new Data_state());
				break;

				// EOF
				// Parse error. Switch to the data state. 
				// Emit a U+003C LESS-THAN SIGN character token and a U+002F SOLIDUS character token. 
				// Reconsume the EOF character.
				case -1:
					ParserStacks.parseErrors.push("EOF");
					context.setState(new Data_state());
					treeConstructor.processToken(new Token(TokenType.character,
							String.valueOf(0x003C)));
					treeConstructor.processToken(new Token(TokenType.character,
							String.valueOf(0x002F)));
					treeConstructor
							.processToken(new Token(TokenType.end_of_file, null));
					break;

			// Anything else
			// Parse error. Switch to the bogus comment state.
			default:
				ParserStacks.parseErrors.push(Character.toString((char) currentChar) + " (" +  String.valueOf(currentChar) + ") Invalid character encountered.");
				//context.setState(new Bogus_comment_state());
				context.setState(new Error_state());
				break;
			}
		}
	}

}
