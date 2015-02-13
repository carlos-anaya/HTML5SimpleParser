package com.html5parser.InsertionModes;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.html5parser.SimplestTreeParser.InsertionMode;
import com.html5parser.SimplestTreeParser.Parser;
import com.html5parser.SimplestTreeParser.ParserStacks;
import com.html5parser.SimplestTreeParser.Token;

public class InBody {

	public boolean process(Document doc, Token token) {
		switch (token.getType()) {
		case character:
			break;
		case comment:
			//doc.appendChild(doc.createComment(token.getValue()));
			break;
		case DOCTYPE:
			ParserStacks.parseErrors
					.push("DOCTYPE in InBody insertion mode");
			break;
		case start_tag:
			break;
		case end_tag:
			break;
		case end_of_file:
			TokenEndOfFile();
			return true;
		default:
			TokenAnythingElse(doc);
			break;
		}
		return false;
	}

	private void TokenEndOfFile() {
		ParserStacks.parseErrors
		.push("DOCTYPE in InBody insertion mode");
	}
	
	private void TokenAnythingElse(Document doc) {
		
	}
}
