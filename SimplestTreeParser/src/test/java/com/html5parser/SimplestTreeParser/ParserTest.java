package com.html5parser.SimplestTreeParser;

import static org.junit.Assert.assertEquals;

import java.io.StringWriter;
import java.util.Arrays;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.w3c.dom.Document;

@RunWith(value = Parameterized.class)
public class ParserTest {

	private Parser parser;

	private String input;

	private final String MINIMAL_DOM = "<html><head/><body/></html>";

	// parameters pass via this constructor
	public ParserTest(String input) {
		this.input = input;
	}

	// Declares parameters here
	@Parameters(name = "Test {0}")
	public static Iterable<Object[]> data1() {
		return Arrays.asList(new Object[][] { { "" }, { "    " }, { "<html>" },
				{ "<head>" }, { "<body>" },

				{ "<HTML>" }, { "<hEAd>" },

				{ "<html/>" }, { "<head/>" }, { "<body/>" },

				{ "<html" },

				{ "<html/" }, { "<head/" }, { "<body/" },

				{ "</html" }, { "</head" }, { "</body" },

				{ "</html/>" }, { "</head/>" }, { "</body/>" },

				{ "</html/" }, { "</head/" }, { "</body/" },

				{ "<html/><xx" },

				{ "<html><head><body>" }, { "<html><body>" },
				{ "<head><body><html/>" },

				{ "<html>             " },
				{ "<html>      <head><body><html/>" },

				{ "<html><head/><body/></html>" },

				{ "      <html><head><body><html/>" },
				{ "           <html>      <head><body><html/>" },

		});
	}

	@Before
	public void setUp() throws Exception {
		parser = new Parser();
	}

	@Test
	public final void tests() {

		Document doc = parser.parse(input);
		// .parse(new ByteArrayInputStream((input).getBytes()));

		assertEquals(MINIMAL_DOM, SerializeDocument(doc));
		// //Assert doc has only one child
		// assertTrue(doc.getChildNodes().getLength() == 1);
		//
		// //Assert this only child is the html tag
		// Node html = doc.getLastChild();
		// assertTrue("html tag incorrect "+ html.getNodeName(),
		// html.getNodeName().equals("html"));
		//
		// //Assert doc has two children
		// assertTrue(html.getChildNodes().getLength() == 2);
		//
		// //Assert html has head and body as children
		// Node head = html.getFirstChild();
		// assertTrue("head tag incorrect: "+ head.getNodeName(),
		// head.getNodeName().equals("head"));
		//
		// Node body = head.getNextSibling();
		// assertTrue("body tag incorrect "+ body.getNodeName(),
		// body.getNodeName().equals("body"));
		//
		// //Assert head and body have no children
		//
		// assertTrue(head.getChildNodes().getLength() == 0);
		// assertTrue(body.getChildNodes().getLength() == 0);
		//
	}

	private String SerializeDocument(Document doc) {
		boolean indent = false;
		try {
			StringWriter writer = new StringWriter();
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION,
					"yes");
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			transformer.setOutputProperty(OutputKeys.INDENT, indent ? "yes"
					: "no");
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			transformer.setOutputProperty(
					"{http://xml.apache.org/xslt}indent-amount", "4");

			transformer.transform(new DOMSource(doc), new StreamResult(writer));
			return writer.toString();
		} catch (IllegalArgumentException
				| TransformerFactoryConfigurationError | TransformerException e) {
			e.printStackTrace();
			return null;

		}
	}

}
