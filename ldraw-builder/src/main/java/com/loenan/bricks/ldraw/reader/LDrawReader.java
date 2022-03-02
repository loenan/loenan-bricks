package com.loenan.bricks.ldraw.reader;

import com.loenan.bricks.ldraw.model.CommandLine;
import com.loenan.bricks.ldraw.model.FileMetaCommand;
import com.loenan.bricks.ldraw.model.ItemReference;
import com.loenan.bricks.ldraw.model.LDrawFile;
import com.loenan.bricks.ldraw.model.LDrawFileReference;
import com.loenan.bricks.ldraw.model.MultiPartDocument;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

public class LDrawReader {

	public LDrawFile readLDR(InputStream inputStream) throws IOException {
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
			return new LDrawFile(reader.lines()
				.map(LineReader::new)
				.map(CommandLine::read)
				.filter(Objects::nonNull)
				.collect(toList()));
		}
	}

	public MultiPartDocument readMPD(InputStream inputStream) throws IOException {
		return convertToMultiPartDocument(readLDR(inputStream));
	}

	private MultiPartDocument convertToMultiPartDocument(LDrawFile lDrawFile) {
		// 1. Split sub-models
		List<CommandLine> currentModel = new ArrayList<>();
		List<List<CommandLine>> models = new ArrayList<>();
		for (CommandLine commandLine : lDrawFile.getCommandLines()) {
			if (commandLine instanceof FileMetaCommand) {
				if (!currentModel.isEmpty()) {
					models.add(currentModel);
					currentModel = new ArrayList<>();
				}
			}
			currentModel.add(commandLine);
		}
		models.add(currentModel);

		// 2. Models by name
		Map<String, LDrawFile> modelsByName = models.stream()
			.map(LDrawFile::new)
			.collect(toMap(
				LDrawFile::getName,
				Function.identity(),
				(u, v) -> { throw new IllegalStateException("Duplicate file name: " + u); },
				LinkedHashMap::new));

		// 3. Replace sub-model references by actual sub-models
		modelsByName.values().stream()
			.map(LDrawFile::getCommandLines)
			.flatMap(Collection::stream)
			.forEach(commandLine -> Optional.of(commandLine)
				.filter(ItemReference.class::isInstance)
				.map(ItemReference.class::cast)
				.ifPresent(itemReference -> Optional.of(itemReference.getItem())
					.filter(LDrawFileReference.class::isInstance)
					.map(LDrawFileReference.class::cast)
					.map(LDrawFileReference::getName)
					.map(modelsByName::get)
					.ifPresent(itemReference::replaceItem)));

		// 4. Main model is the first
		LDrawFile mainModel = modelsByName.values().stream().findFirst().get();

		// 5. Check cycles
		checkCycles(mainModel, new LinkedHashSet<>());

		return new MultiPartDocument(mainModel);
	}

	private void checkCycles(LDrawFile currentModel, Set<String> parentModelNames) {
		parentModelNames.add(currentModel.getName());
		currentModel.getCommandLines().stream()
			.filter(ItemReference.class::isInstance)
			.map(ItemReference.class::cast)
			.map(ItemReference::getItem)
			.filter(LDrawFile.class::isInstance)
			.map(LDrawFile.class::cast)
			.peek(subModel -> Optional.ofNullable(subModel.getName())
				.filter(name -> parentModelNames.contains(subModel.getName()))
				.ifPresent(s -> {
					throw new IllegalStateException("Cycle detected in sub-models: " +
							String.join(", ", parentModelNames));
				}))
			.forEach(subModel -> checkCycles(subModel, parentModelNames));
		parentModelNames.remove(currentModel.getName());
	}
}
