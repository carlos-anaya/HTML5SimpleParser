package com.html5parser.TokenizerStates;

import com.html5parser.SimplestTreeParser.Token;
import com.html5parser.SimplestTreeParser.Token.TokenType;
import com.html5parser.SimplestTreeParser.TokenizerContext;
import com.html5parser.SimplestTreeParser.TreeConstructor;

public class Error_state implements State {

	public void process(TokenizerContext context) {

		TreeConstructor treeConstructor = context.getTreeConstructor();
		// EOF
		// Emit an end-of-file token.
		treeConstructor.processToken(new Token(TokenType.end_of_file, null));
	}

}
