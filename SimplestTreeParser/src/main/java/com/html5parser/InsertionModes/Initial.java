package com.html5parser.InsertionModes;

import org.w3c.dom.Document;

import com.html5parser.SimplestTreeParser.InsertionMode;
import com.html5parser.SimplestTreeParser.Parser;
import com.html5parser.SimplestTreeParser.Token;
import com.html5parser.SimplestTreeParser.TreeConstructor;

public class Initial {

	public boolean process(Document doc, Token token,
			TreeConstructor treeConstructor) {
		switch (token.getType()) {
		case DOCTYPE:
			break;

		// A comment token
		// Append a Comment node to the Document object with the data attribute
		// set to the data given in the comment token.
		case comment:
			doc.appendChild(doc.createComment(token.getValue()));
			break;

		// A character token that is one of U+0009 CHARACTER TABULATION, "LF"
		// (U+000A), "FF" (U+000C), "CR" (U+000D), or U+0020 SPACE
		// Ignore the token.
		case character:
			if (!(token.getValue().equals(0x0009)
					|| token.getValue().equals(0x000A)
					|| token.getValue().equals(0x000C)
					|| token.getValue().equals(0x000D) || token.getValue()
					.equals(0x0020)))
				TokenAnythingElse(token, treeConstructor);
			break;

		// Anything else
		// If the document is not an iframe srcdoc document, then this is a
		// parse error; set the Document to quirks mode.
		// In any case, switch the insertion mode to "before html", then
		// reprocess the current token.
		case end_of_file:
		case start_tag:
		case end_tag:
		default:
			TokenAnythingElse(token, treeConstructor);
		}
		return false;
	}

	private void TokenAnythingElse(Token token, TreeConstructor treeConstructor) {
		Parser.currentMode = InsertionMode.before_html;
		treeConstructor.processToken(token);
	}
}
