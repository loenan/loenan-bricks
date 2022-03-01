package com.loenan.bricks.ldraw;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestUtils {

	public static InputStream loadLowellSphereContent() {
		return TestUtils.class.getResourceAsStream("/lowell_sphere.mpd");
	}

	public static InputStream toInputStream(ByteArrayOutputStream byteArrayOutputStream) {
		return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
	}

	public static void assertContentEquals(InputStream expectedContent, InputStream actualContent) throws IOException {
		List<String> expectedLines = readLines(expectedContent);
		List<String> actualLines = readLines(actualContent);
		IntStream.range(0, Math.min(actualLines.size(), expectedLines.size()))
			.forEach(i -> assertEquals(expectedLines.get(i), actualLines.get(i), String.format("Lines #%d differ", i+1)));
		assertEquals(expectedLines.size(), actualLines.size(), "Line count differs");
	}
	private static List<String> readLines(InputStream inputStream) throws IOException {
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
			return reader.lines().collect(toList());
		}
	}
}
