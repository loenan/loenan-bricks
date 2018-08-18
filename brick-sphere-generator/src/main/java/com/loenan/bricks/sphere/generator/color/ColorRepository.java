package com.loenan.bricks.sphere.generator.color;

import com.loenan.bricks.ldraw.color.Color;
import com.loenan.bricks.ldraw.color.ColorSet;
import com.loenan.bricks.ldraw.color.Colors;
import com.loenan.bricks.ldraw.model.Part;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ColorRepository {

	private static final String RESOURCE_DIR_PATH = "colors_by_part/";

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private Map<String, ColorSet> colorsByDesignId = new ConcurrentHashMap<>();

	public ColorSet getColorsByPart(Part part) {
		return colorsByDesignId.computeIfAbsent(part.getDesignId(), this::loadColorsForDesignId);
	}

	private ColorSet loadColorsForDesignId(String designId) {
		List<Color> colors = new ArrayList<>();
		try (InputStream inputStream = getClass().getClassLoader()
				.getResourceAsStream(RESOURCE_DIR_PATH + designId)) {
			if (inputStream == null) {
				logger.info("No color found for part {}.", designId);
			} else {
				BufferedReader reader = new BufferedReader(new InputStreamReader(
						inputStream, StandardCharsets.UTF_8));
				String colorName;
				while ((colorName = reader.readLine()) != null) {
					Color color = Colors.getByName(colorName);
					if (color != null) {
						colors.add(color);
					} else  {
						logger.info("Color {} for part {} not found.", colorName, designId);
					}
				}
			}
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
		return new ColorSet(colors);
	}
}
