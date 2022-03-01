package com.loenan.bricks.ldraw.reader;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class LineReader {

	private final String line;
	private final int lineSize;
	private int position = 0;
	private int mark = -1;

	public LineReader(String line) {
		this.line = Objects.requireNonNull(line);
		this.lineSize = line.length();
	}

	public Optional<String> readToken() {
		// skip whitespaces
		while (position < lineSize && isWhitespace(line.charAt(position))) {
			position++;
		}

		int start = position;
		while (position < lineSize && !isWhitespace(line.charAt(position))) {
			position++;
		}

		if (position == start) {
			return Optional.empty();
		} else {
			return Optional.of(line.substring(start, position));
		}
	}

	public Optional<List<String>> readTokens(int tokenCount) {
		List<String> tokens = new ArrayList<>(tokenCount);
		for (int i = 0; i < tokenCount; i++) {
			Optional<String> token = readToken();
			if (!token.isPresent()) {
				return Optional.empty();
			}
			tokens.add(token.get());
		}
		return Optional.of(tokens);
	}

	public String readEndOfLine() {
		// skip whitespaces
		while (position < lineSize && isWhitespace(line.charAt(position))) {
			position++;
		}
		int start = position;
		position = lineSize;
		return line.substring(start);

	}

	/**
	 * Mark the current position.
	 */
	public void mark() {
		mark = position;
	}

	/**
	 * Reset the current position to the previously marked position.
	 */
	public void reset() {
		if (mark < 0) {
			throw new IllegalStateException("No marked position");
		}
		position = mark;
	}

	private boolean isWhitespace(char ch) {
		return ch == ' ' || ch == '\t';
	}
}
