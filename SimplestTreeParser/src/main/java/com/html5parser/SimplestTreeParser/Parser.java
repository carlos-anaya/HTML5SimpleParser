package com.html5parser.SimplestTreeParser;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Parser {

	public static InsertionMode currentMode ;
	public static TokenizerState currentState ;

	public static void main(String[] args) {
		System.out.println("Hello World!");

//		if (args.length == 1)
//			new Parser().parse(new ByteArrayInputStream((args[0]).getBytes()));
		String input = "<html>";
		new Parser().parse(new ByteArrayInputStream(input.getBytes()));
	}

	public Document parse(InputStream stream) {
		
		//Initialization
		Parser.currentMode = InsertionMode.initial;
		Parser.currentState = TokenizerState.Data_state;
		
		Document doc = null;
		try {
			stream = new Decoder().ValidateEncoding(stream);
			doc = new Tokenizer().Tokenize(stream);

			// Stop Parsing
			if (!ParserStacks.openElements.isEmpty())
				ParserStacks.openElements.clear();

			printDocument(doc, System.out);
			// System.out.println(doc.getElementsByTagName("body").item(0).getNodeName());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
		}

		return doc;
	}

	public static void printDocument(Document doc, OutputStream out)
			throws IOException, TransformerException {
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer = tf.newTransformer();
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
		transformer.setOutputProperty(OutputKeys.METHOD, "xml");
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		transformer.setOutputProperty(
				"{http://xml.apache.org/xslt}indent-amount", "4");

		transformer.transform(new DOMSource(doc), new StreamResult(
				new OutputStreamWriter(out, "UTF-8")));
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