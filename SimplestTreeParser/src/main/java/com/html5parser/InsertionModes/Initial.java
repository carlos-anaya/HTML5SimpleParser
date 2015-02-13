package com.html5parser.InsertionModes;

import org.w3c.dom.Document;

import com.html5parser.SimplestTreeParser.InsertionMode;
import com.html5parser.SimplestTreeParser.Parser;
import com.html5parser.SimplestTreeParser.Token;

public class Initial {

	public boolean process(Document doc, Token token) {
		switch (token.getType()) {
			case DOCTYPE:
				break;
			case comment:
				//doc.appendChild(doc.createComment(token.getValue()));
				break;
			case character:
				break;
			case end_of_file:
			case start_tag:
			case end_tag:
			default:
				TokenAnythingElse();
				break;
		}
		return false;
	}

	private void TokenAnythingElse() {
		Parser.currentMode = InsertionMode.before_html;
	}
}
