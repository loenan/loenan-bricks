package com.loenan.bricks.ldraw.geometry;

public class Matrix {

	private final double a, b, c;

	private final double d, e, f;

	private final double g, h, i;

	public Matrix(
			double a, double b, double c,
			double d, double e, double f,
			double g, double h, double i) {
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
		this.e = e;
		this.f = f;
		this.g = g;
		this.h = h;
		this.i = i;
	}

	public double getA() {
		return a;
	}

	public double getB() {
		return b;
	}

	public double getC() {
		return c;
	}

	public double getD() {
		return d;
	}

	public double getE() {
		return e;
	}

	public double getF() {
		return f;
	}

	public double getG() {
		return g;
	}

	public double getH() {
		return h;
	}

	public double getI() {
		return i;
	}

	public Vector transform(Vector v) {
		return new Vector(
				a * v.getX() + b * v.getY() + c * v.getZ(),
				d * v.getX() + e * v.getY() + f * v.getZ(),
				g * v.getX() + h * v.getY() + i * v.getZ());
	}

	public Matrix combine(Matrix m) {
		return new Matrix(
				m.a * a + m.d * b + m.g * c,
				m.a * d + m.d * e + m.g * f,
				m.a * g + m.d * h + m.g * i,
				m.b * a + m.e * b + m.h * c,
				m.b * d + m.e * e + m.h * f,
				m.b * g + m.e * h + m.h * i,
				m.c * a + m.f * b + m.i * c,
				m.c * d + m.f * e + m.i * f,
				m.c * g + m.f * h + m.i * i);
	}

	public String format() {
		return Format.format(this);
	}
}
