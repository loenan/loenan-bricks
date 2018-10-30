package com.loenan.bricks.sphere.generator.geometry;

import com.loenan.bricks.ldraw.geometry.Matrix;

import java.util.stream.Stream;

import static com.loenan.bricks.ldraw.geometry.Angle.ANTI_QUARTER_TURN;
import static com.loenan.bricks.ldraw.geometry.Angle.HALF_TURN;
import static com.loenan.bricks.ldraw.geometry.Angle.QUARTER_TURN;
import static com.loenan.bricks.ldraw.geometry.Transformation.IDENTITY;
import static com.loenan.bricks.ldraw.geometry.Transformation.rotationY;

/**
 * @author Laurent ISTIN
 */
public enum Orientation {

	ROTATION_0(IDENTITY),
	ROTATION_90(rotationY(QUARTER_TURN)),
	ROTATION_180(rotationY(HALF_TURN)),
	ROTATION_270(rotationY(ANTI_QUARTER_TURN));

	private final Matrix transformation;

	Orientation(Matrix transformation) {
		this.transformation = transformation;
	}

	public Matrix getTransformation() {
		return transformation;
	}

	public static Stream<Orientation> single() {
		return Stream.of(ROTATION_0);
	}

	public static Stream<Orientation> all() {
		return Stream.of(values());
	}
}
