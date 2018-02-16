package com.loenan.bricks.ldraw.geometry;

public class Matrix {

    private final double a, b, c;

    private final double d, e, f;

    private final double g, h, i;

    public Matrix(
            double a, double b, double c,
            double d, double e, double f,
            double g, double h, double i) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.e = e;
        this.f = f;
        this.g = g;
        this.h = h;
        this.i = i;
    }

    public double getA() {
        return a;
    }

    public double getB() {
        return b;
    }

    public double getC() {
        return c;
    }

    public double getD() {
        return d;
    }

    public double getE() {
        return e;
    }

    public double getF() {
        return f;
    }

    public double getG() {
        return g;
    }

    public double getH() {
        return h;
    }

    public double getI() {
        return i;
    }

    public Point transform(Point p) {
        return new Point(
                a * p.getX() + b * p.getY() + c * p.getZ(),
                d * p.getX() + e * p.getY() + f * p.getZ(),
                g * p.getX() + h * p.getY() + i * p.getZ());
    }

    public String format() {
        return a + " " + b + " " + c + " " +
                d + " " + e + " " + f + " " +
                g + " " + h + " " + i;
    }
}