package com.loenan.bricks.sphere.generator;

import com.loenan.bricks.sphere.generator.color.ColorSelectorRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SphereGeneratorFactory {

	private final ColorSelectorRegistry colorSelectorRegistry;

	@Autowired
	public SphereGeneratorFactory(ColorSelectorRegistry colorSelectorRegistry) {
		this.colorSelectorRegistry = colorSelectorRegistry;
	}

	public SphereGenerator createSphereGenerator() {
		return new SphereGenerator(colorSelectorRegistry);
	}
}
