package com.loenan.bricks.sphere.generator.color;

import com.loenan.bricks.ldraw.color.Color;
import com.loenan.bricks.ldraw.color.ColorSet;
import com.loenan.bricks.ldraw.color.SolidColor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import static java.lang.Math.floorMod;

@Component
@Order(5)
public class LongitudeColorScheme extends CoordinatesColorScheme {

	public static final String SCHEME_NAME = "longitude";

	private static final Color[] COLORS = {
			SolidColor.RED,
			SolidColor.ORANGE,
			SolidColor.YELLOW,
			SolidColor.GREEN,
			SolidColor.BLUE,
			SolidColor.DARK_BLUE,
	};

	public LongitudeColorScheme() {
		super(SCHEME_NAME);
	}

	@Override
	public Color selectColor(double longitude, double latitude, ColorSet availableColors) {
		return COLORS[floorMod((int)((180 + longitude) / 15), COLORS.length)];
	}
}
