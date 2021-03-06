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

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;

import static com.loenan.bricks.sphere.generator.geometry.MathUtil.sq;
import static java.lang.Math.ceil;
import static java.lang.Math.floor;
import static java.lang.Math.round;
import static java.lang.Math.sqrt;

public class SphereGenerator {

	private static final String CONTENT_TYPE = "application/x-ldraw";

	private static final String NAME_PREFIX = "LoenanSphere";

	private static final int STUD_WIDTH_LDU = 20;

	private static final int PLATE_HEIGHT_LDU = 8;

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
		double centerU = studPosition.getCenterU();
		double centerV = studPosition.getCenterV();
		double h2 = sq(diameter / 2) - sq(centerU) - sq(centerV);
		if (h2 <= 0) {
			return;
		}
		int h = (int) round((sqrt(h2) - coreSize / 2.0) * STUD_WIDTH_LDU / PLATE_HEIGHT_LDU);
		if (h > 0) {
			double hCoord = -sqrt(h2);
			Vector position = new Vector(centerU, hCoord, centerV);
			ColorSet availableColors = colorRepository.getColorsByPart(Tile.TILE_1x1);
			position = face.getTransformation().transform(position);
			faceBuilder
					.setCurrentColor(colorScheme.selectColor(face, position, availableColors))
					.add(centerU * STUD_WIDTH_LDU,
							-h * PLATE_HEIGHT_LDU,
							centerV * STUD_WIDTH_LDU,
							Tile.TILE_1x1);
			for (int dh = 1; dh < 3 && h - dh > 0; dh++) {
				faceBuilder.add(centerU * STUD_WIDTH_LDU,
						-(h - dh) * PLATE_HEIGHT_LDU,
						centerV * STUD_WIDTH_LDU,
						Plate.PLATE_1x1);
			}
		}
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
