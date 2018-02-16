package com.loenan.bricks.ldraw.color;

import static java.util.Objects.requireNonNull;

public class Color {

    private final int colorId;

    private final String rgb;

    public Color(int colorId, String rgb) {
        this.colorId = colorId;
        this.rgb = requireNonNull(rgb);
    }

    public int getColorId() {
        return colorId;
    }

    public String getRgb() {
        return rgb;
    }
}
