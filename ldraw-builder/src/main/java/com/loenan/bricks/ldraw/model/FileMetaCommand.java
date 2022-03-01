package com.loenan.bricks.ldraw.model;

public class FileMetaCommand extends MetaCommand {

	public FileMetaCommand(String fileName) {
		super(MetaCommands.FILE_COMMAND, fileName);
	}
}
