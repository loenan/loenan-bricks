package com.loenan.bricks.sphere.generator.geometry;

import com.loenan.bricks.ldraw.geometry.Matrix;
import com.loenan.bricks.ldraw.geometry.Vector;

import static com.loenan.bricks.ldraw.geometry.Angle.ANTI_QUARTER_TURN;
import static com.loenan.bricks.ldraw.geometry.Angle.HALF_TURN;
import static com.loenan.bricks.ldraw.geometry.Angle.QUARTER_TURN;
import static com.loenan.bricks.ldraw.geometry.Transformation.IDENTITY;
import static com.loenan.bricks.ldraw.geometry.Transformation.rotationY;
import static com.loenan.bricks.ldraw.geometry.Transformation.rotationZ;

public enum CubeFace {

	TOP(0, -1, 0, IDENTITY),
	BOTTOM(0, 1, 0, rotationZ(HALF_TURN)),
	FRONT(1, 0, 0, rotationZ(QUARTER_TURN).combine(rotationY(QUARTER_TURN))),
	BACK(-1, 0, 0, rotationZ(ANTI_QUARTER_TURN).combine(rotationY(ANTI_QUARTER_TURN))),
	LEFT(0, 0, 1, rotationY(ANTI_QUARTER_TURN).combine(rotationZ(QUARTER_TURN))),
	RIGHT(0, 0, -1, rotationY(QUARTER_TURN).combine(rotationZ(QUARTER_TURN)));

	private final Vector translation;
	private final Matrix transformation;

	CubeFace(double x, double y, double z, Matrix transformation) {
		this.translation = new Vector(x, y, z);
		this.transformation = transformation;
	}

	public Vector getTranslation() {
		return translation;
	}

	public Matrix getTransformation() {
		return transformation;
	}
}
