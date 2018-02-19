package com.loenan.bricks.ldraw.geometry;

import static com.loenan.bricks.ldraw.geometry.Angles.ANTI_QUARTER_TURN;
import static com.loenan.bricks.ldraw.geometry.Angles.HALF_TURN;
import static com.loenan.bricks.ldraw.geometry.Angles.QUARTER_TURN;
import static com.loenan.bricks.ldraw.geometry.Transformation.*;

public enum CubeFace {

    TOP(0, -1, 0, IDENTITY),
    BOTTOM(0, 1, 0, rotationZ(HALF_TURN)),
    FRONT(1, 0, 0, rotationZ(ANTI_QUARTER_TURN).combine(rotationX(QUARTER_TURN))),
    BACK(-1, 0, 0, rotationZ(QUARTER_TURN).combine(rotationX(QUARTER_TURN))),
    LEFT(0, 0, 1, rotationX(QUARTER_TURN).combine(rotationZ(ANTI_QUARTER_TURN))),
    RIGHT(0, 0, -1, rotationX(ANTI_QUARTER_TURN).combine(rotationZ(ANTI_QUARTER_TURN)));

    private final Point position;
    private final Matrix transformation;

    CubeFace(double x, double y, double z, Matrix transformation) {
        this.position = new Point(x, y, z);
        this.transformation = transformation;
    }

    public Point getPosition() {
        return position;
    }

    public Matrix getTransformation() {
        return transformation;
    }

    public static void main(String... args) {
        for (CubeFace face: CubeFace.values()) {
            System.out.println(face.name() + ": "
                    + face.getPosition().format() + " / "
                    + face.getTransformation().format());
        }
    }
}
