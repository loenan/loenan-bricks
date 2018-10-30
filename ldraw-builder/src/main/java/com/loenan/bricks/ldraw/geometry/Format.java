package com.loenan.bricks.ldraw.geometry;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class Format {

	private static final NumberFormat FORMAT = new DecimalFormat("#.######");

	public static String format(double x) {
		String result = FORMAT.format(x);
		// Some small negative values can be rounded and formatted to "-0". Replace it to "0".
		return result.equals("-0") ? "0" : result;
	}

	static String format(Vector v) {
		return format(v.getX()) + " " +
				format(v.getY()) + " " +
				format(v.getZ());
	}

	static String format(Matrix m) {
		return format(m.getA()) + " " +
				format(m.getB()) + " " +
				format(m.getC()) + " " +

				format(m.getD()) + " " +
				format(m.getE()) + " " +
				format(m.getF()) + " " +

				format(m.getG()) + " " +
				format(m.getH()) + " " +
				format(m.getI());
	}
}
