package com.loenan.bricks.sphere.generator.geometry;

import com.loenan.bricks.ldraw.geometry.Format;
import com.loenan.bricks.ldraw.geometry.Vector;

import java.util.Objects;
import java.util.stream.Stream;

import static java.util.stream.IntStream.range;

/**
 * Position of a stud in a 2 dimension area.
 * The 2 dimensions are named U and V.
 * Coordinates are in stud units.
 *
 * @author Laurent ISTIN
 */
public class StudPosition {

	private final double u;

	private final double v;

	/**
	 * Return a stream of stud positions covering an area of the given size, centered around the (0, 0) position.
	 */
	public static Stream<StudPosition> area(int lengthU, int lengthV) {
		double deltaU = (lengthU - 1.0) / 2;
		double deltaV = (lengthV - 1.0) / 2;
		return range(0, lengthU).boxed()
				.flatMap(u -> range(0, lengthV).mapToObj(v ->
						new StudPosition(u - deltaU, v - deltaV))
				);
	}

	public static StudPosition of(Vector position) {
		return new StudPosition(position.getX(), position.getZ());
	}

	StudPosition(double u, double v) {
		this.u = u;
		this.v = v;
	}

	public double getU() {
		return u;
	}

	public double getV() {
		return v;
	}

	public Vector atHeight(double h) {
		return new Vector(u, -h, v);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		StudPosition that = (StudPosition) o;
		return Double.compare(that.u, u) == 0 && Double.compare(that.v, v) == 0;
	}

	@Override
	public int hashCode() {
		return Objects.hash(u, v);
	}

	@Override
	public String toString() {
		return "(" + Format.format(u) + ", " + Format.format(v) + ")";
	}
}
