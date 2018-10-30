package com.loenan.bricks.sphere.generator.geometry;

import com.loenan.bricks.ldraw.geometry.Format;
import com.loenan.bricks.ldraw.geometry.Vector;
import com.loenan.bricks.ldraw.model.Part;
import com.loenan.bricks.ldraw.part.Slope;
import com.loenan.bricks.ldraw.part.Tile;
import com.loenan.bricks.ldraw.part.TileRound;

import java.util.List;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;

/**
 * A possible stud part to use at a stud position.
 * An instance holds the part to place, its position, and the position of its top corners.
 * Coordinates are in stud units.
 * Candidate parts are generated from a list of base stud parts, defining its position to use and its corner position for many part designs.
 * These base parts are then combined with possible orientations, in a range of heights around a base height.
 *
 * @author Laurent ISTIN
 */
public class StudPart {

	private static final double HALF_STUD = 0.5;
	private static final double PLATE_HEIGHT = 0.4;

	// Base part definition
	// --------------------

	/*
	 * Base parts are described here.
	 * They should be translated, so thar:
	 * - their bottom is at height Y=0
	 * - the center of stud postion to be exposed is at X=0 and Z=0
	 */

	private static final StudPart STUD_TILE = new StudPart(Tile.TILE_1x1,
			0, 0, 0,
			PLATE_HEIGHT, PLATE_HEIGHT, PLATE_HEIGHT, PLATE_HEIGHT);

	private static final StudPart STUD_SLOPE_31 = new StudPart(Slope.SLOPE_31_1x1,
			0, PLATE_HEIGHT, 0,
			2 * PLATE_HEIGHT, 0.5 * PLATE_HEIGHT, 0.5 * PLATE_HEIGHT, 2 * PLATE_HEIGHT);

	private static final StudPart STUD_SLOPE_45 = new StudPart(Slope.SLOPE_PLATE_45_2x1,
			0, 0, HALF_STUD,
			3 * PLATE_HEIGHT, 0.5 * PLATE_HEIGHT, 0.5 * PLATE_HEIGHT, 3 * PLATE_HEIGHT);

	private static final StudPart STUD_TILE_ROUND_QUARTER = new StudPart(TileRound.TILE_ROUND_1x1_QUARTER,
			0, 0, 0,
			PLATE_HEIGHT, 0, PLATE_HEIGHT, PLATE_HEIGHT);

	private final Part part;

	private final Vector position;

	private final List<Vector> corners;

	private final Orientation orientation;

	private final StudPosition studPosition;

	private final double baseHeight;

	private final double deltaHeight;

	/**
	 * Generates candidate stud parts for the given position.
	 * Candidates are the combination of each base stud part, for each orientation, in a range of heights around the given
	 * base height.
	 */
	public static Stream<StudPart> candidatesAt(StudPosition studPosition, double baseHeight) {
		return Stream.of(STUD_TILE, STUD_SLOPE_31/*, STUD_SLOPE_45*/)
				.flatMap(basePart -> (basePart == STUD_TILE ? Orientation.single() : Orientation.all()) // the tile doesn't need to be oriented
						.flatMap(orientation -> range(-2, 3) // height range
								.mapToObj(dh -> new StudPart(basePart, orientation, studPosition, baseHeight, dh * PLATE_HEIGHT))
						)
				);
//		return Stream.of(STUD_TILE, STUD_SLOPE_31, STUD_SLOPE_45, STUD_TILE_ROUND_QUARTER)
//				.flatMap(basePart -> (basePart == STUD_TILE ? Orientation.single() : Orientation.all()) // the tile doesn't need to be oriented
//						.flatMap(orientation -> range(-1, 2) // height range
//								.mapToObj(dh -> new StudPart(basePart, orientation, studPosition, baseHeight, dh * PLATE_HEIGHT))
//						)
//				);
	}

	/**
	 * Constructor for base part definition.
	 */
	private StudPart(Part part, double translationX, double translationY, double translationZ,
					 double cornerHeight0, double cornerHeight1, double cornerHeight2, double cornerHeight3) {
		this.part = part;
		this.position = new Vector(translationX, translationY, translationZ);
		this.corners = asList(
				new Vector(0, - avg(cornerHeight0, cornerHeight1, cornerHeight2, cornerHeight3), 0),
				new Vector(0, - avg(cornerHeight0, cornerHeight3), + HALF_STUD),
				new Vector(+ HALF_STUD, - cornerHeight0, + HALF_STUD),
				new Vector(+ HALF_STUD, - avg(cornerHeight0, cornerHeight1), 0),
				new Vector(+ HALF_STUD, - cornerHeight1, - HALF_STUD),
				new Vector(0, - avg(cornerHeight1, cornerHeight2), - HALF_STUD),
				new Vector(- HALF_STUD, - cornerHeight2, - HALF_STUD),
				new Vector(- HALF_STUD, - avg(cornerHeight2, cornerHeight3), 0),
				new Vector(- HALF_STUD, - cornerHeight3, + HALF_STUD));
		this.orientation = Orientation.ROTATION_0;
		this.studPosition = new StudPosition(0, 0);
		this.baseHeight = 0;
		this.deltaHeight = 0;
	}

	/**
	 * Constructor for moved parts.
	 */
	private StudPart(StudPart basePart, Orientation orientation, StudPosition studPosition, double baseHeight, double deltaHeight) {
		double height = baseHeight + deltaHeight;
		this.part = basePart.part;
		this.position = move(basePart.position, orientation, studPosition, height);
		this.corners = basePart.corners.stream().map(c -> move(c, orientation, studPosition, height)).collect(toList());
		this.orientation = orientation;
		this.studPosition = studPosition;
		this.baseHeight = baseHeight;
		this.deltaHeight = deltaHeight;
	}

	public Part getPart() {
		return part;
	}

	public Vector getPosition() {
		return position;
	}

	public Stream<Vector> getCorners() {
		return corners.stream();
	}

	public Orientation getOrientation() {
		return orientation;
	}

	public StudPosition getStudPosition() {
		return studPosition;
	}

	public double getBaseHeight() {
		return baseHeight;
	}

	public double getDeltaHeight() {
		return deltaHeight;
	}

	public double getHeight() {
		return baseHeight + deltaHeight;
	}

	@Override
	public String toString() {
		return studPosition + "/" + Format.format(baseHeight) + "+" + Format.format(deltaHeight)
				+ "@" + orientation.name() + ": " + part.getDescription();
	}

	private static Vector move(Vector position, Orientation orientation, StudPosition studPosition, double height) {
		return orientation.getTransformation().transform(position)
				.plus(studPosition.atHeight(height));
	}

	private double avg(double... values) {
		return DoubleStream.of(values).sum() / values.length;
	}
}
