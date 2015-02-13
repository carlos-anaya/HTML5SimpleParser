package com.html5parser.InsertionModes;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.html5parser.SimplestTreeParser.InsertionMode;
import com.html5parser.SimplestTreeParser.Parser;
import com.html5parser.SimplestTreeParser.ParserStacks;
import com.html5parser.SimplestTreeParser.Token;

public class BeforeHTML {

	public void process(Document doc, Token token) {
		switch (token.getType()) {
		case DOCTYPE:
			ParserStacks.parseErrors
					.push("DOCTYPE in BeforeHTML insertion mode");
			break;
		case comment:
			//doc.appendChild(doc.createComment(token.getValue()));
			break;
		case character:
			break;
		case start_tag:
			break;
		case end_tag:
			break;
		case end_of_file:
		default:
			TokenAnythingElse(doc);
			break;
		}
	}

	private void TokenAnythingElse(Document doc) {
		Element el = doc.createElement("html");
		doc.appendChild(el);
		ParserStacks.openElements.push(el);
		Parser.currentMode = InsertionMode.before_head;
	}
}
