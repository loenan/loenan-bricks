package com.loenan.bricks.ldraw.builder;

import com.loenan.bricks.ldraw.color.Color;
import com.loenan.bricks.ldraw.geometry.Matrix;
import com.loenan.bricks.ldraw.geometry.Point;
import com.loenan.bricks.ldraw.model.Comment;
import com.loenan.bricks.ldraw.model.ItemReference;
import com.loenan.bricks.ldraw.model.LDrawFile;
import com.loenan.bricks.ldraw.model.LDrawItem;

import java.util.ArrayList;
import java.util.List;

import static com.loenan.bricks.ldraw.geometry.Transformation.IDENTITY;
import static java.util.Objects.requireNonNull;

public class LDrawBuilder {

    private final static String AUTHOR_PREFIX = "Author: ";

    private final static String COPYRIGHT_PREFIX = "(c) ";

    private final String baseName;

    private String description;

    private String author;

    private String copyright;

    private List<ItemReference> itemReferences = new ArrayList<>();

    private Color currentColor = Color.MAIN_COLOR;

    public LDrawBuilder(String baseName) {
        this.baseName = baseName;
    }

    public LDrawBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public LDrawBuilder setAuthor(String author) {
        this.author = author;
        return this;
    }

    public LDrawBuilder setCopyright(String copyright) {
        this.copyright = copyright;
        return this;
    }

    public LDrawBuilder setCurrentColor(Color currentColor) {
        this.currentColor = requireNonNull(currentColor);
        return this;
    }

    public LDrawBuilder add(double x, double y, double z, LDrawItem item) {
        return add(new Point(x, y, z), IDENTITY, item);
    }

    public LDrawBuilder add(double x, double y, double z, Matrix transformation, LDrawItem item) {
        return add(new Point(x, y, z), transformation, item);
    }

    public LDrawBuilder add(Point position, LDrawItem item) {
        return add(position, IDENTITY, item);
    }

    public LDrawBuilder add(Point position, Matrix transformation, LDrawItem item) {
        itemReferences.add(new ItemReference(currentColor, position, transformation, item));
        return this;
    }

    public LDrawFile build() {
        return new LDrawFile(baseName, buildComments(), itemReferences);
    }

    private List<Comment> buildComments() {
        List<Comment> comments = new ArrayList<>(3);
        if (description != null) {
            comments.add(new Comment(description));
        }
        if (author != null) {
            comments.add(new Comment(AUTHOR_PREFIX + author));
        }
        if (copyright != null) {
            comments.add(new Comment(COPYRIGHT_PREFIX + copyright));
        }
        return comments;
    }
}
