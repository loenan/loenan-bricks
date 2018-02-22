package com.loenan.bricks.sphere.generator.color;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

@Component
public class ColorSelectorRegistry {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private final Map<String, ColorSelector> colorSelectorMap;

	private final ColorSelector defaultColorSelector;

	@Autowired
	public ColorSelectorRegistry(List<ColorSelector> colorSelectors) {
		colorSelectorMap = colorSelectors.stream()
				.peek(cs -> logger.info("Registered Color selector '{}'.", cs.getScheme()))
				.collect(toMap(ColorSelector::getScheme, identity()));
		defaultColorSelector = colorSelectorMap.get(NeutralColorSelector.SCHEME);
	}

	public ColorSelector getColorSelector(String scheme) {
		return colorSelectorMap.getOrDefault(scheme, defaultColorSelector);
	}
}

