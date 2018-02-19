package com.loenan.bricks.ldraw.model;

import java.util.List;
import java.util.stream.Stream;

import static java.util.Collections.singletonList;
import static java.util.Collections.unmodifiableList;
import static java.util.Objects.requireNonNull;

public class LDrawFile implements LDrawItem {

	private final String baseName;

	private final List<Comment> comments;

	private final List<ItemReference> itemReferences;

	public LDrawFile(String baseName, List<Comment> comments, List<ItemReference> itemReferences) {
		this.baseName = requireNonNull(baseName);
		this.comments = unmodifiableList(comments);
		this.itemReferences = unmodifiableList(itemReferences);
	}

	public String getBaseName() {
		return baseName;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public List<ItemReference> getItemReferences() {
		return itemReferences;
	}

	@Override
	public String getName() {
		return baseName + Extensions.LDR;
	}

	public Stream<CommandLine> getCommandLines() {
		return Stream.of(singletonList(new FileMetaCommand(baseName)), comments, itemReferences)
				.flatMap(List::stream);
	}
}
