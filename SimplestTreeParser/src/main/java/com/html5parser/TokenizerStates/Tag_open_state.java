package com.html5parser.TokenizerStates;

import com.html5parser.SimplestTreeParser.ParserStacks;
import com.html5parser.SimplestTreeParser.Token;
import com.html5parser.SimplestTreeParser.Token.TokenType;
import com.html5parser.SimplestTreeParser.TreeConstructor;

public class Tag_open_state implements TokenizerState{

	private TokenizerState nextState;
	private Token currentToken;
	
	public void process(int currentChar, TreeConstructor treeConstructor ){
		
		//Uppercase ASCII
		if(currentChar > 64 && currentChar < 91)
		{
			//Transfrom to the lowercase ASCII
			currentChar +=20;
		}
		
		//Lowercase ASCII
		if (currentChar > 96 && currentChar < 123)
		{
		
		}
		else{
		switch (currentChar) {
		case 0x0021: // !
			//nextState = new Markup_declaration_open_state(); 
			break;
		case 0x002F: // /
			//nextState = new End_tag_open_state();
			break;
		case 0x003F: // ?
			ParserStacks.parseErrors
			.push("NULL Character encountered");
			//nextState = new Bogus_comment_state();
			break;
		default:
			ParserStacks.parseErrors
			.push("Invalid character");
			nextState = new Data_state();
			treeConstructor.ProcessToken(new Token(TokenType.character, String
					.valueOf(0x003C)));
			break;
		}}
	}

	
	public TokenizerState nextState(){
		return nextState;
	}


	public Token currentToken() {
		return currentToken;
	}
}
