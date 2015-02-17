package com.html5parser.TokenizerStates;

import com.html5parser.SimplestTreeParser.ParserStacks;
import com.html5parser.SimplestTreeParser.Token;
import com.html5parser.SimplestTreeParser.Token.TokenType;
import com.html5parser.SimplestTreeParser.TokenizerContext;
import com.html5parser.SimplestTreeParser.TreeConstructor;

public class Data_state implements State {

	public void process(TokenizerContext context) {
		int currentChar = context.getCurrentChar();
		TreeConstructor treeConstructor = context.getTreeConstructor();
		switch (currentChar) {
		// U+0026 AMPERSAND (&)
		// Switch to the character reference in data state.
		case 0x0026:
			// nextState = new Character_reference_in_data_state();
			break;

		// U+0026 AMPERSAND (<)
		// Switch to the character reference in data state.
		case 0x003C:
			context.setState(new Tag_open_state());
			break;

		// U+0000 NULL
		// Parse error. Emit the current input character as a character token.
		case 0x0000:
			ParserStacks.parseErrors.push("NULL Character encountered");
			treeConstructor.processToken(new Token(TokenType.character, String
					.valueOf(currentChar)));
			break;

		// EOF
		// Emit an end-of-file token.
		case -1:
			treeConstructor
					.processToken(new Token(TokenType.end_of_file, null));
			break;

		// Anything else
		// Emit the current input character as a character token.
		default:
			treeConstructor.processToken(new Token(TokenType.character, String
					.valueOf(currentChar)));
			break;
		}
	}

}
