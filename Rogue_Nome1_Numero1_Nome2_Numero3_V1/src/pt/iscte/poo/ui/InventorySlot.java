package pt.iscte.poo.ui;

import pt.iscte.poo.engine.GameElement;
import pt.iscte.poo.gui.ImageMatrixGUI;
import pt.iscte.poo.item.Item;
import pt.iscte.poo.utils.Point2D;

public class InventorySlot extends GameElement {
    private Item item;
    private boolean selected = false;

    public InventorySlot(Point2D position) {
        super("Inventory1", position, 0);
        item = null;
    }

    @Override
    public String getName() {
        if (selected) {
            return "Inventory2";
        }
        return "Inventory1";
    }

    public void select() {
        selected = true;
    }

    public void unselect() {
        selected = false;
    }

    public Item getItem() {
        return item;
    }

    public void addItem(Item i) {
        if (i != null) {
            item = i;
            item.setPosition(getPosition());
            ImageMatrixGUI.getInstance().addImage(i);
        }
    }

    public void removeItem() {
        if (item != null) {
            ImageMatrixGUI.getInstance().removeImage(item);
            item = null;
        }
    }
}
