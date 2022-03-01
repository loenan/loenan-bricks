package com.loenan.bricks.ldraw.reader;

import com.loenan.bricks.ldraw.model.LDrawFile;
import com.loenan.bricks.ldraw.writer.LDrawWriter;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static com.loenan.bricks.ldraw.TestUtils.assertContentEquals;
import static com.loenan.bricks.ldraw.TestUtils.loadLowellSphereContent;
import static com.loenan.bricks.ldraw.TestUtils.toInputStream;

public class LDrawReaderTest {

	@Test
	public void testReadAndWriteModel() throws IOException {
		LDrawFile model;
		try (InputStream inputStream = loadLowellSphereContent()) {
			LDrawReader reader = new LDrawReader();
			model = reader.read(inputStream);
		}

		LDrawWriter writer = new LDrawWriter();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		writer.write(model, outputStream);

		assertContentEquals(
			loadLowellSphereContent(),
			toInputStream(outputStream));
	}
}
