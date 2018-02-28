package com.loenan.bricks.sphere.generator.color;

import com.loenan.bricks.ldraw.color.Color;
import com.loenan.bricks.ldraw.color.ColorSet;
import com.loenan.bricks.ldraw.geometry.Vector;
import com.loenan.bricks.sphere.generator.geometry.CubeFace;

public interface ColorScheme {

	String getSchemeName();

	Color selectColor(CubeFace face, Vector position, ColorSet availableColors);
}
