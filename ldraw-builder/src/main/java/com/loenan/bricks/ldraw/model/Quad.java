package com.loenan.bricks.ldraw.model;

import com.loenan.bricks.ldraw.color.Color;
import com.loenan.bricks.ldraw.geometry.Vector;

import com.loenan.bricks.ldraw.reader.LineReader;

import static java.util.Objects.requireNonNull;

public class Quad implements CommandLine {

	private final Color color;
	private final Vector point1;
	private final Vector point2;
	private final Vector point3;
	private final Vector point4;

	public static Quad read(LineReader reader) {
		return new Quad(
			Color.read(reader),
			Vector.read(reader),
			Vector.read(reader),
			Vector.read(reader),
			Vector.read(reader));
	}

	public Quad(Color color, Vector point1, Vector point2, Vector point3, Vector point4) {
		this.color = requireNonNull(color);
		this.point1 = requireNonNull(point1);
		this.point2 = requireNonNull(point2);
		this.point3 = requireNonNull(point3);
		this.point4 = requireNonNull(point4);
	}

	public Color getColor() {
		return color;
	}

	public Vector getPoint1() {
		return point1;
	}

	public Vector getPoint2() {
		return point2;
	}

	public Vector getPoint3() {
		return point3;
	}

	public Vector getPoint4() {
		return point4;
	}

	@Override
	public String getLine() {
		return "4 " + color.getColorId()
			+ " " + point1.format()
			+ " " + point2.format()
			+ " " + point3.format()
			+ " " + point4.format();
	}
}
