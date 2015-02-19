package com.html5parser.TokenizerStates;

import com.html5parser.SimplestTreeParser.ParserStacks;
import com.html5parser.SimplestTreeParser.Token;
import com.html5parser.SimplestTreeParser.Token.TokenType;
import com.html5parser.SimplestTreeParser.TokenizerContext;
import com.html5parser.SimplestTreeParser.TreeConstructor;

public class Self_closing_start_tag_state implements State {

	public void process(TokenizerContext context) {
		int currentChar = context.getCurrentChar();
		Token currentToken = context.getCurrentToken();
		TreeConstructor treeConstructor = context.getTreeConstructor();
		switch (currentChar) {
		// U+003E GREATER-THAN SIGN (>)
		// Set the self-closing flag of the current tag token. 
		// Switch to the data state. Emit the current tag token.
		case 0x003E:
			context.setState(new Data_state());
			treeConstructor.processToken(currentToken);
			break;

		// U+003C LESS-THAN SIGN (<)
		// Switch to the character reference in data state.
		case 0x003C:
			context.setState(new Tag_open_state());
			break;

		// U+0000 NULL
		// Parse error. Emit the current input character as a character token.
		case 0x0000:
			ParserStacks.parseErrors.push("NULL Character encountered.");
			treeConstructor.processToken(new Token(TokenType.character, String
					.valueOf(currentChar)));
			break;

		// EOF
		// Parse error. Switch to the data state. Reconsume the EOF character.
		case -1:
			context.setState(new Data_state());
			ParserStacks.parseErrors.push("EOF encountered.");
			break;

		// Anything else
		// Parse error. Switch to the before attribute name state. Reconsume the character.
		default:
			ParserStacks.parseErrors.push(Character.toString((char) currentChar) + " (" +  String.valueOf(currentChar) + ") Invalid character encountered.");
			//context.setState(new Before_attribute_name_state());
			
			context.setState(new Error_state());
			break;
		}
	}

}
