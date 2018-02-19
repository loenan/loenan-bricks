package com.loenan.bricks.sphere.generator;

import com.loenan.bricks.ldraw.builder.LDrawBuilder;
import com.loenan.bricks.ldraw.color.Color;
import com.loenan.bricks.ldraw.color.SolidColor;
import com.loenan.bricks.ldraw.model.MultiPartDocument;
import com.loenan.bricks.ldraw.writer.LDrawWriter;
import com.loenan.bricks.sphere.generator.geometry.CubeFace;
import com.loenan.bricks.sphere.generator.ldaw.Parts;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;

@Component
public class SphereGenerator {

	public void generateSphere(SphereParameters parameters, OutputStream outputStream) throws IOException {
		double diameter = parameters.getDiameter();
		LDrawBuilder modelBuilder = new LDrawBuilder("loenan_sphere_" + diameter);
		for (CubeFace face : CubeFace.values()) {
			modelBuilder.add(
					face.getPosition().mult(diameter * 10),
					face.getTransformation(),
					new LDrawBuilder("face_" + face.name().toLowerCase())
							.setCurrentColor(getColor(face))
							.add(0, -8, 0, Parts.PLATE_6X8)
							.add(10, -8, 10, Parts.SLOPE_31_1X1)
							.build());
		}
		LDrawWriter writer = new LDrawWriter();
		writer.write(new MultiPartDocument(modelBuilder.build()), outputStream);
	}

	private Color getColor(CubeFace face) {
		switch (face) {
			case TOP:
				return SolidColor.RED;
			case BOTTOM:
				return SolidColor.ORANGE;
			case FRONT:
				return SolidColor.BLUE;
			case BACK:
				return SolidColor.GREEN;
			case LEFT:
				return SolidColor.YELLOW;
			case RIGHT:
				return SolidColor.TAN;
			default:
				return SolidColor.BLACK;
		}
	}
}
