package com.loenan.bricks.ldraw.model;

import com.loenan.bricks.ldraw.color.Color;
import com.loenan.bricks.ldraw.geometry.Vector;

import com.loenan.bricks.ldraw.reader.LineReader;

import static java.util.Objects.requireNonNull;

public class OptionalLine implements CommandLine {

	private final Color color;
	private final Vector point1;
	private final Vector point2;
	private final Vector controlPoint1;
	private final Vector controlPoint2;

	public static OptionalLine read(LineReader reader) {
		return new OptionalLine(
			Color.read(reader),
			Vector.read(reader),
			Vector.read(reader),
			Vector.read(reader),
			Vector.read(reader));
	}

	public OptionalLine(Color color, Vector point1, Vector point2, Vector controlPoint1, Vector controlPoint2) {
		this.color = requireNonNull(color);
		this.point1 = requireNonNull(point1);
		this.point2 = requireNonNull(point2);
		this.controlPoint1 = requireNonNull(controlPoint1);
		this.controlPoint2 = requireNonNull(controlPoint2);
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

	public Vector getControlPoint1() {
		return controlPoint1;
	}

	public Vector getControlPoint2() {
		return controlPoint2;
	}

	@Override
	public String getLine() {
		return "5 " + color.getColorId()
			+ " " + point1.format()
			+ " " + point2.format()
			+ " " + controlPoint1.format()
			+ " " + controlPoint2.format();
	}
}
