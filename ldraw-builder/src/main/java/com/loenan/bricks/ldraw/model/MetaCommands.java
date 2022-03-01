package com.loenan.bricks.ldraw.model;

import com.loenan.bricks.ldraw.reader.LineReader;
import org.apache.commons.lang3.StringUtils;

public interface MetaCommands {

	String STEP_COMMAND = "STEP";
	String FILE_COMMAND = "FILE";
	String NOFILE_COMMAND = "NOFILE";

	MetaCommand STEP = new MetaCommand(STEP_COMMAND);
	MetaCommand NOFILE = new MetaCommand(NOFILE_COMMAND);

	static CommandLine readCommentOrMetaCommand(LineReader reader) {
		reader.mark();
		return reader.readToken()
			.<CommandLine>map(command -> {
				String parameters = reader.readEndOfLine();
				switch (command) {
					case STEP_COMMAND:
					case NOFILE_COMMAND:
						if (StringUtils.isBlank(parameters)) {
							return new MetaCommand(command);
						}
						break;
					case FILE_COMMAND:
						return new FileMetaCommand(parameters);
				}
				reader.reset();
				return null;
			})
			.orElseGet(() -> new Comment(reader.readEndOfLine()));
	}
}
