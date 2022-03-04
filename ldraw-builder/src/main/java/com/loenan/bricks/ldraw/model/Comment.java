package com.loenan.bricks.ldraw.model;

import static java.util.Objects.requireNonNull;

public class Comment implements CommandLine {

	private final String comment;

	public Comment(String comment) {
		this.comment = requireNonNull(comment);
	}

	public String getComment() {
		return comment;
	}

	@Override
	public String getLine() {
		return "0 " + comment;
	}

	@Override
	public String toString() {
		return getLine();
	}
}
