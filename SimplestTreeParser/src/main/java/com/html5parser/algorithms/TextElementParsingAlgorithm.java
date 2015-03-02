package com.html5parser.algorithms;

import com.html5parser.SimplestTreeParser.InsertionMode;
import com.html5parser.SimplestTreeParser.Parser;
import com.html5parser.SimplestTreeParser.Token;
import com.html5parser.SimplestTreeParser.Token.TokenType;
import com.html5parser.SimplestTreeParser.TokenizerContext;
import com.html5parser.TokenizerStates.RCDATA_state;

public class TextElementParsingAlgorithm {
	
	public static void parseRawTextElement(Token token){
		parseTextElement(token,1);
	}
	
	public static void parseRCDATAElement(Token token){
		parseTextElement(token,2);
	}
	
	private static void parseTextElement(Token token,int textType){
		if(!token.getType().equals(TokenType.start_tag))
			throw new IllegalArgumentException(token.getType()+" is not an start tag token (Html Spec: 12.2.5.2)");
		
		ForeingElementInsertionAlgorithm.insertHTMLElement(token);
		
		if(textType==1)
			throw new UnsupportedOperationException(token.getType().name()+"token handling not implemented yet (TextElementParsingAlgorithm)");
			//TODO: TokenizerContext.state = new RAWTEXT_state();
		else
			TokenizerContext.state = new RCDATA_state();
		
		Parser.originalMode = Parser.currentMode;
		Parser.currentMode = InsertionMode.text;
	}

}
