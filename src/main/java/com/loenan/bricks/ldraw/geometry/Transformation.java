package com.loenan.bricks.ldraw.geometry;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class Transformation {

    public static final Matrix IDENTITY = new Matrix(
            1, 0, 0,
            0, 1, 0,
            0, 0, 1);

    public static Matrix getRotationX(double angle) {
        return new Matrix(
                1, 0, 0,
                0, cos(angle), -sin(angle),
                0, sin(angle), cos(angle));
    }

    public static Matrix getRotationY(double angle) {
        return new Matrix(
                cos(angle), 0, sin(angle),
                0, 1, 0,
                -sin(angle), 0, cos(angle));
    }

    public static Matrix getRotationZ(double angle) {
        return new Matrix(
                cos(angle), -sin(angle), 0,
                sin(angle), cos(angle), 0,
                0, 0, 1);
    }
}
