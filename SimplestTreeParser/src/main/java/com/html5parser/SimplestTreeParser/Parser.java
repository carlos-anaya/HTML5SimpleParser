package com.html5parser.SimplestTreeParser;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

public class Parser {

	public static InsertionMode currentMode;
	public static InsertionMode originalMode;
	public static TokenizerState currentState;

	// public static Document doc;

	public static void main(String[] args) {
		// if (args.length == 1)
		// new Parser().parse(new ByteArrayInputStream((args[0]).getBytes()));
		String input = "</⢺晀㭰>";
		// new Parser().parse(new ByteArrayInputStream(input.getBytes()));
		new Parser().parse(input);
	}

	public Document parse(String htmlString) {
		System.out.println("***** Input:\n" + htmlString);
		return new Parser().parse(new ByteArrayInputStream(htmlString
				.getBytes()));
	}

	public Document parse(InputStream stream) {

		Document doc = null;
		// doc = null;
		// try {
		// DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		// DocumentBuilder builder = dbf.newDocumentBuilder();
		// doc = builder.newDocument();
		// } catch (ParserConfigurationException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		// Initialization
		Parser.currentMode = InsertionMode.initial;
		Parser.currentState = TokenizerState.Data_state;

		try {
//			System.out.print("\n***** Tokens:\n");
			stream = new Decoder().ValidateEncoding(stream);
			new Tokenizer().Tokenize(stream);
			doc = DocumentGenerator.getFinalDocument(); // doc = new
			// StackUpdater().getDocument();
			// Stop Parsing
			if (!ParserStacks.openElements.isEmpty())
				ParserStacks.openElements.clear();

			System.out.print("\n***** DOM:\n");
			// printDocument(doc, System.out);
			System.out.println(SerializeDocument(doc, false));

			System.out.print("\n\n");
//			 System.out.print("\n\n***** ERROR LOG:\n"
//			 + ParserStacks.parseErrors);
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

	private String SerializeDocument(Document doc, boolean indent)
			throws IOException, TransformerException {
		StringWriter writer = new StringWriter();
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer = tf.newTransformer();
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
		transformer.setOutputProperty(OutputKeys.METHOD, "xml");
		transformer.setOutputProperty(OutputKeys.INDENT, indent ? "yes" : "no");
		transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		transformer.setOutputProperty(
				"{http://xml.apache.org/xslt}indent-amount", "4");

		transformer.transform(new DOMSource(doc), new StreamResult(writer));
		return writer.toString();
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