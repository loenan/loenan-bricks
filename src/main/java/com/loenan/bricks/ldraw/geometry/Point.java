package com.loenan.bricks.ldraw.geometry;

public class Point {

    private final double x;

    private final double y;

    private final double z;

    public Point(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public Point plus(Point o) {
        return plus(this, o);
    }

    public static Point plus(Point a, Point b) {
        return new Point(a.x + b.x, a.y + b.y, a.z + b.z);
    }

    public String format() {
        return x + " " + y + " " + z;
    }
}
