package com.html5parser.InsertionModes;

import com.html5parser.SimplestTreeParser.InsertionMode;
import com.html5parser.SimplestTreeParser.Parser;
import com.html5parser.SimplestTreeParser.ParserStacks;
import com.html5parser.SimplestTreeParser.StackUpdater;
import com.html5parser.SimplestTreeParser.Token;
import com.html5parser.SimplestTreeParser.TreeConstructor;

public class AfterHead {

	public void process(Token token, TreeConstructor treeConstructor) {
		switch (token.getType()) {
		// A character token that is one of U+0009 CHARACTER TABULATION, "LF"
		// (U+000A), "FF" (U+000C), "CR" (U+000D), or U+0020 SPACE
		// Insert the character into the current node.
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
				new StackUpdater().updateStack(token.getValue(), "text");
			} else
				TokenAnythingElse(token, treeConstructor, true);
			break;
		case comment:
			new StackUpdater().updateStack(token.getValue(), "comment");
			break;
		case DOCTYPE:
			ParserStacks.parseErrors.push("DOCTYPE in InHead insertion mode");
			break;
		case start_tag:
			TokenStartTag(token, treeConstructor);
			break;
		case end_tag:
			TokenEndTag(token, treeConstructor);
			break;
		case end_of_file:
		default:
			TokenAnythingElse(token, treeConstructor, true);
			break;
		}
	}

	public void TokenAnythingElse(Token token, TreeConstructor treeConstructor,
			boolean reprocessToken) {
		// Element el = doc.createElement("body");
		// doc.getElementsByTagName("html").item(0).appendChild(el);
		// ParserStacks.openElements.push(el);
		new StackUpdater().updateStack("body", "element");
		Parser.currentMode = InsertionMode.in_body;

		if (reprocessToken)
			treeConstructor.processToken(token);
	}

	private void TokenEndTag(Token token, TreeConstructor treeConstructor) {
		// An end tag whose tag name is "head"
		// Pop the current node (which will be the head element) off the stack
		// of open elements.
		// Switch the insertion mode to "after head".
		// An end tag whose tag name is one of: "body", "html", "br"
		// Act as described in the "anything else" entry below.
		if (token.getValue().equals("head") || token.getValue().equals("body")
				|| token.getValue().equals("html")
				|| token.getValue().equals("br")) {
			TokenAnythingElse(token, treeConstructor, true);
		} else {
			ParserStacks.parseErrors.push(token.getValue()
					+ " close tag in InHead insertion mode");
		}
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

		// A start tag whose tag name is "body"
		// Insert an HTML element for the token.
		// Set the frameset-ok flag to "not ok".
		// Switch the insertion mode to "in body".
		case "body":
			TokenAnythingElse(token, treeConstructor, false);
			break;
		// A start tag whose tag name is "frameset"
		// Insert an HTML element for the token.
		// Switch the insertion mode to "in frameset".
		case "frameset":
			ParserStacks.parseErrors.push("Unexpected frameset tag.");
			break;
		// A start tag whose tag name is "meta"
		// Insert an HTML element for the token. Immediately pop the current
		// node off the stack of open elements.

		default:
			TokenAnythingElse(token, treeConstructor, true);
			break;

		}

	}
}
