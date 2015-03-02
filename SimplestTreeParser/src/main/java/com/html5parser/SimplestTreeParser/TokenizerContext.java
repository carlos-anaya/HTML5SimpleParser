package com.html5parser.SimplestTreeParser;

import com.html5parser.TokenizerStates.Data_state;
import com.html5parser.TokenizerStates.State;

public class TokenizerContext {

	public static State state = new Data_state();
	private TreeConstructor treeConstructor = new TreeConstructor();
	private Token currentToken = null;
	private int currentChar = 0;

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public TreeConstructor getTreeConstructor() {
		return treeConstructor;
	}

	public void setTreeConstructor(TreeConstructor treeConstructor) {
		this.treeConstructor = treeConstructor;
	}

	public Token getCurrentToken() {
		return currentToken;
	}

	public void setCurrentToken(Token currentToken) {
		this.currentToken = currentToken;
	}

	public int getCurrentChar() {
		return currentChar;
	}

	public void setCurrentChar(int currentChar) {
		this.currentChar = currentChar;
	}

}
