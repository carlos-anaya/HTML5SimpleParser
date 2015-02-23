package com.html5parser.InsertionModes;

import com.html5parser.SimplestTreeParser.InsertionMode;
import com.html5parser.SimplestTreeParser.Parser;
import com.html5parser.SimplestTreeParser.ParserStacks;
import com.html5parser.SimplestTreeParser.StackUpdater;
import com.html5parser.SimplestTreeParser.Token;
import com.html5parser.SimplestTreeParser.TreeConstructor;

public class BeforeHTML {

	public void process(Token token, TreeConstructor treeConstructor) {
		switch (token.getType()) {

		// A DOCTYPE token
		// Parse error. Ignore the token.
		case DOCTYPE:
			ParserStacks.parseErrors
					.push("DOCTYPE in BeforeHTML insertion mode");
			break;

		// A comment token
		// Append a Comment node to the Document object with the data attribute
		// set to the data given in the comment token.
		case comment:
			new StackUpdater().updateStack(token.getValue(), "comment");
			break;

		// A character token that is one of U+0009 CHARACTER TABULATION, "LF"
		// (U+000A), "FF" (U+000C), "CR" (U+000D), or U+0020 SPACE
		// Ignore the token.
		case character:
			int currentChar = (int)token.getValue().charAt(0);
			if (!(currentChar == 0x0009
					|| currentChar == 0x000A
					|| currentChar == 0x000C
					|| currentChar == 0x000D 
					|| currentChar == 0x0020))
				TokenAnythingElse(token, treeConstructor, true);
			break;

		case start_tag:
			TokenStartTag(token, treeConstructor);
			break;

		case end_tag:
			TokenEndTag(token, treeConstructor);
			break;

		// Anything else
		case end_of_file:
		default:
			TokenAnythingElse(token, treeConstructor, true);
			break;
		}
	}

	private void TokenAnythingElse(Token token,
			TreeConstructor treeConstructor, boolean reprocessToken) {
		// Create an html element. Append it to the Document object. Put this
		// element in the stack of open elements.
		// If the Document is being loaded as part of navigation of a browsing
		// context, then: run the application cache selection algorithm with no
		// manifest, passing it the Document object.
		// Switch the insertion mode to "before head", then reprocess the
		// current token.

		// Element el = doc.createElement("html");
		// doc.appendChild(el);
		// ParserStacks.openElements.push(el);
		new StackUpdater().updateStack("html", "element");
		Parser.currentMode = InsertionMode.before_head;
		if (reprocessToken)
			treeConstructor.processToken(token);
	}

	private void TokenStartTag(Token token, TreeConstructor treeConstructor) {
		// A start tag whose tag name is "html"
		// Create an element for the token in the HTML namespace. Append it to
		// the Document object. Put this element in the stack of open elements.
		// If the Document is being loaded as part of navigation of a browsing
		// context, then: if the newly created element has a manifest attribute
		// whose token.getValue() is not the empty string, then resolve the
		// token.getValue() of that
		// attribute to an absolute URL, relative to the newly created element,
		// and if that is successful, run the application cache selection
		// algorithm with the resulting absolute URL with any <fragment>
		// component removed; otherwise, if there is no such attribute, or its
		// token.getValue() is the empty string, or resolving its
		// token.getValue() fails, run the
		// application cache selection algorithm with no manifest. The algorithm
		// must be passed the Document object.
		// Switch the insertion mode to "before head".
		if (token.getValue().equals("html"))
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
