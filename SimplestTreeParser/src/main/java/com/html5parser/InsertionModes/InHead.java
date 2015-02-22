package com.html5parser.InsertionModes;

import com.html5parser.SimplestTreeParser.InsertionMode;
import com.html5parser.SimplestTreeParser.Parser;
import com.html5parser.SimplestTreeParser.ParserStacks;
import com.html5parser.SimplestTreeParser.StackUpdater;
import com.html5parser.SimplestTreeParser.Token;
import com.html5parser.SimplestTreeParser.TreeConstructor;

public class InHead {

	public void process(Token token, TreeConstructor treeConstructor) {
		switch (token.getType()) {
		// A character token that is one of U+0009 CHARACTER TABULATION, "LF"
		// (U+000A), "FF" (U+000C), "CR" (U+000D), or U+0020 SPACE
		// Insert the character into the current node.
		case character:
			if (token.getValue().equals(0x0009)
					|| token.getValue().equals(0x000A)
					|| token.getValue().equals(0x000C)
					|| token.getValue().equals(0x000D)
					|| token.getValue().equals(0x0020)) {

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

	private void TokenAnythingElse(Token token,
			TreeConstructor treeConstructor, boolean reprocessToken) {
		ParserStacks.openElements.pop();
		Parser.currentMode = InsertionMode.after_head;
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
		if (token.getValue().equals("head"))
			TokenAnythingElse(token, treeConstructor, false);
		else if (token.getValue().equals("body")
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

		// A start tag whose tag name is one of: "base", "basefont", "bgsound",
		// "command", "link"
		// Insert an HTML element for the token. Immediately pop the current
		// node off the stack of open elements.
		case "base":
		case "basefont":
		case "bgsound":
		case "command":
		case "link":
			// Element el = doc.createElement("html");
			// doc.appendChild(el);
			// ParserStacks.openElements.pop();
			ParserStacks.parseErrors
					.push("Unexpected base/basefont/bgsound/command/link tag.");
			break;
		// A start tag whose tag name is "meta"
		// Insert an HTML element for the token. Immediately pop the current
		// node off the stack of open elements.
		case "meta":
			// Element el1 = doc.createElement("html");
			// doc.appendChild(el1);
			// ParserStacks.openElements.pop();
			ParserStacks.parseErrors.push("Unexpected meta tag.");
			break;

		// A start tag whose tag name is "title"
		// Follow the generic RCDATA element parsing algorithm.
		case "title":
			// Insert an HTML element for the token.
			// If the algorithm that was invoked is the generic raw text element
			// parsing algorithm, switch the tokenizer to the RAWTEXT state;
			// otherwise the algorithm invoked was the generic RCDATA element
			// parsing algorithm, switch the tokenizer to the RCDATA state.
			// Let the original insertion mode be the current insertion mode.
			// Then, switch the insertion mode to "text".
			new StackUpdater().updateStack("title", "element");
			Parser.originalMode = Parser.currentMode;
			Parser.currentMode = InsertionMode.text;
			// tokenizer to RCDATA_State
			break;
		// A start tag whose tag name is "head"
		// Parse error. Ignore the token.
		case "head":
			ParserStacks.parseErrors
					.push("head start tag in InHead insertion mode.");
			break;
		default:
			TokenAnythingElse(token, treeConstructor, true);
			break;

		}

	}
}
