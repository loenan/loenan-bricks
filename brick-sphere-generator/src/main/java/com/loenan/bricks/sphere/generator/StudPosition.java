package com.loenan.bricks.sphere.generator;

import java.util.stream.Stream;

import static java.util.stream.IntStream.range;

/**
 * Position of a stud in a 2 dimension area.
 * The 2 dimensions are named U and V.
 * A stud is a 1x1 square, so on each axle the position has 2 bounds coordinates (min and max) separated by 1,
 * and a center coordinate in the middle.
 * Coordinates are in stud units.
 *
 * @author Laurent ISTIN
 */
class StudPosition {

	/**
	 * Coordinate of the center of the stud on the U axle.
	 */
	private final double centerU;

	/**
	 * Coordinate of the center of the stud on the V axle.
	 */
	private final double centerV;

	public StudPosition(double centerU, double centerV) {
		this.centerU = centerU;
		this.centerV = centerV;
	}

	public double getCenterU() {
		return centerU;
	}

	public double getMinU() {
		return centerU - 0.5;
	}

	public double getMaxU() {
		return centerU + 0.5;
	}

	public double getCenterV() {
		return centerV;
	}

	public double getMinV() {
		return centerV - 0.5;
	}

	public double getMaxV() {
		return centerV + 0.5;
	}

	/**
	 * Return a stream of stud positions covering an area of the given size, centered around the (0, 0) position.
	 */
	public static Stream<StudPosition> area(int lengthU, int lengthV) {
		double deltaU = (lengthU - 1.0) / 2;
		double deltaV = (lengthV - 1.0) / 2;
		return range(0, lengthU).boxed()
				.flatMap(u -> range(0, lengthV).mapToObj(v ->
						new StudPosition(u - deltaU, v - deltaV)));
	}
}
