package com.loenan.bricks.ldraw.examples;

import com.loenan.bricks.ldraw.builder.LDrawBuilder;
import com.loenan.bricks.ldraw.color.SolidColor;
import com.loenan.bricks.ldraw.geometry.Angle;
import com.loenan.bricks.ldraw.geometry.Transformation;
import com.loenan.bricks.ldraw.model.LDrawFile;
import com.loenan.bricks.ldraw.model.MultiPartDocument;
import com.loenan.bricks.ldraw.part.BrickModified;
import com.loenan.bricks.ldraw.part.Plate;
import com.loenan.bricks.ldraw.part.PlateModified;
import com.loenan.bricks.ldraw.writer.LDrawWriter;

import java.io.FileOutputStream;
import java.io.IOException;

public class LowellSphere {

	public static void main(String[] args) throws IOException {

		LDrawFile face = new LDrawBuilder("face")
				.setCurrentColor(SolidColor.RED)
				.add(0, -8, 30, Plate.PLATE_1x4)
				.add(0, -8, -30, Plate.PLATE_1x4)
				.add(30, -8, 10, PlateModified.PLATE_1x2_JUMPER)
				.add(-30, -8, 10, PlateModified.PLATE_1x2_JUMPER)
				.add(30, -8, -10, PlateModified.PLATE_1x2_JUMPER)
				.add(-30, -8, -10, PlateModified.PLATE_1x2_JUMPER)
				.addStep()
				.add(10, -16, 10, Plate.PLATE_2x2_CORNER)
				.add(-10, -16, 10, Transformation.rotationY(Angle.ANTI_QUARTER_TURN), Plate.PLATE_2x2_CORNER)
				.add(10, -16, -10, Transformation.rotationY(Angle.QUARTER_TURN), Plate.PLATE_2x2_CORNER)
				.add(-10, -16, -10, Transformation.rotationY(Angle.HALF_TURN), Plate.PLATE_2x2_CORNER)
				.addStep()
				.add(0, -24, 0, Plate.PLATE_2x2)
				.build();

		LDrawFile faceWithSnotBricks = new LDrawBuilder("face_with_bricks")
				.setCurrentColor(SolidColor.BLACK)
				.add(30, 0, 30, BrickModified.BRICK_MODIFIED_1x1_STUDS_ON_4_SIDES)
				.add(-30, 0, 30, BrickModified.BRICK_MODIFIED_1x1_STUDS_ON_4_SIDES)
				.add(30, 0, -30, BrickModified.BRICK_MODIFIED_1x1_STUDS_ON_4_SIDES)
				.add(-30, 0, -30, BrickModified.BRICK_MODIFIED_1x1_STUDS_ON_4_SIDES)
				.addStep()
				.add(0, 0, 0, face)
				.build();

		LDrawFile lowellSphere = new LDrawBuilder("lowell_sphere")
				.setDescription("Lowell Sphere")
				.setAuthor("Bruce Lowell")
				.add(0, -40, 0, Transformation.IDENTITY, faceWithSnotBricks)
				.add(0, 40, 0, Transformation.rotationZ(Angle.HALF_TURN), faceWithSnotBricks)
				.addStep()
				.add(40, 0, 0, Transformation.rotationZ(Angle.ANTI_QUARTER_TURN)
						.combine(Transformation.rotationX(Angle.QUARTER_TURN)), face)
				.add(-40, 0, 0, Transformation.rotationZ(Angle.QUARTER_TURN)
						.combine(Transformation.rotationX(Angle.QUARTER_TURN)), face)
				.addStep()
				.add(0, 0, 40, Transformation.rotationX(Angle.QUARTER_TURN)
						.combine(Transformation.rotationZ(Angle.ANTI_QUARTER_TURN)), face)
				.add(0, 0, -40, Transformation.rotationX(Angle.ANTI_QUARTER_TURN)
						.combine(Transformation.rotationZ(Angle.ANTI_QUARTER_TURN)), face)
				.build();

		MultiPartDocument mpd = new MultiPartDocument(lowellSphere);

		LDrawWriter writer = new LDrawWriter();
		try (FileOutputStream outputStream = new FileOutputStream("lowell_sphere.mpd")) {
			writer.write(mpd, outputStream);
		}
	}
}
