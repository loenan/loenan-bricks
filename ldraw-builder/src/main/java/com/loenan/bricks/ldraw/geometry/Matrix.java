package com.loenan.bricks.ldraw.geometry;

import com.loenan.bricks.ldraw.reader.LineReader;

import java.util.NoSuchElementException;

public class Matrix {

	private final double a, b, c;
	private final double d, e, f;
	private final double g, h, i;

	public static Matrix read(LineReader reader) {
		return reader.readTokens(9)
			.map(tokens -> new Matrix(
				Double.parseDouble(tokens.get(0)),
				Double.parseDouble(tokens.get(1)),
				Double.parseDouble(tokens.get(2)),
				Double.parseDouble(tokens.get(3)),
				Double.parseDouble(tokens.get(4)),
				Double.parseDouble(tokens.get(5)),
				Double.parseDouble(tokens.get(6)),
				Double.parseDouble(tokens.get(7)),
				Double.parseDouble(tokens.get(8))))
			.orElseThrow(() -> new NoSuchElementException("No matrix defined"));
	}

	public Matrix(double a, double b, double c,
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
			m.b * a + m.e * b + m.h * c,
			m.c * a + m.f * b + m.i * c,

			m.a * d + m.d * e + m.g * f,
			m.b * d + m.e * e + m.h * f,
			m.c * d + m.f * e + m.i * f,

			m.a * g + m.d * h + m.g * i,
			m.b * g + m.e * h + m.h * i,
			m.c * g + m.f * h + m.i * i);
	}

	public String format() {
		return Format.format(this);
	}
}
