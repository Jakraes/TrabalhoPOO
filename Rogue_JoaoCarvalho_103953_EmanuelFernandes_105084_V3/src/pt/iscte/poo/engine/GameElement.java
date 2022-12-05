package pt.iscte.poo.engine;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;

public abstract class GameElement implements ImageTile {
    private String name;
    private Point2D position;
    private int layer;

    public GameElement(String name, Point2D position, int layer) {
        this.name = name;
        this.position = position;
        this.layer = layer;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Point2D getPosition() {
        return position;
    }

    public void setPosition(Point2D position) {
        this.position = position;
    }

    @Override
    public int getLayer() {
        return layer;
    }
}
