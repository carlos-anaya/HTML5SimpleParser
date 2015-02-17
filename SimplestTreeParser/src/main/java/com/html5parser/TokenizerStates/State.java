package com.html5parser.TokenizerStates;

import com.html5parser.SimplestTreeParser.Token;
import com.html5parser.SimplestTreeParser.TokenizerContext;
import com.html5parser.SimplestTreeParser.TreeConstructor;

public interface State {

	public void process(TokenizerContext context);
	
	//public State nextState();
		
}
