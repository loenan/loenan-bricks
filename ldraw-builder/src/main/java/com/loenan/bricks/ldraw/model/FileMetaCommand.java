package com.loenan.bricks.ldraw.model;

public class FileMetaCommand extends MetaCommand {

	public FileMetaCommand(String modelBaseName) {
		super("FILE", modelBaseName + Extensions.LDR);
	}
}
