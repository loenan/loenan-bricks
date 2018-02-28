package com.loenan.bricks.ldraw.part;

import com.loenan.bricks.ldraw.model.Part;

public interface Tile {

	Part TILE_1x1 = new Part("3070b", "Tile 1 x 1 with Groove");
	Part TILE_1x2 = new Part("3069b", "Tile 1 x 2 with Groove");
	Part TILE_1x3 = new Part(63864, "Tile 1 x 3 with Groove");
	Part TILE_1x4 = new Part(2431, "Tile 1 x 4 with Groove");
	Part TILE_1x6 = new Part(6636, "Tile 1 x 6");
	Part TILE_1x8 = new Part(4162, "Tile 1 x 8");

	Part TILE_2x2_CORNER = new Part(14719, "Tile 2 x 2 Corner");
	Part TILE_2x2 = new Part("6068b", "Tile 2 x 2 with Groove");
	Part TILE_2x3 = new Part(26603, "Tile 2 x 3");
	Part TILE_2x4 = new Part(87079, "Tile 2 x 4 with Groove");

	Part TILE_6x6 = new Part(10202, "Tile 6 x 6 with Groove and Underside Studs");
	Part TILE_8x16 = new Part(90498, "Tile 8 x 16");
}
