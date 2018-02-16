package com.loenan.bricks.ldraw.model;

import static java.util.Objects.requireNonNull;

public class MultiPartDocument {

    private final LDrawFile mainModel;

    public MultiPartDocument(LDrawFile mainModel) {
        this.mainModel = requireNonNull(mainModel);
    }

    public LDrawFile getMainModel() {
        return mainModel;
    }
}
