package pt.iscte.poo.engine;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.interfaces.Walkable;
import pt.iscte.poo.utils.Point2D;

public abstract class GameElement implements ImageTile, Walkable {
    private String name; // Nome da imagem
    private Point2D position; // Posição do elemento na grid gráfica
    private int layer; // Layer da imagem
    private boolean walkable; // Pode-se atravessar o elemento?

    public GameElement(String name, Point2D position, int layer, boolean walkable) {
        this.name = name;
        this.position = position;
        this.layer = layer;
        this.walkable = walkable;
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

    public boolean isWalkable() {
        return walkable;
    }
}
