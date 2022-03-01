package com.loenan.bricks.ldraw.color;

import org.apache.commons.lang3.function.Failable;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableMap;
import static java.util.stream.Collectors.toMap;

public class Colors {

	private static final List<Class<?>> COLOR_CLASSES = asList(
			SolidColor.class,
			TransparentColor.class,
			MetallicColor.class,
			PearlColor.class,
			ChromeColor.class
	);

	private static final Map<Integer, Color> COLORS_BY_ID = unmodifiableMap(Stream.concat(
		Stream.of(Color.MAIN_COLOR, Color.EDGE_COLOR),
		COLOR_CLASSES.stream()
			.map(Class::getFields)
			.flatMap(Arrays::stream)
			.filter(field -> Modifier.isStatic(field.getModifiers()))
			.filter(field -> field.getType() == Color.class)
			.map(Failable.asFunction(field -> {
				Color color = (Color) field.get(null);
				color.setName(field.getName());
				return color;
			})))
		.collect(toMap(Color::getColorId, Function.identity(), (u, v) -> u)));

	public static Color getById(int colorId) {
		return COLORS_BY_ID.get(colorId);
	}

	public static Color getByName(String colorName) {
		for (Class<?> cl: COLOR_CLASSES) {
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
