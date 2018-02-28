package com.loenan.bricks.sphere.generator;

import com.loenan.bricks.sphere.generator.color.ColorSchemeRegistry;
import com.loenan.bricks.sphere.generator.color.ColorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SphereGeneratorFactory {

	private final ColorSchemeRegistry colorSchemeRegistry;

	private final ColorRepository colorRepository;

	@Autowired
	public SphereGeneratorFactory(ColorSchemeRegistry colorSchemeRegistry,
								  ColorRepository colorRepository) {
		this.colorSchemeRegistry = colorSchemeRegistry;
		this.colorRepository = colorRepository;
	}

	public SphereGenerator createSphereGenerator() {
		return new SphereGenerator(colorSchemeRegistry, colorRepository);
	}
}
