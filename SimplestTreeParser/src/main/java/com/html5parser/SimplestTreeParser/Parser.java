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

import com.html5parser.TokenizerStates.Data_state;
import com.html5parser.TokenizerStates.State;

public class Parser {

	public static InsertionMode currentMode;
	public static InsertionMode originalMode;
	public static State currentState;

	// public static Document doc;

	public static void main(String[] args) {
		// if (args.length == 1)
		// new Parser().parse(new ByteArrayInputStream((args[0]).getBytes()));
		String input = "<Html><head><title><title/></head><body/></html>";
		new Parser().parse(new ByteArrayInputStream(input.getBytes()));
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
		TokenizerContext.state = new Data_state();
		Parser.currentState = TokenizerContext.state;

		try {
			System.out.print("***** Tokens:\n");
			stream = new Decoder().ValidateEncoding(stream);
			new Tokenizer().Tokenize(stream);
			doc = DocumentGenerator.getFinalDocument(); // doc = new
			// StackUpdater().getDocument();
			// Stop Parsing
			if (!ParserStacks.openElements.isEmpty())
				ParserStacks.openElements.clear();

			System.out.print("\n\n***** DOM:\n");
			printDocument(doc, System.out);

			System.out.print("\n\n***** ERROR LOG:\n"
					+ ParserStacks.parseErrors);
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

	private void printDocument(Document doc, OutputStream out)
			throws IOException, TransformerException {
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer = tf.newTransformer();
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
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