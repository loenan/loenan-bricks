package com.loenan.bricks.sphere.generator.color;

import com.loenan.bricks.ldraw.color.Color;
import com.loenan.bricks.ldraw.color.ColorSet;
import com.loenan.bricks.ldraw.color.SolidColor;
import com.loenan.bricks.ldraw.geometry.Vector;
import com.loenan.bricks.sphere.generator.geometry.CubeFace;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(3)
public class FaceColorScheme implements ColorScheme {

	public static final String SCHEME_NAME = "faces";

	@Override
	public String getSchemeName() {
		return SCHEME_NAME;
	}

	@Override
	public Color selectColor(CubeFace face, Vector position, ColorSet availableColors) {
		switch (face) {
			case TOP:
				return SolidColor.RED;
			case BOTTOM:
				return SolidColor.ORANGE;
			case FRONT:
				return SolidColor.BLUE;
			case BACK:
				return SolidColor.GREEN;
			case LEFT:
				return SolidColor.YELLOW;
			case RIGHT:
				return SolidColor.TAN;
			default:
				return SolidColor.BLACK;
		}
	}
}
