package pt.iscte.poo.item;

import pt.iscte.poo.engine.Engine;
import pt.iscte.poo.engine.GameElement;
import pt.iscte.poo.gui.ImageMatrixGUI;
import pt.iscte.poo.interfaces.Pickable;
import pt.iscte.poo.utils.Point2D;

public abstract class Item extends GameElement implements Pickable {
    public Item(String name, Point2D position) {
        super(name, position, 2);
    }

    public void remove() {
        ImageMatrixGUI.getInstance().removeImage(this);
        Engine.getInstance().getRoom().remove(this);
    }

    public void add(Point2D position) {
        setPosition(position);
        ImageMatrixGUI.getInstance().addImage(this);
        Engine.getInstance().getRoom().add(this);
    }
}
