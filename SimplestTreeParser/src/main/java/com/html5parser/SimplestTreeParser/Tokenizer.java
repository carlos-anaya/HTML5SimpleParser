package com.html5parser.SimplestTreeParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import com.html5parser.TokenizerStates.Data_state;
import com.html5parser.TokenizerStates.State;

public class Tokenizer {

	private TokenizerContext context;
	
	public Tokenizer() {
		context = new TokenizerContext();
		context.setState(new Data_state());
	}

	/**
	 * Tokenize a stream.
	 * 
	 * @param stream
	 *            The input stream.
	 * @return a list of tokens.
	 * @throws exception
	 *             invalid stream codification error.
	 */
	public void Tokenize(InputStream stream) {
		// TreeConstructor treeConstructor = context.getTreeConstructor();
		State state = context.getState();
		// BufferedReader in = new BufferedReader(new
		// InputStreamReader(url.openStream(), "UTF-8"));

		BufferedReader in;
		try {
			in = new BufferedReader(new InputStreamReader(stream, "UTF-8"));

			int currentChar = in.read();
			while (currentChar != -1) {
				context.setCurrentChar(currentChar);
				state = context.getState();
				if (!state.process(context))
					currentChar = in.read();
			}

			// EOF Procedure
			// Send value -1 for EOF
			context.setCurrentChar(-1);
			state = context.getState();
			while (state.process(context)) {
				//context.setCurrentChar(-1);
				state = context.getState();
			}

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}