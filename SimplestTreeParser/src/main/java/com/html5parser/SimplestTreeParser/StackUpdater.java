package com.html5parser.SimplestTreeParser;

import java.util.Stack;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class StackUpdater {

	public void updateStack(String value, String type) {

		Document doc = DocumentGenerator.getDocument(); //Parser.doc;

		Stack<Element> elements = new Stack<Element>();
		Element newElement;

		if (ParserStacks.openElements.size() == 0) {
			ParserStacks.openElements.push(doc.createElement(value));
			return;
		}

		while (ParserStacks.openElements.size() > 0) {
			Element el = ParserStacks.openElements.pop();
			if (type == "element") {
				newElement = doc.createElement(value);
				el.appendChild(newElement);
				elements.push(newElement);
				type = "";
			} else if (type == "text") {
				el.appendChild(doc.createTextNode(value));
				type = "";
			} else if (type == "comment") {
				el.appendChild(doc.createComment(value));
				ParserStacks.openElements.push(el);
				return;
			}
			newElement = el;
			elements.push(newElement);
		}

		while (elements.size() > 0) {
			ParserStacks.openElements.push(elements.pop());
		}
	}

//	public Document getDocument() {
//
//		Document doc = Parser.doc;
//		doc.appendChild(ParserStacks.openElements.firstElement());
//		return doc;
//	}
}