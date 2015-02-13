package com.html5parser.TokenizerStates;

import com.html5parser.SimplestTreeParser.Token;
import com.html5parser.SimplestTreeParser.TreeConstructor;

public interface TokenizerState {

	public void process(int currentChar, TreeConstructor treeConstructor);
	
	public TokenizerState nextState();
	
	public Token currentToken();
	
}
