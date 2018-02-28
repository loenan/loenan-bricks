package com.loenan.bricks.ldraw.model;

import java.util.List;
import java.util.stream.Stream;

import static java.util.Collections.singletonList;
import static java.util.Collections.unmodifiableList;
import static java.util.Objects.requireNonNull;

public class LDrawFile implements LDrawItem {

	private final String baseName;

	private final List<CommandLine> commandLines;

	public LDrawFile(String baseName, List<CommandLine> commandLines) {
		this.baseName = requireNonNull(baseName);
		this.commandLines = unmodifiableList(commandLines);
	}

	public String getBaseName() {
		return baseName;
	}

	@Override
	public String getName() {
		return baseName + Extensions.LDR;
	}

	public Stream<CommandLine> getCommandLines() {
		return Stream.of(singletonList(new FileMetaCommand(baseName)), commandLines)
				.flatMap(List::stream);
	}
}
