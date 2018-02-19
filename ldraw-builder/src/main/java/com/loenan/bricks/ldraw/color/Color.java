package com.loenan.bricks.ldraw.color;

import static java.util.Objects.requireNonNull;

public class Color {

	public static final Color MAIN_COLOR = new Color(16, "");

	public static final Color EDGE_COLOR = new Color(24, "");

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
