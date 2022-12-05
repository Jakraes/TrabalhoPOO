package pt.iscte.poo.item;

import pt.iscte.poo.entity.Hero;
import pt.iscte.poo.utils.Point2D;

public class Hammer extends Item {
    public Hammer(Point2D position) {
        super("Hammer", position);
    }

    @Override
    public void grab() {
        remove();
        Hero.getInstance().setAtk(Hero.getInstance().getAtk() + Hero.getInstance().getBaseAtk() * 2);
    }

    @Override
    public void drop(Point2D position) {
        add(position);
        Hero.getInstance().setAtk(Hero.getInstance().getAtk() - Hero.getInstance().getBaseAtk() * 2);
    }
}
