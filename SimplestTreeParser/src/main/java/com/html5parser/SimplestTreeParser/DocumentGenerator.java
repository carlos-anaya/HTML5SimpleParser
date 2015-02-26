package com.html5parser.SimplestTreeParser;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;

public class DocumentGenerator {
	private DocumentGenerator() {
	}

	private static volatile Document doc = null;

	public static Document getDocument() {
		if (doc == null) {
			try {
				DocumentBuilderFactory dbf = DocumentBuilderFactory
						.newInstance();
				DocumentBuilder builder = dbf.newDocumentBuilder();
				doc = builder.newDocument();
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return doc;
	}

	public static Document getFinalDocument() {
		Document out = null;
		if (doc == null)
			doc = getDocument();
			if (ParserStacks.openElements.size() > 0){
				doc.appendChild(ParserStacks.openElements.firstElement());
			out = doc;
			doc = null;
			}
		return out;
	}
}