package com.html5parser.TokenizerStates;

import com.html5parser.SimplestTreeParser.ParserStacks;
import com.html5parser.SimplestTreeParser.Token;
import com.html5parser.SimplestTreeParser.Token.TokenType;
import com.html5parser.SimplestTreeParser.TreeConstructor;

public class Data_state implements TokenizerState{

	private TokenizerState nextState;
	
	public void process(int currentChar, TreeConstructor treeConstructor ){
		switch (currentChar) {
		case 0x0026: // &
			//nextState = new Character_reference_in_data_state(); 
			break;
		case 0x003C: // <
			//nextState = new Tag_open_state(); 
			break;
		case 0x0000: // NULL
			ParserStacks.parseErrors
			.push("NULL Character encountered");
			treeConstructor.ProcessToken(new Token(TokenType.character, String
					.valueOf(currentChar)));			
			break;
		default:
			treeConstructor.ProcessToken(new Token(TokenType.character, String
					.valueOf(currentChar)));
			break;
		}
	}

	
	public TokenizerState nextState(){
		return nextState;
	}
	

	
	
	
}
