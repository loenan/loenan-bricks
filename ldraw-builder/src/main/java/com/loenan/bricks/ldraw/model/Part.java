package com.loenan.bricks.ldraw.model;

public class Part implements LDrawItem {

    private final int designId;

    private final String description;

    public Part(int designId, String description) {
        this.designId = designId;
        this.description = description;
    }

    public int getDesignId() {
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
