package com.loenan.bricks.sphere.generator.color;

import com.loenan.bricks.ldraw.color.Color;
import com.loenan.bricks.ldraw.color.SolidColor;
import com.loenan.bricks.ldraw.geometry.Vector;
import com.loenan.bricks.sphere.generator.geometry.CubeFace;
import org.springframework.stereotype.Component;

@Component
public class FaceColorSelector implements ColorSelector {

	public static final String SCHEME = "faces";

	@Override
	public String getScheme() {
		return SCHEME;
	}

	@Override
	public Color selectColor(CubeFace face, Vector position) {
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
