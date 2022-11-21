package pt.iscte.poo.item;

import pt.iscte.poo.engine.Engine;
import pt.iscte.poo.entity.Hero;
import pt.iscte.poo.gui.ImageMatrixGUI;
import pt.iscte.poo.utils.Point2D;

public class Sword extends Item {

    public Sword(Point2D position) {
        super("Sword", position);
    }

    @Override
    public void pick() {
        removeItem();
        Hero.getInstance().setAtk(Hero.getInstance().getAtk() + 1);
    }

    @Override
    public void drop(Point2D position) {
        putItem(position);
        Hero.getInstance().setAtk(Hero.getInstance().getAtk() - 1);
    }
}
