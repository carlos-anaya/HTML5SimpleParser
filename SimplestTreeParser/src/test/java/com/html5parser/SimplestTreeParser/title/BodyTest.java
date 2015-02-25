package com.html5parser.SimplestTreeParser.title;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.html5parser.SimplestTreeParser.Parser;

@RunWith(value = Parameterized.class)
public class BodyTest {
	
	private Parser parser;
	
	private String input;
 
	//parameters pass via this constructor
	public BodyTest(String input) {
		this.input = input;
	}
 
	//Declares parameters here
	@Parameters(name = "Test {0}")
	public static Iterable<Object[]> data1() {
		return Arrays.asList(new Object[][] { 
			{ "<html><head/><body></body></html>"},
			{ "<html><head/><body>   </body></html>"},
			{ "<html><head/><body>text</body></html>"},
			{ "<html><head/><body><p></p></body></html>"},
			{ "<html><head/><body><i></i>/body></html>"},
			{ "<html><head/><body><b></b></body></html>"},
			{ "<html><head/><body><p><b><i></i></b></p></body></html>"},
			{ "<html><head/><body><p/><b/><i/></body></html>"},
			{ "<html><head/><body><title>title</title></body></html>"},
			{ "<html><head/><body></body></html>"},
			{ "<html><head/><body></html>"},
			
			
			
			//Parse errors <!DOCTYPE html>,
			{ "<html><head/><body><!DOCTYPE html></body></html>"},
			{ "<html><head/><body><html></body></html>"},
			{ "<html><head/><body><body></body></html>"},
			
			//Scripts not implemented yet
			{ "<html><head/><body><!-- comment --></body></html>"},
			{ "<html><head/><body><frameset></body></html>"},
			{ "<html><head/><body>"},
			{ "<html><head/><body></sarcasm></body></html>"},
			
		});
	}

	@Before
	public void setUp() throws Exception {
		parser = new Parser();
	}
	
	@Test
	public final void tests() {
		
		Document doc = parser.parse(new ByteArrayInputStream((input).getBytes()));
		
		//Assert doc has only one child
		assertTrue(doc.getChildNodes().getLength() == 1);
		
		//Assert this only child is the html tag
		Node html = doc.getLastChild();
		assertTrue("html tag incorrect "+ html.getNodeName(), html.getNodeName().equals("html"));
		
		//Assert doc has two children
		assertTrue(html.getChildNodes().getLength() == 2);
		
		//Assert html has head and body as children
		Node head = html.getFirstChild();
		assertTrue("head tag incorrect: "+ head.getNodeName(), head.getNodeName().equals("head"));
		
		Node body = head.getNextSibling();
		assertTrue("body tag incorrect "+ body.getNodeName(), body.getNodeName().equals("body"));
		
		
		
		//Assert head have no children
		assertTrue(head.getChildNodes().getLength() == 0);
		
	}


	
}
