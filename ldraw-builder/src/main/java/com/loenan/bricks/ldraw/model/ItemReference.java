package com.loenan.bricks.ldraw.model;

import com.loenan.bricks.ldraw.color.Color;
import com.loenan.bricks.ldraw.geometry.Matrix;
import com.loenan.bricks.ldraw.geometry.Vector;
import com.loenan.bricks.ldraw.reader.LineReader;

import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.joining;

public class ItemReference implements CommandLine {

	private final Color color;
	private final Vector position;
	private final Matrix transformation;
	private LDrawItem item;

	public static ItemReference read(LineReader reader) {
		return new ItemReference(
			Color.read(reader),
			Vector.read(reader),
			Matrix.read(reader),
			LDrawItem.read(reader));
	}

	public ItemReference(Color color, Vector position, Matrix transformation, LDrawItem item) {
		this.color = requireNonNull(color);
		this.position = requireNonNull(position);
		this.transformation = requireNonNull(transformation);
		this.item = requireNonNull(item);
	}

	public Color getColor() {
		return color;
	}

	public Vector getPosition() {
		return position;
	}

	public Matrix getTransformation() {
		return transformation;
	}

	public LDrawItem getItem() {
		return item;
	}

	public void replaceItem(LDrawItem item) {
		this.item = item;
	}

	@Override
	public String getLine() {
		return "1 " + color.getColorId()
			+ " " + position.format()
			+ " " + transformation.format()
			+ " " + item.getName();
	}

	@Override
	public String toString() {
		return Stream.of("1", color, position, transformation, item.getName())
			.map(Object::toString)
			.collect(joining(" "));
	}
}
