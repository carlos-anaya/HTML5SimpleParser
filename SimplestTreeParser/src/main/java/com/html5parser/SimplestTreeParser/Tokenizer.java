package com.html5parser.SimplestTreeParser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Tokenizer {

	/**
	 * Tokenize a stream.
	 * 
	 * @param stream
	 *            The input stream.
	 * @return a list of tokens.
	 * @throws exception
	 *             invalid stream codification error.
	 */
	public List<Token> Tokenize(InputStream stream) {
		List<Token> tokens = new ArrayList<Token>();

		/*
		 * Consume the stream and generate tokens.
		 * 
		 * Following lines for example.
		 */
		// tokens.add(new Token(TokenType.start_tag, "html"));
		// tokens.add(new Token(TokenType.start_tag, "head"));
		// tokens.add(new Token(TokenType.end_tag, "head"));
		// tokens.add(new Token(TokenType.start_tag, "body"));
		// tokens.add(new Token(TokenType.end_tag, "body"));
		// tokens.add(new Token(TokenType.end_tag, "html"));
		return tokens;
	}

}