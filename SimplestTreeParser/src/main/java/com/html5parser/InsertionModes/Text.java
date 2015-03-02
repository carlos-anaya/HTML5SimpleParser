package com.html5parser.InsertionModes;

import com.html5parser.SimplestTreeParser.Parser;
import com.html5parser.SimplestTreeParser.ParserStacks;
import com.html5parser.SimplestTreeParser.StackUpdater;
import com.html5parser.SimplestTreeParser.Token;
import com.html5parser.SimplestTreeParser.TreeConstructor;

public class Text {

	public boolean process(Token token, TreeConstructor treeConstructor) {
		switch (token.getType()) {
		// A character token
		// Insert the token's character into the current node.
		case character:
			characterToken(token);
			break;

		// An end-of-file token
		// Parse error.
		// If the current node is a script element, mark the script element as
		// "already started".
		// Pop the current node off the stack of open elements.
		// Switch the insertion mode to the original insertion mode and
		// reprocess the current token.
		case end_of_file:
			TokenEndOfFile(token, treeConstructor);
			return true;
		case end_tag:
			TokenEndTag(token, treeConstructor);
			break;

		default:
			// TokenAnythingElse();
			break;
		}
		return false;
	}

	private void TokenEndOfFile(Token token, TreeConstructor treeConstructor) {
		ParserStacks.parseErrors.push("EOF");
		ParserStacks.openElements.pop();
		Parser.currentMode = Parser.originalMode;
		treeConstructor.processToken(token);
	}

	// private void TokenAnythingElse() {
	//
	// }

	private void characterToken(Token token) {
		new StackUpdater().updateStack(token.getValue(), "text");
		//THIS IS NOT WORKING, INSTEAD OF ADDING ONLY ONE "TEXT" NODE IN THE DOC, 
		//IT INSERT MULTIPLES NODES FOR A STREAM CONTAINING A CHAIN CHARACTERS
		//SOLUTION: IMPLEMENT THE https://html.spec.whatwg.org/multipage/syntax.html#insert-a-character
		//ALGORITHM
	}

	private void TokenEndTag(Token token, TreeConstructor treeConstructor) {
		switch (token.getValue()) {
		// An end tag whose tag name is "script"
		case "script":
			break;
		// Any other end tag
		// Pop the current node off the stack of open elements.
		// Switch the insertion mode to the original insertion mode.
		default:
			ParserStacks.openElements.pop();
			Parser.currentMode = Parser.originalMode;
			//treeConstructor.processToken(token);
			break;
		}

	}
}