package com.loenan.bricks.ldraw.model;

public class Part implements LDrawItem {

	private final String designId;

	private final String description;

	public Part(int designId, String description) {
		this.designId = String.valueOf(designId);
		this.description = description;
	}

	public Part(String designId, String description) {
		this.designId = designId;
		this.description = description;
	}

	public String getDesignId() {
		return designId;
	}

	public String getDescription() {
		return description;
	}

	@Override
	public String getName() {
		return designId + Extensions.DAT;
	}
}
