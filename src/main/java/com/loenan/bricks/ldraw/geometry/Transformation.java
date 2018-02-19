package com.loenan.bricks.ldraw.geometry;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class Transformation {

    public static final Matrix IDENTITY = new Matrix(
            1, 0, 0,
            0, 1, 0,
            0, 0, 1);

    public static Matrix rotationX(double angle) {
        return new Matrix(
                1, 0, 0,
                0, cos(angle), -sin(angle),
                0, sin(angle), cos(angle));
    }

    public static Matrix rotationY(double angle) {
        return new Matrix(
                cos(angle), 0, sin(angle),
                0, 1, 0,
                -sin(angle), 0, cos(angle));
    }

    public static Matrix rotationZ(double angle) {
        return new Matrix(
                cos(angle), -sin(angle), 0,
                sin(angle), cos(angle), 0,
                0, 0, 1);
    }
}
