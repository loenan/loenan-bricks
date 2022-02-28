# LDraw Builder

LDraw Builder library is a simple Java library to build and write Lego models [in LDraw format](http://www.ldraw.org/).

It provides a fluent API to easily generate models following [the LDraw format specification](http://www.ldraw.org/article/218.html).

Generated model files can then be used with [the LDraw tools](http://www.ldraw.org/help/getting-started.html), such as:
- LDview, to view the model
- MLCad, to edit the model
- LPub3D, to create building instructions for the model

## Features

### File formats
- `.mpd` files (see `MultiPartDocument` class)
- `.ldr` files (see `LDrawFile` class)

### Supported command lines
- META commands / comments (line type 0)
- Item references (line type 1). 

NB: Currently, line types 2 (lines), 3 (triangles), 4 (quadrilaterals), 5 (optional lines) are **not** supported.

### Colors
- Most colors declared with constants (see `com.loenan.bricks.ldraw.color` package), wrapping their LDraw color ID and their RGB representation. 
- With `ColorSet`, get the closest color in a selection of LDraw colors for a given RGB value.
- Use `Colors.getByName(name)` to retrieve a color by its constant name.

### Parts
- Common parts declared with constants (see `com.loenan.bricks.ldraw.color` package), wrapping their LDraw design ID and their description.

### Geometry and transformations
- Positions represented by the `Vector` class.
- Transformations represented by the `Matrix` class.
- Rotation transformation matrices computed from the `Transformation` class. 
- Matrix computation: 
    - Transformation matrix combination.
    - Position (`Vector`) transformed by a `Matrix`.

### Building models
- `LDrawBuilder` class provides a fluent API to generate `LDrawFile` instances

### Writing models
- `LDrawWriter` class writes `MultiPartDocument` or `LDrawFile` content to an `OutputStrream` 

## Example

To generate a [Lowell Sphere](http://www.brucelowell.com/lowell-sphere/instructions/) model:

```java
LDrawFile face = new LDrawBuilder("face")
        .setCurrentColor(SolidColor.RED)
        .add(0, -8, 30, Plate.PLATE_1x4)
        .add(0, -8, -30, Plate.PLATE_1x4)
        .add(30, -8, 10, PlateModified.PLATE_1x2_JUMPER)
        .add(-30, -8, 10, PlateModified.PLATE_1x2_JUMPER)
        .add(30, -8, -10, PlateModified.PLATE_1x2_JUMPER)
        .add(-30, -8, -10, PlateModified.PLATE_1x2_JUMPER)
        .addStep()
        .add(10, -16, 10, Plate.PLATE_2x2_CORNER)
        .add(-10, -16, 10, Transformation.rotationY(Angle.ANTI_QUARTER_TURN), Plate.PLATE_2x2_CORNER)
        .add(10, -16, -10, Transformation.rotationY(Angle.QUARTER_TURN), Plate.PLATE_2x2_CORNER)
        .add(-10, -16, -10, Transformation.rotationY(Angle.HALF_TURN), Plate.PLATE_2x2_CORNER)
        .addStep()
        .add(0, -24, 0, Plate.PLATE_2x2)
        .build();

LDrawFile faceWithSnotBricks = new LDrawBuilder("face_with_bricks")
        .setCurrentColor(SolidColor.BLACK)
        .add(30, 0, 30, BrickModified.BRICK_MODIFIED_1x1_STUDS_ON_4_SIDES)
        .add(-30, 0, 30, BrickModified.BRICK_MODIFIED_1x1_STUDS_ON_4_SIDES)
        .add(30, 0, -30, BrickModified.BRICK_MODIFIED_1x1_STUDS_ON_4_SIDES)
        .add(-30, 0, -30, BrickModified.BRICK_MODIFIED_1x1_STUDS_ON_4_SIDES)
        .addStep()
        .add(0, 0, 0, face)
        .build();

LDrawFile lowellSphere = new LDrawBuilder("lowell_sphere")
        .setDescription("Lowell Sphere")
        .setAuthor("Bruce Lowell")
        .add(0, -40, 0, Transformation.IDENTITY, faceWithSnotBricks)
        .add(0, 40, 0, Transformation.rotationZ(Angle.HALF_TURN), faceWithSnotBricks)
        .addStep()
        .add(40, 0, 0, Transformation.rotationZ(Angle.ANTI_QUARTER_TURN)
                .combine(Transformation.rotationX(Angle.QUARTER_TURN)), face)
        .add(-40, 0, 0, Transformation.rotationZ(Angle.QUARTER_TURN)
                .combine(Transformation.rotationX(Angle.QUARTER_TURN)), face)
        .addStep()
        .add(0, 0, 40, Transformation.rotationX(Angle.QUARTER_TURN)
                .combine(Transformation.rotationZ(Angle.ANTI_QUARTER_TURN)), face)
        .add(0, 0, -40, Transformation.rotationX(Angle.ANTI_QUARTER_TURN)
                .combine(Transformation.rotationZ(Angle.ANTI_QUARTER_TURN)), face)
        .build();

MultiPartDocument mpd = new MultiPartDocument(lowellSphere);

LDrawWriter writer = new LDrawWriter();
try (FileOutputStream outputStream = new FileOutputStream("lowell_sphere.mpd")) {
    writer.write(mpd, outputStream);
}
```

See [LowellSphereTest.java](src/test/java/com/loenan/bricks/ldraw/writer/LowellSphereTest.java).
