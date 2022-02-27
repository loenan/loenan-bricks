package com.loenan.bricks.sphere.generator.color;

import com.loenan.bricks.ldraw.color.Color;
import com.loenan.bricks.ldraw.color.ColorSet;
import com.loenan.bricks.ldraw.geometry.Vector;
import com.loenan.bricks.sphere.generator.geometry.CubeFace;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order()
public class NeutralColorScheme implements ColorScheme {

	public static final String SCHEME_NAME = "neutral";

	@Override
	public String getSchemeName() {
		return SCHEME_NAME;
	}

	@Override
	public Color selectColor(CubeFace face, Vector position, ColorSet availableColors) {
		return Color.MAIN_COLOR;
	}
}
