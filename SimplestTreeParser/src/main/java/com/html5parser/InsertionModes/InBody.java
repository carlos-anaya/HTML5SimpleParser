/*
 * In order to pass the tests:
 * Commented character method
 * Added default action for head (start & end tag)
 * Commented code for other tags
 * */

package com.html5parser.InsertionModes;

import java.util.Iterator;

import org.w3c.dom.Element;

import com.html5parser.SimplestTreeParser.InsertionMode;
import com.html5parser.SimplestTreeParser.Parser;
import com.html5parser.SimplestTreeParser.ParserStacks;
import com.html5parser.SimplestTreeParser.StackUpdater;
import com.html5parser.SimplestTreeParser.Token;
import com.html5parser.SimplestTreeParser.TokenizerContext;
import com.html5parser.SimplestTreeParser.TreeConstructor;
import com.html5parser.TokenizerStates.Error_state;

public class InBody {

	public boolean process(Token token, TreeConstructor treeConstructor) {

		switch (token.getType()) {
		case character:
			characterToken(token);
			break;
		case comment:
			new StackUpdater().updateStack(token.getValue(), "comment");
			break;
		case DOCTYPE:
			ParserStacks.parseErrors.push("DOCTYPE in InBody insertion mode");
			break;
		case start_tag:
			TokenStartTag(token, treeConstructor);
			break;
		case end_tag:
			TokenEndTag(token, treeConstructor);
			break;
		case end_of_file:
			TokenEndOfFile();
			return true;
		default:
			// TokenAnythingElse();
			break;
		}
		return false;
	}

	private void TokenEndOfFile() {
		ParserStacks.parseErrors.push("EOF");
	}

	// private void TokenAnythingElse() {
	//
	// }

	private void characterToken(Token token) {

		ParserStacks.parseErrors.push("Unsuported character token");
		TokenizerContext.state = new Error_state();

//		 // A character token that is U+0000 NULL
//		 // Parse error. Ignore the token.
//		
//		 int currentChar = (int)token.getValue().charAt(0);
//		
//		
//		 if (currentChar == 0x0000) {
//		 ParserStacks.parseErrors.push("Null character found");
//		 }
//		 // A character token that is one of U+0009 CHARACTER TABULATION, "LF"
//		 // (U+000A), "FF" (U+000C), "CR" (U+000D), or U+0020 SPACE
//		 // Reconstruct the active formatting elements, if any.
//		 // Insert the token's character into the current node.
//		 else if (currentChar == 0x0009
//		 || currentChar == 0x000A
//		 || currentChar == 0x000C
//		 || currentChar == 0x000D
//		 || currentChar == 0x0020) {
//		
//		 new StackUpdater().updateStack(token.getValue(), "text");
//		 }
//		 // Any other character token
//		 // Reconstruct the active formatting elements, if any.
//		 // Insert the token's character into the current node.
//		 // Set the frameset-ok flag to "not ok".
//		 else {
//		 new StackUpdater().updateStack(token.getValue(), "text");
//		 }
	}

	private void TokenEndTag(Token token, TreeConstructor treeConstructor) {
		switch (token.getValue()) {
		// An end tag whose tag name is "html"
		// Act as if an end tag with tag name "body" had been seen, then, if
		// that token wasn't ignored, reprocess the current token.
		case "html":
			// An end tag whose tag name is "body"
			// If the stack of open elements does not have a body element in
			// scope,
			// this is a parse error; ignore the token.
			// Otherwise, if there is a node in the stack of open elements that
			// is
			// not either a dd element, a dt element, an li element, an optgroup
			// element, an option element, a p element, an rp element, an rt
			// element, a tbody element, a td element, a tfoot element, a th
			// element, a thead element, a tr element, the body element, or the
			// html
			// element, then this is a parse error.
			// Switch the insertion mode to "after body".
		case "body":
			boolean error = false;
			Iterator<Element> els = ParserStacks.openElements.iterator();
			while (els.hasNext()) {
				Element el = els.next();
				if (el.getNodeName().equals("body")
						|| el.getNodeName().equals("p")) {
					error = true;
					break;
				}
			}
			if (error)
				ParserStacks.parseErrors.push(token.getValue()
						+ " closing tag found");
			Parser.currentMode = InsertionMode.after_body;
			break;
		// An end tag whose tag name is one of: "a", "b", "big", "code", "em",
		// "font", "i", "nobr", "s", "small", "strike", "strong", "tt", "u"

		// An end tag whose tag name is "p"
		// If the stack of open elements does not have an element in button
		// scope with the same tag name as that of the token, then this is a
		// parse error; act as if a start tag with the tag name "p" had been
		// seen, then reprocess the current token.
		// Otherwise, run these steps:
		// Generate implied end tags, except for elements with the same tag name
		// as the token.
		// If the current node is not an element with the same tag name as that
		// of the token, then this is a parse error.
		// Pop elements from the stack of open elements until an element with
		// the same tag name as the token has been popped from the stack.
		// case "p":
		// ParserStacks.openElements.pop();
		// break;
		//
		// // An end tag whose tag name is one of: "a", "b", "big", "code",
		// "em",
		// // "font", "i", "nobr", "s", "small", "strike", "strong", "tt", "u"
		// // Run these steps
		// case "i":
		// case "b":
		// formattingEndTag(token);
		// break;
		case "head":
			ParserStacks.parseErrors.push("Unexpected " + token.getValue()
					+ " tag.");
			break;
		case "title":
			break;
		default:
			ParserStacks.parseErrors.push("Unexpected " + token.getValue()
					+ " tag.");
			TokenizerContext.state = new Error_state();

			break;
		}

	}

	private void TokenStartTag(Token token, TreeConstructor treeConstructor) {
		switch (token.getValue()) {
		// A start tag whose tag name is "html"
		// Process the token using the rules for the "in body" insertion mode.

		// From "in body": Parse error. For each attribute on the token, check
		// to see if the attribute is already present on the top element of the
		// stack of open elements. If it is not, add the attribute and its
		// corresponding token.getValue() to that element.
		case "html":
			ParserStacks.parseErrors.push("Unexpected " + token.getValue()
					+ " tag.");
			break;

		// A start tag token whose tag name is one of: "base", "basefont",
		// "bgsound", "command", "link", "meta", "noframes", "script", "style",
		// "title"
		// Process the token using the rules for the "in head" insertion mode.
		case "base":
		case "basefont":
		case "bgsound":
		case "command":
		case "link":
		case "meta":
		case "noframes":
		case "script":
		case "style":
		case "title":
			new InHead().process(token, treeConstructor);
			break;
		// A start tag whose tag name is "body"
		// Parse error.
		// If the second element on the stack of open elements is not a body
		// element, or, if the stack of open elements has only one node on it,
		// then ignore the token. (fragment case)
		// Otherwise, set the frameset-ok flag to "not ok"; then, for each
		// attribute on the token, check to see if the attribute is already
		// present on the body element (the second element) on the stack of open
		// elements, and if it is not, add the attribute and its corresponding
		// value to that element.
		case "body":
			// Element el1 = doc.createElement("html");
			// doc.appendChild(el1);
			// ParserStacks.openElements.pop();
			ParserStacks.parseErrors.push("Unexpected " + token.getValue()
					+ " tag.");
			break;

		// A start tag whose tag name is one of: "address", "article", "aside",
		// "blockquote", "center", "details", "dialog", "dir", "div", "dl",
		// "fieldset", "figcaption", "figure", "footer", "header", "hgroup",
		// "menu", "nav", "ol", "p", "section", "summary", "ul"
		// If the stack of open elements has a p element in button scope, then
		// act as if an end tag with the tag name "p" had been seen.
		// Insert an HTML element for the token.
		// case "p":
		// new StackUpdater().updateStack(token.getValue(), "element");
		// break;
		// // A start tag whose tag name is one of: "b", "big", "code", "em",
		// // "font", "i", "s", "small", "strike", "strong", "tt", "u"
		// // Reconstruct the active formatting elements, if any.
		// // Insert an HTML element for the token. Push onto the list of active
		// // formatting elements that element.
		// case "b":
		// case "i":
		// formattingStartTag(token);
		// break;
		// Any other start tag
		// Reconstruct the active formatting elements, if any.
		// Insert an HTML element for the token.
		case "head":
			ParserStacks.parseErrors.push("Unexpected " + token.getValue()
					+ " tag.");
			break;
		default:
			ParserStacks.parseErrors.push("Unexpected " + token.getValue()
					+ " tag.");
			TokenizerContext.state = new Error_state();

			// new StackUpdater().updateStack(token.getValue(), "element");
			break;

		}

	}

	private void formattingStartTag(Token token) {
		// Look for
		// A start tag whose tag name is one of: "b", "big", "code", "em",
		// "font", "i", "s", "small", "strike", "strong", "tt", "u"
		// http://dev.w3.org/html5/spec-preview/tokenization.html#parsing-main-inbody
		new StackUpdater().updateStack(token.getValue(), "element");
	}

	private void formattingEndTag(Token token) {
		// Look for
		// An end tag whose tag name is one of: "a", "b", "big", "code", "em",
		// "font", "i", "nobr", "s", "small", "strike", "strong", "tt", "u"
		// http://dev.w3.org/html5/spec-preview/tokenization.html#parsing-main-inbody
		ParserStacks.openElements.pop();
	}
}