package com.html5parser.InsertionModes;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.html5parser.SimplestTreeParser.InsertionMode;
import com.html5parser.SimplestTreeParser.Parser;
import com.html5parser.SimplestTreeParser.ParserStacks;
import com.html5parser.SimplestTreeParser.Token;
import com.html5parser.SimplestTreeParser.TreeConstructor;

public class AfterHead {

	public void process(Document doc, Token token, TreeConstructor treeConstructor ) {
		switch (token.getType()) {
		case character:
			break;
		case comment:
			//doc.appendChild(doc.createComment(token.getValue()));
			break;
		case DOCTYPE:
			ParserStacks.parseErrors
					.push("DOCTYPE in AfterHead insertion mode");
			break;
		case start_tag:
			break;
		case end_tag:
			break;
		case end_of_file:
		default:
			TokenAnythingElse(doc, token,  treeConstructor );
			break;
		}
	}

	public void TokenAnythingElse(Document doc,Token token, TreeConstructor treeConstructor ) {
		Element el = doc.createElement("body");
		doc.getElementsByTagName("html").item(0).appendChild(el);
		ParserStacks.openElements.push(el);
		Parser.currentMode = InsertionMode.in_body;
		treeConstructor.processToken(token);
	}
}
