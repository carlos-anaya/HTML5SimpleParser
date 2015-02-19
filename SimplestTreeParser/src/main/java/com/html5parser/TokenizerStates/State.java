package com.html5parser.TokenizerStates;

import com.html5parser.SimplestTreeParser.TokenizerContext;

public interface State {

	public void process(TokenizerContext context);

	// public State nextState();

}
