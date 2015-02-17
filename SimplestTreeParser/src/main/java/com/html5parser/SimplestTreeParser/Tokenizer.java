package com.html5parser.SimplestTreeParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.w3c.dom.Document;

import com.html5parser.TokenizerStates.Data_state;
import com.html5parser.TokenizerStates.State;

public class Tokenizer {
	
	private TokenizerContext context = new TokenizerContext();
	
	/**
	 * Tokenize a stream.
	 * 
	 * @param stream
	 *            The input stream.
	 * @return a list of tokens.
	 * @throws exception
	 *             invalid stream codification error.
	 */
	public Document Tokenize(InputStream stream) {
		TreeConstructor treeConstructor = context.getTreeConstructor();
		State state = context.getState();
		// BufferedReader in = new BufferedReader(new
		// InputStreamReader(url.openStream(), "UTF-8"));
	
		BufferedReader in;
		try {
			in = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
			
			int currentChar = 0;
			while ((currentChar = in.read()) != -1) {
				context.setCurrentChar(currentChar);
				state.process(context);
				//state = state.nextState();
			}

			// EOF Procedure
			// Send value -1 for EOF
			context.setCurrentChar(-1);
			state.process(context);

			// return treeConstructor
			// .ProcessToken(new Token(TokenType.end_of_file, null));

			// int currentChar = 0;
			// while ((currentChar = in.read()) != -1) {
			// //// converts int to character
			// // char c = (char)value;
			//
			// switch (Parser.currentState) {
			// case Data_state:
			// Data_state(currentChar);
			// break;
			// case Character_reference_in_data_state:
			//
			// break;
			// case RCDATA_state:
			//
			// break;
			// case Tag_open_state:
			//
			// break;
			// case Tag_name_state:
			//
			// break;
			// case Self_closing_start_tag_state:
			//
			// break;
			// default:
			// break;
			// }
			//
			// }

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return treeConstructor.getDocument();

	}

}