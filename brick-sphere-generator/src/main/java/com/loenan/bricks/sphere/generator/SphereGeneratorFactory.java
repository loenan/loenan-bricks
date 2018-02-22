package com.loenan.bricks.sphere.generator;

import com.loenan.bricks.sphere.generator.color.ColorSelectorRegistry;
import com.loenan.bricks.sphere.generator.color.ColorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SphereGeneratorFactory {

	private final ColorSelectorRegistry colorSelectorRegistry;

	private final ColorRepository colorRepository;

	@Autowired
	public SphereGeneratorFactory(ColorSelectorRegistry colorSelectorRegistry,
								  ColorRepository colorRepository) {
		this.colorSelectorRegistry = colorSelectorRegistry;
		this.colorRepository = colorRepository;
	}

	public SphereGenerator createSphereGenerator() {
		return new SphereGenerator(colorSelectorRegistry, colorRepository);
	}
}
