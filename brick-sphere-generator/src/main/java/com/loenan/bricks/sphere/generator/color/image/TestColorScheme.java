package com.loenan.bricks.sphere.generator.color.image;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Order(100)
public class TestColorScheme extends ImagePickerColorScheme {

	public static final String SCHEME_NAME = "colortest";

	private static final String IMAGE_RESOURCE = "images/color_test.png";

	public TestColorScheme() throws IOException {
		super(SCHEME_NAME, IMAGE_RESOURCE);
	}
}
