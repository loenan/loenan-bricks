package com.loenan.bricks.ldraw.model;

import com.loenan.bricks.ldraw.color.Color;
import com.loenan.bricks.ldraw.geometry.Vector;

import com.loenan.bricks.ldraw.reader.LineReader;

import static java.util.Objects.requireNonNull;

public class Line implements CommandLine {

	private final Color color;
	private final Vector point1;
	private final Vector point2;

	public static Line read(LineReader reader) {
		return new Line(
			Color.read(reader),
			Vector.read(reader),
			Vector.read(reader));
	}

	public Line(Color color, Vector point1, Vector point2) {
		this.color = requireNonNull(color);
		this.point1 = requireNonNull(point1);
		this.point2 = requireNonNull(point2);
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

	@Override
	public String getLine() {
		return "2 " + color.getColorId()
			+ " " + point1.format()
			+ " " + point2.format();
	}
}
