package com.loenan.bricks.ldraw.geometry;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;

class Format {

	private static final NumberFormat FORMAT = new DecimalFormat("#.######", new DecimalFormatSymbols(Locale.US));

	static String format(double x) {
		String result = FORMAT.format(x);
		// Some small negative values can be rounded and formatted to "-0". Replace it to "0".
		return result.equals("-0") ? "0" : result;
	}

	static String format(Vector v) {
		return Stream.of(v.getX(), v.getY(), v.getZ())
			.map(Format::format)
			.collect(joining(" "));
	}

	static String toString(Vector v) {
		return Stream.of(v.getX(), v.getY(), v.getZ())
			.map(Format::format)
			.collect(joining(", ", "(", ")"));
	}

	static String format(Matrix m) {
		return Stream.of(m.getA(), m.getB(), m.getC(), m.getD(), m.getE(), m.getF(), m.getG(), m.getH(), m.getI())
			.map(Format::format)
			.collect(joining(" "));
	}

	static String toString(Matrix m) {
		return Stream.of(
			Stream.of(m.getA(), m.getB(), m.getC()),
			Stream.of(m.getD(), m.getE(), m.getF()),
			Stream.of(m.getG(), m.getH(), m.getI()))
			.map(triplet -> triplet
				.map(Format::format)
				.collect(joining(", ", "(", ")")))
			.collect(joining(" ", "[", "]"));
	}
}
