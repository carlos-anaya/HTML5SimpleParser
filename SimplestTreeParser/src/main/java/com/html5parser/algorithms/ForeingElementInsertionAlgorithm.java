package com.html5parser.algorithms;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.html5parser.SimplestTreeParser.DocumentGenerator;
import com.html5parser.SimplestTreeParser.ParserStacks;
import com.html5parser.SimplestTreeParser.Token;
import com.html5parser.constants.Namespace;

public class ForeingElementInsertionAlgorithm {
	
	public static void insertHTMLElement(Token token){
		insertForeignElement( token, Namespace.HTML);
	}
	
	public static Element insertForeignElement(Token token, String namespace){
		Node adjustedInsertionLocation = getAppropiatePlaceForInsertingANode(null);
		
		//TODO: implement: Create an element for the token algorithm and replace the following
		Element element = DocumentGenerator.getDocument().createElement(token.getValue());
		
		adjustedInsertionLocation.appendChild(element);
		
		ParserStacks.openElements.push(element);
		
		return element;
	}
	
	private static Node getAppropiatePlaceForInsertingANode(Node overrideTarget){
		Node target;
		
		if(overrideTarget != null) target = overrideTarget;
		else target = ParserStacks.openElements.peek();
		
		
		
		//if(fosterParenting)
		//TODO: foster parenting handling
		
		//else
		Node adjustedInsertionLocation = target;
		
		if(adjustedInsertionLocation.getNodeName().equals("template")){
			throw new UnsupportedOperationException("getAppropiatePlaceForInsertingANode() in a template node  not implemented yet (ForeingElementInsertionAlgorithm)");
		}
		
		return 	adjustedInsertionLocation;
	}
}
