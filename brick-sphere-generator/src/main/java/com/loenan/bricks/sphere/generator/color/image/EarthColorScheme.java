package com.loenan.bricks.sphere.generator.color.image;

import com.loenan.bricks.ldraw.color.Color;
import com.loenan.bricks.ldraw.color.ColorSet;
import com.loenan.bricks.ldraw.color.SolidColor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static java.util.Arrays.asList;

@Component
@Order(1)
public class EarthColorScheme extends ImagePickerColorScheme {

	public static final String SCHEME_NAME = "earth";

	private static final String IMAGE_RESOURCE = "images/Large_World_Topo_Map_2.png";

	private static final ColorSet EARTH_COLORS = new ColorSet(asList(
			SolidColor.BLUE,
			SolidColor.DARK_AZURE,
			SolidColor.DARK_GREEN,
			SolidColor.GREEN,
			SolidColor.TAN,
			SolidColor.WHITE
	));

	public EarthColorScheme() throws IOException {
		super(SCHEME_NAME, IMAGE_RESOURCE);
	}

	@Override
	public Color selectColor(double longitude, double latitude, ColorSet availableColors) {
		return super.selectColor(longitude, latitude, EARTH_COLORS);
	}
}
