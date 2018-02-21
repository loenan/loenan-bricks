package com.loenan.bricks.sphere.generator.color;

import com.loenan.bricks.ldraw.color.Color;
import com.loenan.bricks.ldraw.geometry.Vector;
import com.loenan.bricks.sphere.generator.geometry.CubeFace;
import org.springframework.stereotype.Component;

@Component
public class NeutralColorSelector implements ColorSelector {

	public static final String SCHEME = "neutral";

	@Override
	public String getScheme() {
		return SCHEME;
	}

	@Override
	public Color selectColor(CubeFace face, Vector position) {
		return Color.MAIN_COLOR;
	}
}
