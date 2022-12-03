package pt.iscte.poo.item;

import pt.iscte.poo.utils.Point2D;

public class Key extends Item {
    private int keyNumber;

    public Key(Point2D position, int keyNumber) {
        super("Key", position);
        this.keyNumber = keyNumber;
    }

    @Override
    public void grab() {
        remove();
    }

    @Override
    public void drop(Point2D position) {
        add(position);
    }

    public int getKeyNumber() {
        return keyNumber;
    }
}
