package com.html5parser.SimplestTreeParser;

import java.io.InputStream;
import java.util.List;

import org.w3c.dom.Document;

public class Parser {
	
	public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
    }

	public Document parse(InputStream stream){
		Document doc = null;
	try{
		stream = new Decoder().ValidateEncoding(stream);
		List<Token> tokens = new Tokenizer().Tokenize(stream);
		doc = new TreeConstructor().ConstructDOM(tokens);
	}
	finally {}

	
	return doc;
	}
}