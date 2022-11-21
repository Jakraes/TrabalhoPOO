package pt.iscte.poo.tile;

import pt.iscte.poo.engine.GameElement;
import pt.iscte.poo.utils.Point2D;

public abstract class Tile extends GameElement {
    public Tile(String name, Point2D position, boolean walkable) {
        super(name, position, 0, walkable);
    }
}
