package com.html5parser.SimplestTreeParser;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import org.w3c.dom.Document;

public class Parser {
	
	public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        
        if(args.length == 1)
        {
        	Parse(new ByteArrayInputStream(args[0].getBytes()));
        }
    }

	public static Document Parse(InputStream stream){
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

////convert String into InputStream
//	InputStream is = new ByteArrayInputStream(str.getBytes());
//
//	// read it with BufferedReader
//	BufferedReader br = new BufferedReader(new InputStreamReader(is));
//
//	String line;
//	while ((line = br.readLine()) != null) {
//		System.out.println(line);
//	}
//
//	br.close();