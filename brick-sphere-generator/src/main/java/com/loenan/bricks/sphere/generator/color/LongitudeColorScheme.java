package com.loenan.bricks.sphere.generator.color;

import com.loenan.bricks.ldraw.color.Color;
import com.loenan.bricks.ldraw.color.ColorSet;
import com.loenan.bricks.ldraw.color.SolidColor;
import org.springframework.stereotype.Component;

import static java.lang.Math.floorMod;

@Component
public class LongitudeColorScheme extends CoordinatesColorScheme {

	public static final String SCHEME_NAME = "longitude";

	private static final Color[] COLORS = {
			SolidColor.ORANGE,
			SolidColor.RED,
			SolidColor.BLUE,
			SolidColor.GREEN,
			SolidColor.YELLOW,
			SolidColor.TAN,
	};

	public LongitudeColorScheme() {
		super(SCHEME_NAME);
	}

	@Override
	public Color selectColor(double longitude, double latitude, ColorSet availableColors) {
		return COLORS[floorMod((int)(180 + longitude / 30), COLORS.length)];
	}
}
