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
					case "3":
					case "4":
					case "5":
						throw new UnsupportedOperationException("Command type not supported: " + type);
					default:
						throw new IllegalArgumentException("Unknown command type: " + type);
				}
			})
			.orElse(null);
	}
}
