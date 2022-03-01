package com.loenan.bricks.ldraw.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Collections.unmodifiableList;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;

public class LDrawFile implements LDrawItem {

	private final String fileName;
	private final List<CommandLine> commandLines;

	public LDrawFile(String fileName, List<CommandLine> commandLines) {
		this.fileName = requireNonNull(fileName);
		this.commandLines = unmodifiableList(Stream.concat(
			Stream.of(new FileMetaCommand(fileName)),
			commandLines.stream())
			.collect(toList()));
	}

	public LDrawFile(List<CommandLine> commandLines) {
		this.fileName = commandLines.stream()
			.findFirst()
			.filter(FileMetaCommand.class::isInstance)
			.map(FileMetaCommand.class::cast)
			.map(FileMetaCommand::getParameters)
			.orElse(null);
		this.commandLines = unmodifiableList(new ArrayList<>(commandLines));
	}

	@Override
	public String getName() {
		return fileName;
	}

	public List<CommandLine> getCommandLines() {
		return commandLines;
	}
}
