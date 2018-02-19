package com.loenan.bricks.api.controller;

import com.loenan.bricks.ldraw.builder.LDrawBuilder;
import com.loenan.bricks.ldraw.color.Color;
import com.loenan.bricks.ldraw.color.Colors;
import com.loenan.bricks.ldraw.geometry.CubeFace;
import com.loenan.bricks.ldraw.model.MultiPartDocument;
import com.loenan.bricks.ldraw.model.Parts;
import com.loenan.bricks.ldraw.writer.LDrawWriter;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

@RestController
public class SphereController {

    @GetMapping("/sample/{diameter}")
    public HttpEntity<String> generateSphere(@PathVariable double diameter) {
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.valueOf("application/x-ldraw"));
        header.set(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"LoenanSphere-" + diameter + ".mpd\"");

        String result = Sample.LINES.stream()
                .collect(Collectors.joining("\n"));

        return new HttpEntity<>(result, header);
    }

    @GetMapping("/sphere/{diameter}")
    public void generateSphere(@PathVariable double diameter, HttpServletResponse response) throws IOException {
        response.setContentType("application/x-ldraw");
        response.addHeader(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"LoenanSphere-" + diameter + ".mpd\"");

        LDrawBuilder modelBuilder = new LDrawBuilder("loenan_sphere_" + diameter);
        for (CubeFace face: CubeFace.values()) {
            modelBuilder.add(
                    face.getPosition().mult(diameter * 10),
                    face.getTransformation(),
                    new LDrawBuilder("face_" + face.name().toLowerCase())
                            .setCurrentColor(getColor(face))
                            .add(0, -8, 0, Parts.PLATE_6X8)
                            .add(10, -8, 10, Parts.SLOPE_31_1X1)
                            .build());
        }
        LDrawWriter writer = new LDrawWriter();
        writer.write(new MultiPartDocument(modelBuilder.build()), response.getOutputStream());
    }

    private Color getColor(CubeFace face) {
        switch (face) {
            case TOP:
                return Colors.RED;
            case BOTTOM:
                return Colors.ORANGE;
            case FRONT:
                return Colors.BLUE;
            case BACK:
                return Colors.GREEN;
            case LEFT:
                return Colors.YELLOW;
            case RIGHT:
                return Colors.TAN;
            default:
                return Colors.BLACK;
        }
    }
}
