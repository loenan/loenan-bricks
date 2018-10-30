package com.loenan.bricks.sphere.generator;

import com.loenan.bricks.ldraw.builder.LDrawBuilder;
import com.loenan.bricks.ldraw.color.ColorSet;
import com.loenan.bricks.ldraw.geometry.Vector;
import com.loenan.bricks.ldraw.model.Extensions;
import com.loenan.bricks.ldraw.model.MultiPartDocument;
import com.loenan.bricks.ldraw.part.Plate;
import com.loenan.bricks.ldraw.part.Tile;
import com.loenan.bricks.ldraw.writer.LDrawWriter;
import com.loenan.bricks.sphere.generator.color.ColorRepository;
import com.loenan.bricks.sphere.generator.color.ColorScheme;
import com.loenan.bricks.sphere.generator.color.ColorSchemeRegistry;
import com.loenan.bricks.sphere.generator.color.NeutralColorScheme;
import com.loenan.bricks.sphere.generator.geometry.CubeFace;
import com.loenan.bricks.sphere.generator.geometry.StudPart;
import com.loenan.bricks.sphere.generator.geometry.StudPosition;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

import static com.loenan.bricks.sphere.generator.geometry.MathUtil.sq;
import static java.lang.Math.abs;
import static java.lang.Math.ceil;
import static java.lang.Math.floor;
import static java.lang.Math.round;
import static java.lang.Math.sqrt;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

public class SphereGenerator {

	private static final String CONTENT_TYPE = "application/x-ldraw";

	private static final String NAME_PREFIX = "LoenanSphere";

	private static final double MAX_DISTANCE = 100000;

	private static final int STUD_WIDTH_LDU = 20;

	private static final int PLATE_HEIGHT_LDU = 8;

	private static final double PLATE_HEIGHT = 0.4;

	private static final double EPSILON = 0.0001;

	private final ColorSchemeRegistry colorSchemeRegistry;

	private final ColorRepository colorRepository;

	private String baseName = NAME_PREFIX;

	private String colorSchemeName;

	private ColorScheme colorScheme;

	private double diameter;

	private int diameterExt;

	private int coreSize;

	public SphereGenerator(ColorSchemeRegistry colorSchemeRegistry,
						   ColorRepository colorRepository) {
		this.colorSchemeRegistry = colorSchemeRegistry;
		this.colorRepository = colorRepository;

		setColorSchemeName(NeutralColorScheme.SCHEME_NAME); // default color scheme
	}

	public void setColorSchemeName(String colorSchemeName) {
		this.colorSchemeName = colorSchemeName;
		setBaseName();
		colorScheme = colorSchemeRegistry.getColorScheme(this.colorSchemeName);
	}

	public void setDiameter(double diameter) {
		this.diameter = diameter;
		this.coreSize = (int) floor(diameter / sqrt(3));
		this.diameterExt = coreSize + 2 * ((int) ceil((diameter - coreSize) / 2));
		setBaseName();
	}

	public String getContentType() {
		return CONTENT_TYPE;
	}

	public String getFileName() {
		return baseName + Extensions.MPD;
	}

	public void generateSphere(OutputStream outputStream) throws IOException {
		LDrawBuilder modelBuilder = new LDrawBuilder(baseName)
				.setDescription(getSphereDescription())
				.setAuthor(getAuthor())
				.setCopyright(getCopyright());

		for (CubeFace face : CubeFace.values()) {
			LDrawBuilder faceBuilder = new LDrawBuilder("face_" + face.name().toLowerCase())
					.setDescription(getFaceDescription(face))
					.setAuthor(getAuthor())
					.setCopyright(getCopyright());

			StudPosition.area(coreSize, diameterExt)
					.forEach(studPosition -> generateStudParts(faceBuilder, face, studPosition));

			modelBuilder.add(
					face.getTranslation().mult(coreSize * STUD_WIDTH_LDU / 2),
					face.getTransformation(),
					faceBuilder.build());
		}

		LDrawWriter writer = new LDrawWriter();
		writer.write(new MultiPartDocument(modelBuilder.build()), outputStream);
	}

	private void generateStudParts(LDrawBuilder faceBuilder, CubeFace face, StudPosition studPosition) {
		Optional<Double> baseHeight = getHeightAtPosition(studPosition);
		if (! baseHeight.isPresent()) {
			return;
		}
		double h = round(baseHeight.get() / PLATE_HEIGHT) * PLATE_HEIGHT;
		Map<Double, List<StudPart>> candidates = StudPart.candidatesAt(studPosition, h)
				.collect(groupingBy(this::distance, TreeMap::new, toList())) // TreeMap is ordering by the distance
				.headMap(MAX_DISTANCE);
		candidates
				.values().stream()
//				.map(list -> {
//					if (list.size() > 1) {
//						System.out.println(face+": "+studPosition+": "+list.size());
//					}
//					return Optional.of(list.get(0));
//				})
				.map(list -> list.size() == 1
						? Optional.of(list.get(0))
						: list.stream().filter(s -> s.getPart() == Tile.TILE_1x1).findFirst())
				.filter(Optional::isPresent)
				.map(Optional::get)
				.findFirst()
				.ifPresent(closest -> addStudParts(faceBuilder, face, closest));
	}

	private void addStudParts(LDrawBuilder faceBuilder, CubeFace face, StudPart studPart) {
		if (studPart.getHeight() <= 0) {
			return;
		}
		ColorSet availableColors = colorRepository.getColorsByPart(studPart.getPart());
		Vector position = face.getTransformation().transform(studPart.getPosition()).plus(face.getTranslation().mult(coreSize / 2));
		faceBuilder
				.setCurrentColor(colorScheme.selectColor(face, position, availableColors))
				.add(studPart.getPosition().mult(STUD_WIDTH_LDU), studPart.getOrientation().getTransformation(), studPart.getPart());
		for (int dh = 1; dh < 3; dh++) {
			double plateHeight = studPart.getHeight() - dh * PLATE_HEIGHT;
			if (plateHeight < EPSILON) {
				break;
			}
			Vector platePosition = studPart.getStudPosition().atHeight(plateHeight).mult(STUD_WIDTH_LDU);
			faceBuilder.add(platePosition, Plate.PLATE_1x1);
		}
	}

	private double distance(StudPart candidate) {
		return candidate.getCorners()
				.mapToDouble(corner -> getHeightAtPosition(StudPosition.of(corner))
						.map(h -> abs(h + corner.getY()))
						.orElse(MAX_DISTANCE))
				.sum();
	}

	private Optional<Double> getHeightAtPosition(StudPosition studPosition) {
		double h2 = sq(diameter / 2) - sq(studPosition.getU()) - sq(studPosition.getV());
		if (h2 <= 0) {
			return Optional.empty();
		}
		return Optional.of(sqrt(h2) - coreSize / 2.0);
	}

	private void setBaseName() {
		this.baseName = NAME_PREFIX + "-" + colorSchemeName + "-D" + diameter + "-C" + coreSize;
	}

	private String getSphereDescription() {
		return "Brick Sphere, Diameter " + diameter + ", " +
				coreSize + "x" + coreSize + "x" + coreSize + " Core, " +
				colorSchemeName + " color scheme";
	}

	private String getFaceDescription(CubeFace face) {
		return "Face panel (" + face.name().toLowerCase() + ") for " + getSphereDescription();
	}

	private String getAuthor() {
		return "Loenan Bricks Sphere Generator";
	}

	private String getCopyright() {
		return LocalDate.now().getYear() + " Laurent Istin <laurent.istin@free.fr>";
	}
}
