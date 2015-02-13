package com.html5parser.InsertionModes;

import org.w3c.dom.Document;

import com.html5parser.SimplestTreeParser.InsertionMode;
import com.html5parser.SimplestTreeParser.Parser;
import com.html5parser.SimplestTreeParser.ParserStacks;
import com.html5parser.SimplestTreeParser.Token;

public class InHead {

	public void process(Document doc, Token token) {
		switch (token.getType()) {
		case character:
			break;
		case comment:
			//doc.appendChild(doc.createComment(token.getValue()));
			break;
		case DOCTYPE:
			ParserStacks.parseErrors
					.push("DOCTYPE in InHead insertion mode");
			break;
		case start_tag:
			break;
		case end_tag:
			break;
		case end_of_file:
		default:
			TokenAnythingElse();
			break;
		}
	}

	private void TokenAnythingElse() {
		ParserStacks.openElements.pop();
		Parser.currentMode = InsertionMode.after_head;
	}
}
