package com.loenan.bricks.ldraw.model;

import com.loenan.bricks.ldraw.reader.LineReader;

public interface CommandLine {

	String getLine();

	static CommandLine read(LineReader reader) {
		return reader.readToken()
			.map(type -> {
				switch (type) {
					case "0":
						return MetaCommands.readCommentOrMetaCommand(reader);
					case "1":
						return ItemReference.read(reader);
					case "2":
						return Line.read(reader);
					case "3":
						return Triangle.read(reader);
					case "4":
						return Quad.read(reader);
					case "5":
						return OptionalLine.read(reader);
					default:
						throw new IllegalArgumentException("Unknown command type: " + type);
				}
			})
			.orElse(null);
	}
}
