package com.loenan.bricks.ldraw.color;

import java.util.Collection;

import static java.util.Collections.unmodifiableCollection;
import static java.util.Comparator.comparing;
import static java.util.Objects.requireNonNull;

public class ColorSet {

	private final Collection<Color> colors;

	public ColorSet(Collection<Color> colors) {
		this.colors = unmodifiableCollection(requireNonNull(colors));
	}

	public Color getClosestColor(int red, int green, int blue) {
		return colors.stream()
				.min(comparing(c -> c.getDistanceTo(red, green, blue)))
				.orElse(null);
	}
}
