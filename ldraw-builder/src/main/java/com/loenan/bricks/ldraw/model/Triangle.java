package com.loenan.bricks.ldraw.model;

import com.loenan.bricks.ldraw.color.Color;
import com.loenan.bricks.ldraw.geometry.Vector;

import com.loenan.bricks.ldraw.reader.LineReader;

import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.joining;

public class Triangle implements CommandLine {

	private final Color color;
	private final Vector point1;
	private final Vector point2;
	private final Vector point3;

	public static Triangle read(LineReader reader) {
		return new Triangle(
			Color.read(reader),
			Vector.read(reader),
			Vector.read(reader),
			Vector.read(reader));
	}

	public Triangle(Color color, Vector point1, Vector point2, Vector point3) {
		this.color = requireNonNull(color);
		this.point1 = requireNonNull(point1);
		this.point2 = requireNonNull(point2);
		this.point3 = requireNonNull(point3);
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

	@Override
	public String getLine() {
		return "3 " + color.getColorId()
			+ " " + point1.format()
			+ " " + point2.format()
			+ " " + point3.format();
	}

	@Override
	public String toString() {
		return Stream.of("3", color, point1, point2, point3)
			.map(Object::toString)
			.collect(joining(" "));
	}
}
