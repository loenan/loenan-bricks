package com.loenan.bricks.ldraw.writer;

import com.loenan.bricks.ldraw.model.ItemReference;
import com.loenan.bricks.ldraw.model.LDrawFile;
import com.loenan.bricks.ldraw.model.MultiPartDocument;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UncheckedIOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

public class LDrawWriter {

	public void write(MultiPartDocument document, OutputStream outputStream) throws IOException {
		try (Writer writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8)) {

			Map<String, LDrawFile> files = new LinkedHashMap<>();
			addFileAndSubFiles(files, document.getMainModel());

			files.values().stream()
					.flatMap(LDrawFile::getCommandLines)
					.forEach(line -> {
						try {
							writer.append(line.getLine()).append('\n');
						} catch (IOException e) {
							throw new UncheckedIOException(e);
						}
					});
		} catch (UncheckedIOException e) {
			throw e.getCause();
		}
	}

	private void addFileAndSubFiles(Map<String, LDrawFile> files, LDrawFile file) {
		String fileName = file.getBaseName();
		if (files.containsKey(fileName)) {
			return;
		}

		files.put(fileName, file);

		// search and add sub-files
		file.getItemReferences().stream()
				.map(ItemReference::getItem)
				.filter(item -> item instanceof LDrawFile)
				.forEach(item -> addFileAndSubFiles(files, (LDrawFile) item));
	}
}
