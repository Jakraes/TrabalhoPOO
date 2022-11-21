package pt.iscte.poo.tile;

import pt.iscte.poo.utils.Point2D;

public class Wall extends Tile {
    public Wall(Point2D position) {
        super("Wall", position, false);
    }
}
