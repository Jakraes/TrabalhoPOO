package pt.iscte.poo.item;

import pt.iscte.poo.engine.Engine;
import pt.iscte.poo.engine.GameElement;
import pt.iscte.poo.gui.ImageMatrixGUI;
import pt.iscte.poo.interfaces.Pickable;
import pt.iscte.poo.utils.Point2D;

public abstract class Item extends GameElement implements Pickable {
    public Item(String name, Point2D position) {
        super(name, position, 1, true);
    }

    public void removeItem() {
        Engine.getInstance().getRoom().getItems().remove(this);
        ImageMatrixGUI.getInstance().removeImage(this);
    }

    public void putItem(Point2D position) {
        setPosition(position);
        Engine.getInstance().getRoom().getItems().add(this);
        ImageMatrixGUI.getInstance().addImage(this);
    }
}
