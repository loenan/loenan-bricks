package com.loenan.bricks.ldraw.geometry;

import java.text.DecimalFormat;
import java.text.NumberFormat;

class Format {

    private static final NumberFormat FORMAT = new DecimalFormat("#.######");

    static String format(double x) {
        String result = FORMAT.format(x);
        return result.equals("-0") ? "0" : result;
    }

    static String format(Point p) {
        return format(p.getX())
                + " " + format(p.getY())
                + " " + format(p.getZ());
    }

    static String format(Matrix m) {
        return format(m.getA())
                + " " + format(m.getB())
                + " " + format(m.getC())

                + " " + format(m.getD())
                + " " + format(m.getE())
                + " " + format(m.getF())

                + " " + format(m.getG())
                + " " + format(m.getH())
                + " " + format(m.getI());
    }
}
