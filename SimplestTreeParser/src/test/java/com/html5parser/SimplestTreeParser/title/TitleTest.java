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
public class TitleTest {
	
	private Parser parser;
	
	private String input;
 
	//parameters pass via this constructor
	public TitleTest(String input) {
		this.input = input;
	}
 
	//Declares parameters here
	@Parameters(name = "Test {0}")
	public static Iterable<Object[]> data1() {
		return Arrays.asList(new Object[][] { 
			{ "<html><head><title/></head></body></html>"},
			{ "<html><head><title></title></head></body></html>"},
			{ "<html><head><title>A title</title></head></body></html>"},
			{ "<html><head><title > A title </title ></head></body></html>"},
			
			//Scripts not implemented yet
			{ "<html><head><title>&</title></head></body></html>"},
			
			//Parse errors
			{ "<html><head><title></title><title></title></head></body></html>"},
			{ "<html><head><title/></title/></head></body></html>"},
			
			
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
		
		//Assert head has zero or one children 
		assertTrue(head.getChildNodes().getLength() <= 1);
		
		if(head.getChildNodes().getLength() ==1 ){
			//Assert title has less than 2 nodes
			Node title = head.getFirstChild();
			assertTrue("Total nodes: "+title.getChildNodes().getLength(),title.getChildNodes().getLength() < 2);
			
			if(title.getChildNodes().getLength() != 0 ){
				Node text = title.getFirstChild();
				assertEquals("node type should be text, current:"+ text.getNodeType(), text.getNodeType(), html.getNodeType());
				
			}
			
			
		}
		
		//Assert body have no children
		assertTrue(body.getChildNodes().getLength() == 0);
		
	}


	
}
