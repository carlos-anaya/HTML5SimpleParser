package com.html5parser.InsertionModes;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.html5parser.SimplestTreeParser.InsertionMode;
import com.html5parser.SimplestTreeParser.Parser;
import com.html5parser.SimplestTreeParser.ParserStacks;
import com.html5parser.SimplestTreeParser.Token;
import com.html5parser.SimplestTreeParser.TreeConstructor;

public class BeforeHead {

	public void process(Document doc, Token token,
			TreeConstructor treeConstructor) {
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
				TokenAnythingElse(doc, token, treeConstructor, true);
			break;
		case comment:
			// doc.appendChild(doc.createComment(token.getValue()));
			break;
		case DOCTYPE:
			ParserStacks.parseErrors
					.push("DOCTYPE in BeforeHead insertion mode");
			break;
		case start_tag:
			TokenStartTag(doc, token, treeConstructor);
			break;
		case end_tag:
			TokenEndTag(doc, token, treeConstructor);
			break;
		case end_of_file:
		default:
			TokenAnythingElse(doc, token, treeConstructor, true);
			break;
		}
	}

	private void TokenAnythingElse(Document doc, Token token,
			TreeConstructor treeConstructor, boolean reprocessToken) {
		Element el = doc.createElement("head");
		doc.getElementsByTagName("html").item(0).appendChild(el);
		ParserStacks.openElements.push(el);
		Parser.currentMode = InsertionMode.in_head;
		if (reprocessToken)
			treeConstructor.processToken(token);
	}

	private void TokenStartTag(Document doc, Token token,
			TreeConstructor treeConstructor) {
		// A start tag whose tag name is "html"
		// Process the token using the rules for the "in body" insertion mode.

		// From "in body": Parse error. For each attribute on the token, check
		// to see if the attribute is already present on the top element of the
		// stack of open elements. If it is not, add the attribute and its
		// corresponding token.getValue() to that element.
		if (token.getValue().equals("html"))
			ParserStacks.parseErrors.push("Unexpected html start tag.");
		else if (token.getValue().equals("head"))
			TokenAnythingElse(doc, token, treeConstructor, false);
		else
			TokenAnythingElse(doc, token, treeConstructor, true);
	}

	private void TokenEndTag(Document doc, Token token,
			TreeConstructor treeConstructor) {
		// An end tag whose tag name is one of: "head", "body", "html", "br"
		// Act as described in the "anything else" entry below.
		// Any other end tag
		// Parse error. Ignore the token.
		switch (token.getValue()) {
		case "head":
		case "body":
		case "html":
		case "br":
			TokenAnythingElse(doc, token, treeConstructor, true);
			break;
		default:
			ParserStacks.parseErrors
					.push("Unexpected end tag in BeforeHTML insertion mode");
			break;
		}
	}
}
