package com.loenan.bricks.ldraw.reader;

import com.loenan.bricks.ldraw.model.CommandLine;
import com.loenan.bricks.ldraw.model.LDrawFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

public class LDrawReader {

	public LDrawFile read(InputStream inputStream) throws IOException {
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
			return new LDrawFile(reader.lines()
				.map(LineReader::new)
				.map(CommandLine::read)
				.filter(Objects::nonNull)
				.collect(toList()));
		}
	}
}
