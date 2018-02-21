package com.loenan.bricks.sphere.generator;

import com.loenan.bricks.ldraw.builder.LDrawBuilder;
import com.loenan.bricks.ldraw.color.Color;
import com.loenan.bricks.ldraw.geometry.Vector;
import com.loenan.bricks.ldraw.model.Extensions;
import com.loenan.bricks.ldraw.model.MultiPartDocument;
import com.loenan.bricks.ldraw.writer.LDrawWriter;
import com.loenan.bricks.sphere.generator.color.ColorSelector;
import com.loenan.bricks.sphere.generator.color.ColorSelectorRegistry;
import com.loenan.bricks.sphere.generator.color.NeutralColorSelector;
import com.loenan.bricks.sphere.generator.geometry.CubeFace;
import com.loenan.bricks.sphere.generator.ldaw.Parts;

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

	private final ColorSelectorRegistry colorSelectorRegistry;

	private String baseName = NAME_PREFIX;

	private String colorScheme = NeutralColorSelector.SCHEME;

	private double diameter;

	private int diameterExt;

	private int coreSize;

	public SphereGenerator(ColorSelectorRegistry colorSelectorRegistry) {
		this.colorSelectorRegistry = colorSelectorRegistry;
	}

	public void setColorScheme(String colorScheme) {
		this.colorScheme = colorScheme;
		setBaseName();
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
		ColorSelector colorSelector = colorSelectorRegistry.getColorSelector(colorScheme);
		LDrawBuilder modelBuilder = new LDrawBuilder(baseName)
				.setDescription(getSphereDescription())
				.setAuthor(getAuthor())
				.setCopyright(getCopyright());
		for (CubeFace face : CubeFace.values()) {
			LDrawBuilder faceBuilder = new LDrawBuilder("face_" + face.name().toLowerCase())
					.setDescription(getFaceDescription(face))
					.setAuthor(getAuthor())
					.setCopyright(getCopyright());
			double uShift = (diameterExt - 1.0) / 2;
			double vShift = (coreSize - 1.0) / 2;
			for (int u = 0; u < diameterExt; u++) {
				for (int v = 0; v < coreSize; v++) {
					double uCoord = u - uShift;
					double vCoord = v - vShift;
					double h2 = sq(diameter / 2) - sq(uCoord) - sq(vCoord);
					if (h2 <= 0) {
						continue;
					}
					int h = (int) round((sqrt(h2) - coreSize / 2.0) * STUD_WIDTH_LDU / PLATE_HEIGHT_LDU);
					if (h > 0) {
						double hCoord = -sqrt(h2);
						Vector position = new Vector(uCoord, hCoord, vCoord);
						position = face.getTransformation().transform(position);
						Color color = colorSelector.selectColor(face, position);

						faceBuilder
								.setCurrentColor(color)
								.add(
								uCoord * STUD_WIDTH_LDU,
								-h * PLATE_HEIGHT_LDU,
								vCoord * STUD_WIDTH_LDU,
								Parts.TILE_1X1);
						while (h > 1) {
							h--;
							faceBuilder.add(
									uCoord * STUD_WIDTH_LDU,
									-h * PLATE_HEIGHT_LDU,
									vCoord * STUD_WIDTH_LDU,
									Parts.PLATE_1X1);
						}
					}
				}
			}
			modelBuilder.add(
					face.getTranslation().mult(coreSize * STUD_WIDTH_LDU / 2),
					face.getTransformation(),
					faceBuilder.build());
		}
		LDrawWriter writer = new LDrawWriter();
		writer.write(new MultiPartDocument(modelBuilder.build()), outputStream);
	}

	private void setBaseName() {
		this.baseName = NAME_PREFIX + "-" + colorScheme + "-D" + diameter + "-C" + coreSize;
	}

	private String getSphereDescription() {
		return "Brick Sphere, Diameter " + diameter + ", " +
				coreSize + "x" + coreSize + "x" + coreSize + " Core";
	}

	private String getFaceDescription(CubeFace face) {
		return "Face panel (" + face.name().toLowerCase() + ") for " +
				"Brick Sphere, Diameter " + diameter + ", " +
				coreSize + "x" + coreSize + "x" + coreSize + " Core";
	}

	private String getAuthor() {
		return "Loenan Bricks Sphere Generator";
	}

	private String getCopyright() {
		return LocalDate.now().getYear() + " Laurent Istin <laurent.istin@free.fr>";
	}
}
