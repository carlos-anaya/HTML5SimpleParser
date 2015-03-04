package com.html5parser.XMLValidator;

import org.w3c.dom.Document;

import com.html5parser.SimplestTreeParser.Parser;

public class ParserTest {
	public static void main(String[] args){
		Parser parser = new Parser();
		Document dom = parser.parse("<html><title></html");
		
		try{
			XMLValidator validator = new XMLValidator();
			validator.validate(dom,"src/test/java/com/html5parser/XMLValidator/minimalDom.xsd");
			System.out.println("valid");
		}catch(Exception e){
//			e.printStackTrace();
			System.out.println("Invalid");
		}
	}

}
