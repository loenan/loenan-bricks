package com.loenan.bricks.ldraw.model;

import static java.util.Objects.requireNonNull;

public class NamedPart implements LDrawItem {

	private final String baseName;

	public NamedPart(String baseName) {
		this.baseName = requireNonNull(baseName);
	}

	@Override
	public String getName() {
		return baseName + Extensions.DAT;
	}
}
