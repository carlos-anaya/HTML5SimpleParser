package com.html5parser.TokenizerStates;

import javax.management.RuntimeErrorException;

import com.html5parser.SimplestTreeParser.ParserStacks;
import com.html5parser.SimplestTreeParser.TokenizerContext;

public class Error_state implements State {

	public boolean process(TokenizerContext context) {
		// boolean reconsumeCharacter = false;

		// TreeConstructor treeConstructor = context.getTreeConstructor();
		// // EOF
		// // Emit an end-of-file token.
		// treeConstructor.processToken(new Token(TokenType.end_of_file, null));
		//
		// return reconsumeCharacter;

		throw new RuntimeErrorException(null, "Parse error: "
				+ ParserStacks.parseErrors.lastElement());
	}

}
