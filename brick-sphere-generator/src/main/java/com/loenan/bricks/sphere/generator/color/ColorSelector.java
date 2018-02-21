package com.loenan.bricks.sphere.generator.color;

import com.loenan.bricks.ldraw.color.Color;
import com.loenan.bricks.ldraw.geometry.Vector;
import com.loenan.bricks.sphere.generator.geometry.CubeFace;

public interface ColorSelector {

	String getScheme();

	Color selectColor(CubeFace face, Vector position);
}
