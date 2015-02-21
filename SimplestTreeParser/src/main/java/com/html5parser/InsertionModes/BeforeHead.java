package com.html5parser.InsertionModes;

import com.html5parser.SimplestTreeParser.InsertionMode;
import com.html5parser.SimplestTreeParser.Parser;
import com.html5parser.SimplestTreeParser.ParserStacks;
import com.html5parser.SimplestTreeParser.StackUpdater;
import com.html5parser.SimplestTreeParser.Token;
import com.html5parser.SimplestTreeParser.TreeConstructor;

public class BeforeHead {

	public void process(Token token, TreeConstructor treeConstructor) {
		switch (token.getType()) {
		// A character token that is one of U+0009 CHARACTER TABULATION, "LF"
		// (U+000A), "FF" (U+000C), "CR" (U+000D), or U+0020 SPACE
		// Ignore the token.
		case character:
			if (!(token.getValue().equals(0x0009)
					|| token.getValue().equals(0x000A)
					|| token.getValue().equals(0x000C)
					|| token.getValue().equals(0x000D) || token.getValue()
					.equals(0x0020)))
				TokenAnythingElse(token, treeConstructor, true);
			break;
		case comment:
			// doc.appendChild(doc.createComment(token.getValue()));
			break;
		case DOCTYPE:
			ParserStacks.parseErrors
					.push("DOCTYPE in BeforeHead insertion mode");
			break;
		case start_tag:
			TokenStartTag(token, treeConstructor);
			break;
		case end_tag:
			TokenEndTag(token, treeConstructor);
			break;
		// Anything else
		// Act as if a start tag token with the tag name "head" and no
		// attributes had been seen, then reprocess the current token.
		case end_of_file:
		default:
			TokenAnythingElse(token, treeConstructor, true);
			break;
		}
	}

	private void TokenAnythingElse(Token token,
			TreeConstructor treeConstructor, boolean reprocessToken) {
		// Element el = doc.createElement("head");
		// doc.getElementsByTagName("html").item(0).appendChild(el);
		// Element el1 = ParserStacks.openElements.pop();
		// el1.appendChild(el);
		// ParserStacks.openElements.push(el1);
		// ParserStacks.openElements.push(el);
		new StackUpdater().updateStack("head", "element");

		Parser.currentMode = InsertionMode.in_head;
		if (reprocessToken)
			treeConstructor.processToken(token);
	}

	private void TokenStartTag(Token token, TreeConstructor treeConstructor) {
		// A start tag whose tag name is "html"
		// Process the token using the rules for the "in body" insertion mode.

		// From "in body": Parse error. For each attribute on the token, check
		// to see if the attribute is already present on the top element of the
		// stack of open elements. If it is not, add the attribute and its
		// corresponding token.getValue() to that element.
		if (token.getValue().equals("html"))
			ParserStacks.parseErrors.push("Unexpected html start tag.");
		// A start tag whose tag name is "head"
		// Insert an HTML element for the token.
		// Set the head element pointer to the newly created head element.
		// Switch the insertion mode to "in head".
		else if (token.getValue().equals("head"))
			TokenAnythingElse(token, treeConstructor, false);
		else
			TokenAnythingElse(token, treeConstructor, true);
	}

	private void TokenEndTag(Token token, TreeConstructor treeConstructor) {
		// An end tag whose tag name is one of: "head", "body", "html", "br"
		// Act as described in the "anything else" entry below.
		// Any other end tag
		// Parse error. Ignore the token.
		switch (token.getValue()) {
		case "head":
		case "body":
		case "html":
		case "br":
			TokenAnythingElse(token, treeConstructor, true);
			break;
		default:
			ParserStacks.parseErrors
					.push("Unexpected end tag in BeforeHTML insertion mode");
			break;
		}
	}
}
