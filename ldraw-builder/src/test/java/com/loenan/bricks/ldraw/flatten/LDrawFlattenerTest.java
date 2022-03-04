package com.loenan.bricks.ldraw.flatten;

import com.loenan.bricks.ldraw.model.MultiPartDocument;
import com.loenan.bricks.ldraw.reader.LDrawReader;
import com.loenan.bricks.ldraw.writer.LDrawWriter;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static com.loenan.bricks.ldraw.TestUtils.assertContentEquals;
import static com.loenan.bricks.ldraw.TestUtils.loadLowellSphereContent;
import static com.loenan.bricks.ldraw.TestUtils.loadTestContent;
import static com.loenan.bricks.ldraw.TestUtils.toInputStream;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

public class LDrawFlattenerTest {

	@Test
	public void testFlatten() throws IOException {
		ByteArrayOutputStream outputStream = loadLowellSphereModelAndFlattenWithOptions(new FlattenOptions());

		assertContentEquals(
			loadTestContent("flatten_lowell_sphere.mpd"),
			toInputStream(outputStream));
	}


	@Test
	public void testPartiallyFlatten() throws IOException {
		ByteArrayOutputStream outputStream = loadLowellSphereModelAndFlattenWithOptions(
			new FlattenOptions(singletonList("face.ldr")));

		assertContentEquals(
			loadTestContent("partially_flatten_lowell_sphere.mpd"),
			toInputStream(outputStream));
	}

	@Test
	public void testNoModelFlatten() throws IOException {
		ByteArrayOutputStream outputStream = loadLowellSphereModelAndFlattenWithOptions(
			new FlattenOptions(asList("face_with_bricks.ldr", "face.ldr")));

		assertContentEquals(
			loadLowellSphereContent(),
			toInputStream(outputStream));
	}

	private ByteArrayOutputStream loadLowellSphereModelAndFlattenWithOptions(FlattenOptions options) throws IOException {
		MultiPartDocument document;
		try (InputStream inputStream = loadLowellSphereContent()) {
			LDrawReader reader = new LDrawReader();
			document = reader.readMPD(inputStream);
		}

		LDrawFlattener flattener = new LDrawFlattener();
		MultiPartDocument flattenDocument = flattener.flatten(document, options);

		LDrawWriter writer = new LDrawWriter();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		writer.write(flattenDocument, outputStream);
		return outputStream;
	}

}
