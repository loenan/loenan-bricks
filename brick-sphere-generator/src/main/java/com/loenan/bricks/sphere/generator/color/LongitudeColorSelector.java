package com.loenan.bricks.sphere.generator.color;

import com.loenan.bricks.ldraw.color.Color;
import com.loenan.bricks.ldraw.color.SolidColor;
import org.springframework.stereotype.Component;

import static java.lang.Math.floorMod;

@Component
public class LongitudeColorSelector extends CoordinatesColorSelector {

	private static final Color[] COLORS = {
			SolidColor.ORANGE,
			SolidColor.RED,
			SolidColor.BLUE,
			SolidColor.GREEN,
			SolidColor.YELLOW,
			SolidColor.TAN,
	};

	public static final String SCHEME = "longitude";

	@Override
	public String getScheme() {
		return SCHEME;
	}

	@Override
	public Color selectColor(double longitude, double latitude) {
		return COLORS[floorMod((int)(180 + longitude / 30), COLORS.length)];
	}
}
