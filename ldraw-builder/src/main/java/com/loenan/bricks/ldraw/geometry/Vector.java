package com.loenan.bricks.ldraw.geometry;

public class Vector {

	private final double x;

	private final double y;

	private final double z;

	public Vector(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getZ() {
		return z;
	}

	public Vector plus(Vector v) {
		return plus(this, v);
	}

	public Vector mult(double n) {
		return new Vector(n * x, n * y, n * z);
	}

	public static Vector plus(Vector a, Vector b) {
		return new Vector(a.x + b.x, a.y + b.y, a.z + b.z);
	}

	public String format() {
		return Format.format(this);
	}
}
