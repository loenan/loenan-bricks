package com.loenan.bricks.sphere.generator.color;

import com.loenan.bricks.ldraw.color.Color;
import com.loenan.bricks.ldraw.color.ColorSet;
import com.loenan.bricks.ldraw.geometry.Vector;
import com.loenan.bricks.sphere.generator.geometry.CubeFace;

import static com.loenan.bricks.sphere.generator.geometry.MathUtil.sq;
import static java.lang.Math.PI;
import static java.lang.Math.asin;
import static java.lang.Math.atan2;
import static java.lang.Math.sqrt;

public abstract class CoordinatesColorSelector implements ColorSelector {

	private static final double HALF_TURN_DEGREES = 180;

	private final String colorScheme;

	protected CoordinatesColorSelector(String colorScheme) {
		this.colorScheme = colorScheme;
	}

	@Override
	public String getScheme() {
		return colorScheme;
	}

	@Override
	public Color selectColor(CubeFace face, Vector position, ColorSet availableColors) {
		double radius = sqrt(sq(position.getX()) + sq(position.getY()) + sq(position.getZ()));
		double longitude = atan2(position.getZ(), position.getX()) / PI * HALF_TURN_DEGREES;
		double latitude = asin(- position.getY() / radius) / PI * HALF_TURN_DEGREES;

		return selectColor(longitude, latitude, availableColors);
	}

	public abstract Color selectColor(double longitude, double latitude, ColorSet availableColors);
}
