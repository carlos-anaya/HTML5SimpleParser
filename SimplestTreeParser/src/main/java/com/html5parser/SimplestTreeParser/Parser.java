package com.html5parser.SimplestTreeParser;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.html5parser.InsertionModes.AfterHead;

public class Parser {

	public static InsertionMode currentMode = InsertionMode.initial;
	public static TokenizerState currentState = TokenizerState.Data_state;

	public static void main(String[] args) {
		System.out.println("Hello World!");
		
		
		
		
		new Parser().parse(new ByteArrayInputStream(("").getBytes()));
		
	}
	
	public void blabla(Document doc){
		Element el2 = doc.createElement("body");
		doc.getElementsByTagName("html").item(0).appendChild(el2);
	}

	public Document parse(InputStream stream) {
		Document doc = null;
		try {
			stream = new Decoder().ValidateEncoding(stream);
			new Tokenizer().Tokenize(stream);
			
			//Stop Parsing
			if(!ParserStacks.openElements.isEmpty())ParserStacks.openElements.clear();
			doc = new TreeConstructor().getDOM();
			
			System.out.println(doc.getElementsByTagName("html").item(0).getNodeName());
		} finally {
		}

		return doc;
	}
}

// //convert String into InputStream
// InputStream is = new ByteArrayInputStream(str.getBytes());
//
// // read it with BufferedReader
// BufferedReader br = new BufferedReader(new InputStreamReader(is));
//
// String line;
// while ((line = br.readLine()) != null) {
// System.out.println(line);
// }
//
// br.close();