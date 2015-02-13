package com.html5parser.TokenizerStates;

import com.html5parser.SimplestTreeParser.ParserStacks;
import com.html5parser.SimplestTreeParser.Token;
import com.html5parser.SimplestTreeParser.TreeConstructor;

public class Tag_name_state implements State {

	private State nextState;

	public Token process(int currentChar, TreeConstructor treeConstructor, Token currentToken) {

		// U+0041 LATIN CAPITAL LETTER A through to U+005A LATIN CAPITAL LETTER
		// Z
		// Append the lowercase version of the current input character
		// (add 0x0020 to the character's code point) to the current tag token's
		// tag name.
		if (currentChar > 64 && currentChar < 91) {
			// Transfrom to the lowercase ASCII
			currentChar += 20;
		}

		switch (currentChar) {
		// "tab" (U+0009) "LF" (U+000A) "FF" (U+000C) U+0020 SPACE
		// Switch to the before attribute name state.
		case 0x0009: // TAB
		case 0x000A: // LF
		case 0x000C: // FF
		case 0x0020: // SPACE
			// nextState = new Before_attribute_name_state();
			break;

		// "/" (U+002F)
		// Switch to the self-closing start tag state.
		case 0x002F: // /
			// nextState = new Self_closing_start_tag_state();
			break;

		// U+003E GREATER-THAN SIGN (>)
		// Switch to the data state. Emit the current tag token
		case 0x003E: // >
			nextState = new Data_state();
			treeConstructor.ProcessToken(currentToken);
			break;

		// U+0000 NULL
		// Parse error. Append a U+FFFD REPLACEMENT CHARACTER character to the
		// current tag token's tag name.
		case 0x0000:
			ParserStacks.parseErrors.push("Invalid character - NULL");
			currentToken.setValue(currentToken.getValue()
					+ String.valueOf(0xFFFD));
			break;

		// EOF
		// Parse error. Switch to the data state. Reconsume the EOF character.
		case -1:
			ParserStacks.parseErrors.push("Invalid character - EOF");
			nextState = new Data_state();
			nextState.process(-1, treeConstructor, currentToken);
			break;

		// Anything else
		// Append the current input character to the current tag token's tag
		// name.
		default:
			currentToken.setValue(currentToken.getValue().concat(
					String.valueOf(Character.toChars(currentChar))));
			nextState = new Tag_name_state();
			break;
		}

		return currentToken;
		
	}

	public State nextState() {
		return nextState;
	}

}