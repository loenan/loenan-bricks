package com.loenan.bricks.sphere.generator.color;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

@Component
public class ColorSchemeRegistry {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private final Map<String, ColorScheme> colorSchemeMap;

	private final ColorScheme defaultColorScheme;

	@Autowired
	public ColorSchemeRegistry(List<ColorScheme> colorSchemes) {
		colorSchemeMap = colorSchemes.stream()
				.peek(cs -> logger.info("Registered Color scheme '{}'.", cs.getSchemeName()))
				.collect(toMap(ColorScheme::getSchemeName, identity(), (u, v) -> u, LinkedHashMap::new));
		defaultColorScheme = colorSchemeMap.get(NeutralColorScheme.SCHEME_NAME);
	}

	public ColorScheme getColorScheme(String schemeName) {
		return colorSchemeMap.getOrDefault(schemeName, defaultColorScheme);
	}

	public List<String> getSchemeNames() {
		return new ArrayList<>(colorSchemeMap.keySet());
	}
}

