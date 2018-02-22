package com.loenan.bricks.ldraw.color;

import static java.lang.Math.abs;
import static java.util.Objects.requireNonNull;

public class Color {

	public static final int MAIN_COLOR_ID = 16;

	public static final int EDGE_COLOR_ID = 24;

	public static final Color MAIN_COLOR = new Color(MAIN_COLOR_ID);

	public static final Color EDGE_COLOR = new Color(EDGE_COLOR_ID);

	private final int colorId;

	private final String rgb;

	private final int red;

	private final int green;

	private final int blue;

	/**
	 * Private constructor for special reserved colors, without actual RGB value.
	 */
	private Color(int colorId) {
		this.colorId = colorId;
		this.rgb = null;
		this.red = Integer.MIN_VALUE;
		this.green = Integer.MIN_VALUE;
		this.blue = Integer.MIN_VALUE;
	}

	public Color(int colorId, String rgb) {
		this.colorId = colorId;
		this.rgb = requireNonNull(rgb);
		if (rgb.length() != 6) {
			throw new IllegalArgumentException("Invalid RGB value: " + rgb);
		}
		this.red = Integer.parseInt(rgb.substring(0, 2), 16);
		this.green = Integer.parseInt(rgb.substring(2, 4), 16);
		this.blue = Integer.parseInt(rgb.substring(4, 6), 16);
	}

	public int getColorId() {
		return colorId;
	}

	public String getRgb() {
		return rgb;
	}

	public int getRed() {
		return red;
	}

	public int getGreen() {
		return green;
	}

	public int getBlue() {
		return blue;
	}

	public int getDistanceTo(int red, int green, int blue) {
		if (rgb == null
				|| ! isComponentValid(red)
				|| ! isComponentValid(green)
				|| ! isComponentValid(blue)) {
			return Integer.MAX_VALUE;
		}

		return abs(this.red - red)
				+ abs(this.green - green)
				+ abs(this.blue - blue);
	}

	private boolean isComponentValid(int c) {
		return 0 <= c && c < 256;
	}
}
