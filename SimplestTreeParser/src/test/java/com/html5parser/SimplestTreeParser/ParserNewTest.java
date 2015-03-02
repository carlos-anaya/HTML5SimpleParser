package com.html5parser.SimplestTreeParser;

import static org.junit.Assert.assertTrue;

import java.io.StringWriter;
import java.util.Random;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;

public class ParserNewTest {

	private final int MAX_TOKEN_SIZE = 10;
	private Parser parser;
	private final String MINIMAL_DOM = "^(<html><head/><body/></html>)$";
	private final String TITLE_IN_HEAD_DOM = "^(<html><head>)(<title>(.)*</title>)?(</head><body/></html>)$";
	private final String TITLE_IN_BODY_DOM = "^(<html><head/><body>)(<title>(.)*</title>)?(</body></html>)$";
	private final String TITLE_MIXED_DOM = "^(<html><head>)(<title>(.)*</title>)?(</head><body>)(<title>(.)*</title>)?(</body></html>)$";
	private String serializedDoc;

	@Before
	public void setUp() throws Exception {
		parser = new Parser();
	}

	@Test
	public final void whenEmptyStringIsUsedThenProducesMinimalDOM() {
		String inputString = emptyStringGenerator(0);
		serializedDoc = SerializeDocument(parser.parse(inputString));

		assertTrue("Minimal DOM", serializedDoc.matches(MINIMAL_DOM));
	}

	@Test
	public final void whenHtmlHeadBodyTagsAreUsedThenProducesMinimalDOM() {
		String inputString = htmlHeadBodyGenerator(0);
		serializedDoc = SerializeDocument(parser.parse(inputString));

		assertTrue("Minimal DOM", serializedDoc.matches(MINIMAL_DOM));
	}

	@Test
	public final void whenValidEndTagAreUsedThenProducesMinimalDOM() {
		String inputString = validEndTagGenerator(0);
		serializedDoc = SerializeDocument(parser.parse(inputString));

		assertTrue("Minimal DOM", serializedDoc.matches(MINIMAL_DOM));
	}

	@Test(expected = RuntimeException.class)
	public final void whenInvalidEndTagAreUsedThenExceptionIsThrown() {
		String inputString = invalidEndTagGenerator(0);
		SerializeDocument(parser.parse(inputString));
	}
	
	@Test(expected = RuntimeException.class)
	public final void whenNonEmptyStringIsUsedThenExceptionIsThrown() {
		String inputString = latinLettersGenerator(0);
		SerializeDocument(parser.parse(inputString));
	}

	@Test
	public final void whenTitleInHeadisUsedThenProducesMinimalDOMPlusTitle() {
		String inputString = titleGenerator(0);
		serializedDoc = SerializeDocument(parser.parse(inputString));

		assertTrue("Title DOM", serializedDoc.matches(TITLE_IN_HEAD_DOM));
	}

	@Test
	public final void whenTitleInBodyisUsedThenProducesMinimalDOMPlusTitle() {
		String inputString = titleGenerator(0);
		serializedDoc = SerializeDocument(parser.parse(inputString));

		assertTrue("Title DOM", serializedDoc.matches(TITLE_IN_BODY_DOM));
	}

	private String emptyStringGenerator(int maxSize) {

		String res = "";
		char[] chars = new char[] { 0x0009, // TAB
				0x000A, // LF
				0x000C, // FF
				0x0020 // SPACE
		};
		Random randomGenerator = new Random();

		if (randomGenerator.nextBoolean())
			for (int i = 0; i < (maxSize == 0 ? randomGenerator
					.nextInt(MAX_TOKEN_SIZE) : maxSize); i++)
				res += Character.toString(chars[randomGenerator
						.nextInt(chars.length)]);
		return res;
	}

	private String htmlHeadBodyGenerator(int maxSize) {

		String res = "";
		String[] tags = new String[] { "<html>", "<head>", "<body>", "</html>",
				"</head>", "</body>", };
		Random randomGenerator = new Random();

		// if (randomGenerator.nextBoolean())
		for (int i = 0; i < (maxSize == 0 ? randomGenerator
				.nextInt(MAX_TOKEN_SIZE) : maxSize); i++)
			res += tags[randomGenerator.nextInt(tags.length)];
		return res;
	}

	private String titleGenerator(int maxSize) {

		String res = "";
		// Random randomGenerator = new Random();

		// if (randomGenerator.nextBoolean())
		res += "<title>" + latinLettersGenerator(maxSize) + "</title>";
		return res;
	}

	private String validEndTagGenerator(int maxSize) {
		String res = "</" + latinLettersGenerator(0)
				+ unicodeStringGenerator(maxSize).replace(">", "") + ">";
		return res;
	}

	private String invalidEndTagGenerator(int maxSize) {
		String res = "</" + unicodeStringGenerator(maxSize).replace(">", "")
				+ ">";
		return res;
	}

	private String unicodeStringGenerator(int maxSize) {
		String res = "";
		Random randomGenerator = new Random();

		// if (randomGenerator.nextBoolean())
		for (int i = 0; i < (maxSize == 0 ? randomGenerator
				.nextInt(MAX_TOKEN_SIZE) : maxSize); i++)
			res += Character.toString((char) randomGenerator.nextInt(0xFFFF));
		return res;
	}

	private String latinLettersGenerator(int maxSize) {

		String res = "";
		char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
		Random randomGenerator = new Random();

		// if (randomGenerator.nextBoolean())
		for (int i = 0; i < (maxSize == 0 ? randomGenerator
				.nextInt(MAX_TOKEN_SIZE) : maxSize); i++)
			res += Character.toString(chars[randomGenerator
					.nextInt(chars.length)]);
		return res;
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
