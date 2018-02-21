package com.loenan.bricks.sphere.generator.color;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ColorSelectorRegistry {

	private final Map<String, ColorSelector> colorSelectorMap = new HashMap<>();

	private final ColorSelector defaultColorSelector;

	@Autowired
	public ColorSelectorRegistry(List<ColorSelector> colorSelectors) {
		for (ColorSelector colorSelector : colorSelectors) {
			String scheme = colorSelector.getScheme();
			if (colorSelectorMap.containsKey(scheme)) {
				throw new IllegalArgumentException("Color selector already registered with this scheme: " + scheme);
			}
			colorSelectorMap.put(scheme, colorSelector);
		}
		defaultColorSelector = colorSelectorMap.get(NeutralColorSelector.SCHEME);
	}

	public ColorSelector getColorSelector(String scheme) {
		return colorSelectorMap.getOrDefault(scheme, defaultColorSelector);
	}
}

