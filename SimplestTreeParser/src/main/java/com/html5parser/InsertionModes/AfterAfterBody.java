package com.html5parser.InsertionModes;

import com.html5parser.SimplestTreeParser.InsertionMode;
import com.html5parser.SimplestTreeParser.Parser;
import com.html5parser.SimplestTreeParser.ParserStacks;
import com.html5parser.SimplestTreeParser.StackUpdater;
import com.html5parser.SimplestTreeParser.Token;
import com.html5parser.SimplestTreeParser.TreeConstructor;

public class AfterAfterBody {

	public boolean process(Token token, TreeConstructor treeConstructor) {
		switch (token.getType()) {

		// A comment token
		// Append a Comment node to the Document object with the data attribute
		// set to the data given in the comment token.
		case comment:
			new StackUpdater().updateStack(token.getValue(), "comment");
			break;
		// An end-of-file token
		// Stop parsing.
		case end_of_file:
			return true;
			// A DOCTYPE token
		case DOCTYPE:
			ParserStacks.parseErrors.push("Unexpected html tag.");
			break;
		// A character token that is one of U+0009 CHARACTER TABULATION, "LF"
		// (U+000A), "FF" (U+000C), "CR" (U+000D), or U+0020 SPACE

		case character:
			int currentChar = (int)token.getValue().charAt(0);
			if (currentChar == 0x0009
					|| currentChar == 0x000A
					|| currentChar == 0x000C
					|| currentChar == 0x000D 
					|| currentChar == 0x0020) {

				// doc.getElementsByTagName("head")
				// .item(0)
				// .setTextContent(
				// doc.getElementsByTagName("head").item(0)
				// .getTextContent()
				// + token.getValue());
				ParserStacks.parseErrors.push("Unexpected html tag.");
			} else
				TokenAnythingElse(token, treeConstructor, true);
			break;
		case start_tag:
			TokenStartTag(token, treeConstructor);
			break;

		default:
			TokenAnythingElse(token, treeConstructor, true);
			break;
		}
		return false;
	}

	public void TokenAnythingElse(Token token, TreeConstructor treeConstructor,
			boolean reprocessToken) {
		ParserStacks.parseErrors.push(token.getValue()
				+ " after InBody insertion mode");
		Parser.currentMode = InsertionMode.in_body;
		if (reprocessToken)
			treeConstructor.processToken(token);
	}
	
	private void TokenStartTag(Token token, TreeConstructor treeConstructor) {
		switch (token.getValue()) {
		// A start tag whose tag name is "html"
		// Process the token using the rules for the "in body" insertion mode.

		// From "in body": Parse error. For each attribute on the token, check
		// to see if the attribute is already present on the top element of the
		// stack of open elements. If it is not, add the attribute and its
		// corresponding token.getValue() to that element.
		case "html":
			ParserStacks.parseErrors.push("Unexpected html tag.");
			break;
		default:
			TokenAnythingElse(token, treeConstructor, true);
			break;

		}

	}
}
