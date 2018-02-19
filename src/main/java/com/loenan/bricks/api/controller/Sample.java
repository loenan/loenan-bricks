package com.loenan.bricks.api.controller;

import java.util.List;

import static java.util.Arrays.asList;

public interface Sample {
    List<String> LINES = asList(
            "0 FILE sphe_d6.8_c4.ldr",
            "0 Plate Sphere, Diameter 6.8, 4x4x4 Core",
            "0 Author: Bram's Sphere Generator",
            "0 (c) 2018 Bram Lambrecht <lego@bldesign.org>",
            "0",
            " 1 16 0 -40 0 1 0 0 0 1 0 0 0 1 sphe_d6.8_c4_sub.ldr",
            " 1 16 0 40 0 -1 0 0 0 -1 0 0 0 1 sphe_d6.8_c4_sub.ldr",
            " 1 16 40 0 0 0 -1 0 0 0 1 -1 0 0 sphe_d6.8_c4_sub.ldr",
            " 1 16 -40 0 0 0 1 0 0 0 1 1 0 0 sphe_d6.8_c4_sub.ldr",
            " 1 16 0 0 40 0 0 -1 1 0 0 0 -1 0 sphe_d6.8_c4_sub.ldr",
            " 1 16 0 0 -40 0 0 1 1 0 0 0 1 0 sphe_d6.8_c4_sub.ldr",
            "0  ",
            "0 FILE sphe_d6.8_c4_sub.ldr",
            "0 Single Panel for Plate Sphere, Diameter 6.8, 4x4x4 Core",
            "0 Author: Loenan Sphere Generator",
            "0 (c) 2018 Laurent Istin <laurent.istin@free.fr>",
            "0",
            " 1 16 -10 -4 0 10 0 0 0 4 0 0 0 40 box.dat",
            " 1 16 10 -4 0 10 0 0 0 4 0 0 0 40 box.dat",
            " 1 16 -30 -4 0 10 0 0 0 4 0 0 0 40 box.dat",
            " 1 16 30 -4 0 10 0 0 0 4 0 0 0 40 box.dat",
            " 1 16 -30 -8 30 1 0 0 0 1 0 0 0 1 stud.dat",
            " 1 16 -30 -8 -30 1 0 0 0 1 0 0 0 1 stud.dat",
            " 1 16 30 -8 30 1 0 0 0 1 0 0 0 1 stud.dat",
            " 1 16 30 -8 -30 1 0 0 0 1 0 0 0 1 stud.dat",
            " 1 16 -10 -12 0 10 0 0 0 4 0 0 0 40 box.dat",
            " 1 16 10 -12 0 10 0 0 0 4 0 0 0 40 box.dat",
            " 1 16 -10 -16 30 1 0 0 0 1 0 0 0 1 stud.dat",
            " 1 16 -10 -16 -30 1 0 0 0 1 0 0 0 1 stud.dat",
            " 1 16 10 -16 30 1 0 0 0 1 0 0 0 1 stud.dat",
            " 1 16 10 -16 -30 1 0 0 0 1 0 0 0 1 stud.dat",
            " 1 16 -30 -12 0 10 0 0 0 4 0 0 0 20 box.dat",
            " 1 16 30 -12 0 10 0 0 0 4 0 0 0 20 box.dat",
            " 1 16 -30 -16 10 1 0 0 0 1 0 0 0 1 stud.dat",
            " 1 16 -30 -16 -10 1 0 0 0 1 0 0 0 1 stud.dat",
            " 1 16 30 -16 10 1 0 0 0 1 0 0 0 1 stud.dat",
            " 1 16 30 -16 -10 1 0 0 0 1 0 0 0 1 stud.dat",
            " 1 16 -10 -20 0 10 0 0 0 4 0 0 0 20 box.dat",
            " 1 16 10 -20 0 10 0 0 0 4 0 0 0 20 box.dat",
            " 1 16 -10 -24 10 1 0 0 0 1 0 0 0 1 stud.dat",
            " 1 16 -10 -24 -10 1 0 0 0 1 0 0 0 1 stud.dat",
            " 1 16 10 -24 10 1 0 0 0 1 0 0 0 1 stud.dat",
            " 1 16 10 -24 -10 1 0 0 0 1 0 0 0 1 stud.dat",
            "0");
}
