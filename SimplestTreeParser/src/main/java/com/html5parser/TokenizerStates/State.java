package com.html5parser.TokenizerStates;

import com.html5parser.SimplestTreeParser.Token;
import com.html5parser.SimplestTreeParser.TreeConstructor;

public interface State {

	public Token process(int currentChar, TreeConstructor treeConstructor, Token currentToken);
	
	public State nextState();
		
}
