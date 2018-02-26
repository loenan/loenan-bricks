package com.loenan.bricks.sphere.generator.color.image;

import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class TestColorSelector extends ImagePickerColorSelector {

	public static final String SCHEME = "colortest";

	private static final String IMAGE_RESOURCE = "images/color_test.png";

	public TestColorSelector() throws IOException {
		super(SCHEME, IMAGE_RESOURCE);
	}
}
