package com.loenan.bricks.sphere.generator.color;

import com.loenan.bricks.ldraw.color.Color;
import com.loenan.bricks.ldraw.color.ColorSet;
import com.loenan.bricks.ldraw.color.SolidColor;
import org.springframework.stereotype.Component;

import static java.lang.Math.floorMod;

@Component
public class LatitudeColorScheme extends CoordinatesColorScheme {

	public static final String SCHEME_NAME = "latitude";

	private static final Color[] COLORS = {
			SolidColor.RED,
			SolidColor.ORANGE,
			SolidColor.YELLOW,
			SolidColor.GREEN,
			SolidColor.BLUE,
			SolidColor.DARK_BLUE,
	};

	public LatitudeColorScheme() {
		super(SCHEME_NAME);
	}

	@Override
	public Color selectColor(double longitude, double latitude, ColorSet availableColors) {
		return COLORS[floorMod((int)((90 + latitude) / 15), COLORS.length)];
	}
}
