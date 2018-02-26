package com.loenan.bricks.sphere.generator.color.image;

import com.loenan.bricks.ldraw.color.Color;
import com.loenan.bricks.ldraw.color.ColorSet;
import com.loenan.bricks.sphere.generator.color.CoordinatesColorSelector;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static java.lang.Math.min;

public abstract class ImagePickerColorSelector extends CoordinatesColorSelector {

	private final BufferedImage image;

	private final int imageWidth;

	private final int imageHeight;

	protected ImagePickerColorSelector(String colorScheme, String imageResource) throws IOException {
		super(colorScheme);
		try (InputStream inputStream = getClass().getClassLoader()
				.getResourceAsStream(imageResource)) {
			if (inputStream == null) {
				throw new IllegalArgumentException("Image resource not found: " + imageResource);
			}
			image = ImageIO.read(inputStream);
			imageWidth = image.getWidth();
			imageHeight = image.getHeight();
		}
	}

	@Override
	public Color selectColor(double longitude, double latitude, ColorSet availableColors) {
		int x = (int) ((180 + longitude) * imageWidth / 360) % imageWidth;
		int y = min((int) ((90 - latitude) * imageHeight / 180), imageHeight - 1);
		int rgb = image.getRGB(x, y);
		return availableColors.getClosestColor(
				(rgb & 0xff0000) >> 16,
				(rgb & 0xff00) >> 8,
				rgb & 0xff);
	}
}
