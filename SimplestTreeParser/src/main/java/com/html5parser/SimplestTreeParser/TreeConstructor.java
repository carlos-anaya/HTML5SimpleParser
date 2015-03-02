package com.html5parser.SimplestTreeParser;

import com.html5parser.InsertionModes.AfterBody;
import com.html5parser.InsertionModes.AfterHead;
import com.html5parser.InsertionModes.BeforeHTML;
import com.html5parser.InsertionModes.BeforeHead;
import com.html5parser.InsertionModes.InBody;
import com.html5parser.InsertionModes.InHead;
import com.html5parser.InsertionModes.Initial;
import com.html5parser.InsertionModes.Text;

public class TreeConstructor {

	/**
	 * Construct a tree from a list of Tokens.
	 * 
	 * @param tokens
	 *            The list of tokens generated by the tokenizer.
	 * @return Document a DOM object.
	 * @throws Exception
	 *             invalid token.
	 */

	public TreeConstructor() {
		// try {
		// DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		// DocumentBuilder builder = dbf.newDocumentBuilder();
		// doc = builder.newDocument();
		// } catch (ParserConfigurationException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
	}

	// public Document doc = null;

	public void processToken(Token token) {
//		System.out.println(token.getType() + " - " + token.getValue()
//				+ " (insertion mode: " + Parser.currentMode + ")");

		// boolean stopParsing = false;
		// while (!stopParsing) {
		switch (Parser.currentMode) {
		case initial:
			new Initial().process(token, this);
			break;
		case before_html:
			new BeforeHTML().process(token, this);
			break;
		case before_head:
			new BeforeHead().process(token, this);
			break;
		case in_head:
			new InHead().process(token, this);
			break;
		case after_head:
			new AfterHead().process(token, this);
			break;
		case in_body:
			// stopParsing = new InBody().process(token);
			new InBody().process(token, this);
			break;
		case after_body:
			// stopParsing = new InBody().process(token);
			new AfterBody().process(token, this);
			break;
		case text:
			// stopParsing = new InBody().process(token);
			new Text().process(token, this);
			break;
		default:
			// stopParsing = new InBody().process(token);
			break;
		}
		// }
	}

	// public Document getDocument(){
	// return doc;
	// }
}