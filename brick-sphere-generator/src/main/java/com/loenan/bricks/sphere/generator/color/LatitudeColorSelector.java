package com.loenan.bricks.sphere.generator.color;

import com.loenan.bricks.ldraw.color.Color;
import com.loenan.bricks.ldraw.color.ColorSet;
import com.loenan.bricks.ldraw.color.SolidColor;
import org.springframework.stereotype.Component;

import static java.lang.Math.floorMod;

@Component
public class LatitudeColorSelector extends CoordinatesColorSelector {

	public static final String SCHEME = "latitude";

	private static final Color[] COLORS = {
			SolidColor.ORANGE,
			SolidColor.RED,
			SolidColor.BLUE,
			SolidColor.GREEN,
			SolidColor.YELLOW,
			SolidColor.TAN,
	};

	public LatitudeColorSelector() {
		super(SCHEME);
	}

	@Override
	public Color selectColor(double longitude, double latitude, ColorSet availableColors) {
		return COLORS[floorMod((int)(90 + latitude / 30), COLORS.length)];
	}
}
