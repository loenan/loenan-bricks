package com.loenan.bricks.ldraw.flatten;

import com.loenan.bricks.ldraw.color.Color;
import com.loenan.bricks.ldraw.geometry.Matrix;
import com.loenan.bricks.ldraw.geometry.Vector;
import com.loenan.bricks.ldraw.model.CommandLine;
import com.loenan.bricks.ldraw.model.Comment;
import com.loenan.bricks.ldraw.model.ItemReference;
import com.loenan.bricks.ldraw.model.LDrawFile;
import com.loenan.bricks.ldraw.model.Line;
import com.loenan.bricks.ldraw.model.MetaCommand;
import com.loenan.bricks.ldraw.model.MetaCommands;
import com.loenan.bricks.ldraw.model.MultiPartDocument;
import com.loenan.bricks.ldraw.model.OptionalLine;
import com.loenan.bricks.ldraw.model.Quad;
import com.loenan.bricks.ldraw.model.Triangle;
import com.loenan.bricks.ldraw.reader.LDrawReader;
import com.loenan.bricks.ldraw.writer.LDrawWriter;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

public class LDrawFlattener {

	private static final List<String> FILE_COMMANDS = asList(MetaCommands.FILE_COMMAND, MetaCommands.NOFILE_COMMAND);

	public static void main(String[] args) throws IOException {
		InputStream inputStream = System.in;
		FlattenOptions options = new FlattenOptions();
		if (args.length > 0) {
			inputStream = new FileInputStream(args[0]);
			options = new FlattenOptions(Arrays.stream(args, 1, args.length).collect(toList()));
		}
		LDrawReader reader = new LDrawReader();
		MultiPartDocument document = reader.readMPD(inputStream);

		LDrawFlattener flattener = new LDrawFlattener();
		MultiPartDocument flattenDocument = flattener.flatten(document, options);

		LDrawWriter writer = new LDrawWriter();
		writer.write(flattenDocument, System.out);
	}

	public MultiPartDocument flatten(MultiPartDocument model) {
		return flatten(model, new FlattenOptions());
	}

	public MultiPartDocument flatten(MultiPartDocument model, FlattenOptions options) {
		return new MultiPartDocument(flatten(model.getMainModel(), options));
	}

	private LDrawFile flatten(LDrawFile model, FlattenOptions options) {
		return new LDrawFile(model.getCommandLines().stream()
			.flatMap(commandLine -> flatten(commandLine, options))
			.collect(toList()));
	}

	private Stream<CommandLine> flatten(CommandLine commandLine, FlattenOptions options) {
		return Optional.of(commandLine)
			.filter(ItemReference.class::isInstance)
			.map(ItemReference.class::cast)
			.flatMap(itemReference -> Optional.of(itemReference)
				.map(ItemReference::getItem)
				.filter(LDrawFile.class::isInstance)
				.map(LDrawFile.class::cast)
				.filter(model -> options.isModelToFlatten(model.getName()))
				.map(model -> transform(itemReference, model, options)))
			.orElse(Stream.of(commandLine));
	}

	private Stream<CommandLine> transform(ItemReference parentReference, LDrawFile subModel, FlattenOptions options) {
		return subModel.getCommandLines().stream()
			.filter(this::excludeFileCommands)
			.flatMap(subCommandLine -> flatten(subCommandLine, options))
			.map(subCommandLine -> transform(parentReference, subCommandLine));
	}

	private CommandLine transform(ItemReference parentReference, CommandLine subCommandLine) {
		if (subCommandLine instanceof Comment || subCommandLine instanceof MetaCommand) {
			return subCommandLine;
		} else if (subCommandLine instanceof ItemReference) {
			return mapItemReference(parentReference, (ItemReference) subCommandLine);
		} else if (subCommandLine instanceof Line) {
			return mapLine(parentReference, (Line) subCommandLine);
		} else if (subCommandLine instanceof Triangle) {
			return mapTriangle(parentReference, (Triangle) subCommandLine);
		} else if (subCommandLine instanceof Quad) {
			return mapQuad(parentReference, (Quad) subCommandLine);
		} else if (subCommandLine instanceof OptionalLine) {
			return mapOptionalLine(parentReference, (OptionalLine) subCommandLine);
		} else {
			throw new UnsupportedOperationException("Cannot transform line " + subCommandLine.getLine());
		}
	}

	private ItemReference mapItemReference(ItemReference parentReference, ItemReference subReference) {
		return new ItemReference(
			mapColor(parentReference, subReference.getColor()),
			mapVector(parentReference, subReference.getPosition()),
			mapMatrix(parentReference, subReference.getTransformation()),
			subReference.getItem()
		);
	}

	private Line mapLine(ItemReference parentReference, Line line) {
		return new Line(
			mapColor(parentReference, line.getColor()),
			mapVector(parentReference, line.getPoint1()),
			mapVector(parentReference, line.getPoint2())
		);
	}

	private Triangle mapTriangle(ItemReference parentReference, Triangle triangle) {
		return new Triangle(
			mapColor(parentReference, triangle.getColor()),
			mapVector(parentReference, triangle.getPoint1()),
			mapVector(parentReference, triangle.getPoint2()),
			mapVector(parentReference, triangle.getPoint3())
		);
	}

	private Quad mapQuad(ItemReference parentReference, Quad quad) {
		return new Quad(
			mapColor(parentReference, quad.getColor()),
			mapVector(parentReference, quad.getPoint1()),
			mapVector(parentReference, quad.getPoint2()),
			mapVector(parentReference, quad.getPoint3()),
			mapVector(parentReference, quad.getPoint4())
		);
	}

	private OptionalLine mapOptionalLine(ItemReference parentReference, OptionalLine optionalLine) {
		return new OptionalLine(
			mapColor(parentReference, optionalLine.getColor()),
			mapVector(parentReference, optionalLine.getPoint1()),
			mapVector(parentReference, optionalLine.getPoint2()),
			mapVector(parentReference, optionalLine.getControlPoint1()),
			mapVector(parentReference, optionalLine.getControlPoint2())
		);
	}

	public Color mapColor(ItemReference parentReference, Color subColor) {
		return subColor.getColorId() == Color.MAIN_COLOR_ID
			? parentReference.getColor()
			: subColor;
	}

	public Vector mapVector(ItemReference parentReference, Vector vector) {
		return parentReference.getPosition()
			.plus(parentReference.getTransformation().transform(vector));
	}

	public Matrix mapMatrix(ItemReference parentReference, Matrix matrix) {
		return parentReference.getTransformation().combine(matrix);
	}

	private boolean excludeFileCommands(CommandLine commandLine) {
		return !(commandLine instanceof MetaCommand)
			|| !FILE_COMMANDS.contains(((MetaCommand) commandLine).getCommand());
	}
}
