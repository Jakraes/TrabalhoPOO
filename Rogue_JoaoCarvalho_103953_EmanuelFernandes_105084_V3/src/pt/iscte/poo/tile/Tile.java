package pt.iscte.poo.tile;

import pt.iscte.poo.engine.GameElement;
import pt.iscte.poo.interfaces.Walkable;
import pt.iscte.poo.utils.Point2D;

public abstract class Tile extends GameElement implements Walkable {
    public Tile(String name, Point2D position) {
        super(name, position, 1);
    }
}
