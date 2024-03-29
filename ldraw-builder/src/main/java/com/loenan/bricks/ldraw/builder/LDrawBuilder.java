package com.loenan.bricks.ldraw.builder;

import com.loenan.bricks.ldraw.color.Color;
import com.loenan.bricks.ldraw.geometry.Matrix;
import com.loenan.bricks.ldraw.geometry.Transformation;
import com.loenan.bricks.ldraw.geometry.Vector;
import com.loenan.bricks.ldraw.model.CommandLine;
import com.loenan.bricks.ldraw.model.Comment;
import com.loenan.bricks.ldraw.model.Extensions;
import com.loenan.bricks.ldraw.model.ItemReference;
import com.loenan.bricks.ldraw.model.LDrawFile;
import com.loenan.bricks.ldraw.model.LDrawItem;
import com.loenan.bricks.ldraw.model.Line;
import com.loenan.bricks.ldraw.model.MetaCommands;
import com.loenan.bricks.ldraw.model.OptionalLine;
import com.loenan.bricks.ldraw.model.Quad;
import com.loenan.bricks.ldraw.model.Triangle;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;

public class LDrawBuilder {

	private final static String AUTHOR_PREFIX = "Author: ";
	private final static String COPYRIGHT_PREFIX = "(c) ";

	private final String fileName;

	private String description;
	private String author;
	private String copyright;

	private List<CommandLine> commandLines = new ArrayList<>();

	private Color currentColor = Color.MAIN_COLOR;

	public LDrawBuilder(String baseName) {
		this.fileName = baseName + Extensions.LDR;
	}

	public LDrawBuilder setDescription(String description) {
		this.description = description;
		return this;
	}

	public LDrawBuilder setAuthor(String author) {
		this.author = author;
		return this;
	}

	public LDrawBuilder setCopyright(String copyright) {
		this.copyright = copyright;
		return this;
	}

	public LDrawBuilder setCurrentColor(Color currentColor) {
		this.currentColor = requireNonNull(currentColor);
		return this;
	}

	public LDrawBuilder add(double x, double y, double z, LDrawItem item) {
		return add(new Vector(x, y, z), Transformation.IDENTITY, item);
	}

	public LDrawBuilder add(double x, double y, double z, Matrix transformation, LDrawItem item) {
		return add(new Vector(x, y, z), transformation, item);
	}

	public LDrawBuilder add(Vector position, LDrawItem item) {
		return add(position, Transformation.IDENTITY, item);
	}

	public LDrawBuilder add(Vector position, Matrix transformation, LDrawItem item) {
		commandLines.add(new ItemReference(currentColor, position, transformation, item));
		return this;
	}

	public LDrawBuilder addLine(Vector point1, Vector point2) {
		commandLines.add(new Line(currentColor, point1, point2));
		return this;
	}

	public LDrawBuilder addTriangle(Vector point1, Vector point2, Vector point3) {
		commandLines.add(new Triangle(currentColor, point1, point2, point3));
		return this;
	}

	public LDrawBuilder addQuad(Vector point1, Vector point2, Vector point3, Vector point4) {
		commandLines.add(new Quad(currentColor, point1, point2, point3, point4));
		return this;
	}

	public LDrawBuilder addOptionalLine(Vector point1, Vector point2, Vector controlPoint1, Vector controlPoint2) {
		commandLines.add(new OptionalLine(currentColor, point1, point2, controlPoint1, controlPoint2));
		return this;
	}

	public LDrawBuilder addStep() {
		commandLines.add(MetaCommands.STEP);
		return this;
	}

	public LDrawBuilder addComment(String comment) {
		commandLines.add(new Comment(comment));
		return this;
	}

	public LDrawFile build() {
		return new LDrawFile(fileName, Stream.of(buildComments(), commandLines).flatMap(List::stream).collect(toList()));
	}

	private List<Comment> buildComments() {
		List<Comment> comments = new ArrayList<>(3);
		if (description != null) {
			comments.add(new Comment(description));
		}
		if (author != null) {
			comments.add(new Comment(AUTHOR_PREFIX + author));
		}
		if (copyright != null) {
			comments.add(new Comment(COPYRIGHT_PREFIX + copyright));
		}
		return comments;
	}
}
