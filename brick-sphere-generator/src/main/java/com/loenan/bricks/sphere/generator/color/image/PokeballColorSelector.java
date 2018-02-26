package com.loenan.bricks.sphere.generator.color.image;

import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class PokeballColorSelector extends ImagePickerColorSelector {
	public static final String SCHEME = "pokeball";

	private static final String IMAGE_RESOURCE = "images/pokeball.png";

	public PokeballColorSelector() throws IOException {
		super(SCHEME, IMAGE_RESOURCE);
	}
}
