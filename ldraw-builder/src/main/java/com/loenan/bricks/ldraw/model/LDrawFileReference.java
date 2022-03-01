package com.loenan.bricks.ldraw.model;

public class LDrawFileReference implements LDrawItem {

	private final String fileName;

	public LDrawFileReference(String fileName) {
		this.fileName = fileName;
	}

	@Override
	public String getName() {
		return fileName;
	}
}
