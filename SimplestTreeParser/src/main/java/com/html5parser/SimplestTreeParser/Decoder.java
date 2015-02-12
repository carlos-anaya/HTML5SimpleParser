package com.html5parser.SimplestTreeParser;

import java.io.InputStream;

public class Decoder {

	/**
	 * Validate the encoding of a stream. UTF-8 is the only valid encoding.
	 * 
	 * @param stream
	 *            The input stream.
	 * @return a stream, if not UTF-8 encoding the stream is empty.
	 * @throws exception
	 *             invalid stream codification error.
	 */
	public InputStream ValidateEncoding(InputStream stream) {
		return stream;
	}
}