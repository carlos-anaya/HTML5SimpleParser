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

	private final int MAX_TOKEN_SIZE = 20;
	private Parser parser;
	private final String MINIMAL_DOM = "^(<html><head/><body/></html>)$";
	private final String TITLE_REG_EXP = "((<title>(.)*</title>)|(<title/>))?";
	private final String TITLE_IN_HEAD_DOM = "^(<html><head>)" + TITLE_REG_EXP
			+ "(</head><body/></html>)$";
	private final String TITLE_IN_BODY_DOM = "^(<html><head/><body>)"
			+ TITLE_REG_EXP + "(</body></html>)$";
	private final String TITLE_MIXED_DOM = "^(<html><head>)" + TITLE_REG_EXP
			+ "(</head><body>)" + TITLE_REG_EXP + "(</body></html>)$";
	private String serializedDoc;

	@Before
	public void setUp() throws Exception {
		parser = new Parser();
	}

	/******************
	 * ASSERT MINIMAL DOM TESTS
	 ******************/

	@Test
	public final void whenEmptyStringIsUsedThenProducesMinimalDOM() {
		System.out
				.print("\n~~~~ whenEmptyStringIsUsedThenProducesMinimalDOM ~~~~\n\n");
		String inputString = emptyStringGenerator(0);
		serializedDoc = SerializeDocument(parser.parse(inputString));

		assertTrue("Minimal DOM", serializedDoc.matches(MINIMAL_DOM));
	}

	@Test
	public final void whenHtmlHeadBodyTagsAreUsedThenProducesMinimalDOM() {
		System.out
				.print("\n~~~~ whenHtmlHeadBodyTagsAreUsedThenProducesMinimalDOM ~~~~\n\n");
		String inputString = htmlHeadBodyGenerator(0, false);
		serializedDoc = SerializeDocument(parser.parse(inputString));

		assertTrue("Minimal DOM", serializedDoc.matches(MINIMAL_DOM));
	}

	@Test
	public final void whenHtmlHeadBodyTagsWithEmptyStringAreUsedThenProducesMinimalDOM() {
		System.out
				.print("\n~~~~ whenHtmlHeadBodyTagsWithEmptyStringAreUsedThenProducesMinimalDOM ~~~~\n\n");
		String inputString = htmlHeadBodyGenerator(0, true);
		serializedDoc = SerializeDocument(parser.parse(inputString));

		assertTrue("Minimal DOM", serializedDoc.matches(MINIMAL_DOM));
	}

	@Test
	public final void whenEmptyStringInsideHtmlTagIsUsedThenProducesMinimalDOM() {
		System.out
				.print("\n~~~~ whenEmptyStringInsideHtmlTagIsUsedThenProducesMinimalDOM ~~~~\n\n");
		String inputString = emptyStringInsideTagGenerator(0, "<html>");
		serializedDoc = SerializeDocument(parser.parse(inputString));

		assertTrue("Minimal DOM", serializedDoc.matches(MINIMAL_DOM));
	}
	
	@Test
	public final void whenValidEndTagIsUsedThenProducesMinimalDOM() {
		System.out
				.print("\n~~~~ whenValidEndTagIsUsedThenProducesMinimalDOM ~~~~\n\n");
		String inputString = validEndTagGenerator(0);
		serializedDoc = SerializeDocument(parser.parse(inputString));

		assertTrue("Minimal DOM", serializedDoc.matches(MINIMAL_DOM));
	}
	
	@Test
	public final void whenUnclosedValidStartTagAfterHtmlHeadBodyTagsIsUsedThenProducesMinimalDOM() {
		System.out
				.print("\n~~~~ whenUnclosedValidStartTagAfterHtmlHeadBodyTagsIsUsedThenProducesMinimalDOM ~~~~\n\n");
		String inputString = htmlHeadBodyGenerator(0, false)+unclosedValidStartTagGenerator(0);
		serializedDoc = SerializeDocument(parser.parse(inputString));

		assertTrue("Minimal DOM", serializedDoc.matches(MINIMAL_DOM));
	}

	@Test
	public final void whenUnclosedValidStartTagIsUsedThenProducesMinimalDOM() {
		System.out
				.print("\n~~~~ whenUnclosedValidStartTagIsUsedThenProducesMinimalDOM ~~~~\n\n");
		String inputString = unclosedValidStartTagGenerator(0);
		serializedDoc = SerializeDocument(parser.parse(inputString));

		assertTrue("Minimal DOM", serializedDoc.matches(MINIMAL_DOM));
	}

	/******************
	 * ASSERT TITLE TESTS
	 ******************/

	@Test
	public final void whenTitleInHeadisUsedThenProducesMinimalDOMPlusTitle() {
		System.out
				.print("\n~~~~ whenTitleInHeadisUsedThenProducesMinimalDOMPlusTitle ~~~~\n\n");
		String inputString = titleInHeadGenerator(0);
		serializedDoc = SerializeDocument(parser.parse(inputString));

		assertTrue("Title DOM", serializedDoc.matches(TITLE_IN_HEAD_DOM));
	}
	
	@Test
	public final void whenTitleInHeadWithoutEndTagisUsedThenProducesMinimalDOMPlusTitle() {
		System.out
				.print("\n~~~~ whenTitleInHeadWithoutEndTagisUsedThenProducesMinimalDOMPlusTitle ~~~~\n\n");
		String inputString = titleInHeadWithoutEndTagGenerator(0);
		serializedDoc = SerializeDocument(parser.parse(inputString));

		assertTrue("Title DOM", serializedDoc.matches(TITLE_IN_HEAD_DOM));
	}

	@Test
	public final void whenTitleInBodyisUsedThenProducesMinimalDOMPlusTitle() {
		System.out
				.print("\n~~~~ whenTitleInBodyisUsedThenProducesMinimalDOMPlusTitle ~~~~\n\n");
		String inputString = titleInBodyGenerator(0);
		serializedDoc = SerializeDocument(parser.parse(inputString));

		assertTrue("Title DOM", serializedDoc.matches(TITLE_IN_BODY_DOM));
	}
	
	@Test
	public final void whenTitleInBodyWithoutEndTagisUsedThenProducesMinimalDOMPlusTitle() {
		System.out
				.print("\n~~~~ whenTitleInBodyWithoutEndTagisUsedThenProducesMinimalDOMPlusTitle ~~~~\n\n");
		String inputString = titleInBodyWithoutEndTagGenerator(0);
		serializedDoc = SerializeDocument(parser.parse(inputString));

		assertTrue("Title DOM", serializedDoc.matches(TITLE_IN_BODY_DOM));
	}
	
	@Test
	public final void whenTitleInBodyWithoutEndTagAndTextAfterisUsedThenProducesMinimalDOMPlusTitle() {
		System.out
				.print("\n~~~~ whenTitleInBodyWithoutEndTagisUsedThenProducesMinimalDOMPlusTitle ~~~~\n\n");
		String inputString = "<body><title></body>";
		serializedDoc = SerializeDocument(parser.parse(inputString));

		assertTrue("Title DOM", serializedDoc.matches(TITLE_IN_BODY_DOM));
	}

	@Test
	public final void whenMultipleTitlesAreUsedThenProducesMinimalDOMPlusTitle() {
		System.out
				.print("\n~~~~ whenMultipleTitlesAreUsedThenProducesMinimalDOMPlusTitle ~~~~\n\n");
		String inputString = titleInHeadGenerator(0) + titleInBodyGenerator(0)
				+ titleGenerator(0);
		serializedDoc = SerializeDocument(parser.parse(inputString));

		assertTrue("Title DOM", serializedDoc.matches(TITLE_MIXED_DOM));
	}

	@Test
	public final void whenTitleAfterHeadisUsedThenProducesMinimalDOMPlusTitle() {
		System.out
				.print("\n~~~~ whenTitleAfterHeadisUsedThenProducesMinimalDOMPlusTitle ~~~~\n\n");
		String inputString = titleAfterHeadGenerator(0);
		serializedDoc = SerializeDocument(parser.parse(inputString));

		assertTrue("Title DOM", serializedDoc.matches(TITLE_IN_HEAD_DOM));
	}

	/******************
	 * ASSERT EXCEPTION TESTS
	 ******************/

	@Test(expected = RuntimeException.class)
	public final void whenInvalidEndTagIsUsedThenExceptionIsThrown() {
		System.out
				.print("\n~~~~ whenInvalidEndTagIsUsedThenExceptionIsThrown ~~~~\n\n");
		String inputString = invalidEndTagGenerator(0);
		SerializeDocument(parser.parse(inputString));
	}

	@Test(expected = RuntimeException.class)
	public final void whenUnclosedInvalidStartTagIsUsedThenProducesExceptionIsThrown() {
		System.out
				.print("\n~~~~ whenUnclosedInvalidStartTagIsUsedThenProducesExceptionIsThrown ~~~~\n\n");
		String inputString = unclosedInvalidStartTagGenerator(0);
		SerializeDocument(parser.parse(inputString));
	}

	@Test(expected = RuntimeException.class)
	public final void whenNonEmptyStringIsUsedThenExceptionIsThrown() {
		System.out
				.print("\n~~~~ whenNonEmptyStringIsUsedThenExceptionIsThrown ~~~~\n\n");
		String inputString = latinLettersGenerator(0);
		SerializeDocument(parser.parse(inputString));
	}

	@Test(expected = RuntimeException.class)
	public final void whenEmptyStringInsideHeadTagIsUsedThenExceptionIsThrown() {
		System.out
				.print("\n~~~~ whenEmptyStringInsideHeadTagIsUsedThenExceptionIsThrown ~~~~\n\n");
		String inputString = emptyStringInsideTagGenerator(0, "<head>");
		serializedDoc = SerializeDocument(parser.parse(inputString));
	}
	
	@Test(expected = RuntimeException.class)
	public final void whenTextAfterHtmlEndTagIsUsedThenExceptionIsThrown() {
		System.out
				.print("\n~~~~ whenTextAfterHtmlEndTagIsUsedThenExceptionIsThrown ~~~~\n\n");
		String inputString = htmlHeadBodyGenerator(0, false) + latinLettersGenerator(0);
		serializedDoc = SerializeDocument(parser.parse(inputString));
	}

	/******************
	 * GENERATORS
	 ******************/

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
					.nextInt(MAX_TOKEN_SIZE) + 1 : maxSize); i++)
				res += Character.toString(chars[randomGenerator
						.nextInt(chars.length)]);
		return res;
	}

	private String htmlHeadBodyGenerator(int maxSize, boolean appendEmptyString) {

		String res = "";
		String[] tags = new String[] { "<html", "<head", "<body", "</html",
				"</head", "</body", };
		Random randomGenerator = new Random();

		// if (randomGenerator.nextBoolean())
		for (int i = 0; i < (maxSize == 0 ? randomGenerator
				.nextInt(MAX_TOKEN_SIZE) + 1 : maxSize); i++)
			res += tags[randomGenerator.nextInt(tags.length)]
					+ (appendEmptyString ? emptyStringGenerator(0) : "") + ">";
		return res;
	}

	private String emptyStringInsideTagGenerator(int maxSize, String tag) {
		String res = tag + emptyStringGenerator(0)
				+ htmlHeadBodyGenerator(0, false);

		return res;
	}

	private String titleInHeadGenerator(int maxSize) {
		return "<head>" + titleGenerator(0) + "</head>";
	}
	
	private String titleInHeadWithoutEndTagGenerator(int maxSize) {
		return "<head>" + titleGeneratorStartTag(0) ;
	}

	private String titleAfterHeadGenerator(int maxSize) {
		return "<head/>" + titleGenerator(0);
	}

	private String titleInBodyGenerator(int maxSize) {
		return "<body>" + titleGenerator(0) + "</body>";
	}
	
	private String titleInBodyWithoutEndTagGenerator(int maxSize) {
		return "<body>" + titleGeneratorStartTag(0) + "</body>";
	}

	private String titleGenerator(int maxSize) {

		String res = "";
		res += "<title>" + latinLettersGenerator(maxSize) + "</title>";
		return res;
	}
	
	private String titleGeneratorStartTag(int maxSize) {

		String res = "";
		res += "<title>" + latinLettersGenerator(maxSize);
		return res;
	}

	private String validEndTagGenerator(int maxSize) {
		String res = "</" + latinLettersGenerator(0)
				+ unicodeStringGenerator(maxSize).replace(">", "") + ">";
		return res;
	}

	private String unclosedValidStartTagGenerator(int maxSize) {
		String res = "<" + latinLettersGenerator(0);
		return res;
	}

	private String unclosedInvalidStartTagGenerator(int maxSize) {
		String res = "<" + unicodeStringGenerator(maxSize).replace(">", "");
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
				.nextInt(MAX_TOKEN_SIZE) + 1 : maxSize); i++)
			res += Character.toString((char) randomGenerator.nextInt(0xFFFF));
		return res;
	}

	private String latinLettersGenerator(int maxSize) {

		String res = "";
		char[] chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTWXYZ".toCharArray();
		Random randomGenerator = new Random();

		// if (randomGenerator.nextBoolean())
		for (int i = 0; i < (maxSize == 0 ? randomGenerator
				.nextInt(MAX_TOKEN_SIZE) + 1 : maxSize); i++)
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
