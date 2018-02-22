package com.loenan.bricks.ldraw.color;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;

import static java.util.Arrays.asList;

public class Colors {

	public static final List<Class<?>> COLOR_CLASSES = asList(
			SolidColor.class,
			TransparentColor.class,
			MetallicColor.class,
			PearlColor.class,
			ChromeColor.class
	);

	public static Color getByName(String colorName) {
		for (Class cl: COLOR_CLASSES) {
			try {
				Field field = cl.getField(colorName);
				if (Modifier.isStatic(field.getModifiers())) {
					Object value = field.get(null);
					if (value instanceof Color) {
						return (Color) value;
					}
				}
			} catch (IllegalAccessException | NoSuchFieldException ignored) {
			}
		}
		return null;
	}
}
