package com.loenan.bricks.sphere.generator.color.image;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Order(2)
public class PokeballColorScheme extends ImagePickerColorScheme {
	public static final String SCHEME_NAME = "pokeball";

	private static final String IMAGE_RESOURCE = "images/pokeball.png";

	public PokeballColorScheme() throws IOException {
		super(SCHEME_NAME, IMAGE_RESOURCE);
	}
}
