package pt.iscte.poo.item;

import pt.iscte.poo.entity.Hero;
import pt.iscte.poo.utils.Point2D;

public class Sword extends Item {
    public Sword(Point2D position) {
        super("Sword", position);
    }

    @Override
    public void grab() {
        remove();
        Hero.getInstance().setAtk(Hero.getInstance().getAtk() + Hero.getInstance().getBaseAtk());
    }

    @Override
    public void drop(Point2D position) {
        add(position);
        Hero.getInstance().setAtk(Hero.getInstance().getAtk() - Hero.getInstance().getBaseAtk());
    }
}
