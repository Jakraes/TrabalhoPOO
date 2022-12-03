package pt.iscte.poo.item;

import pt.iscte.poo.entity.Hero;
import pt.iscte.poo.utils.Point2D;

public class Armor extends Item {
    public Armor(Point2D position) {
        super("Armor", position);
    }

    @Override
    public void grab() {
        remove();
        Hero.getInstance().setDef(Hero.getInstance().getDef() + 50);
    }

    @Override
    public void drop(Point2D position) {
        add(position);
        Hero.getInstance().setDef(Hero.getInstance().getDef() - 50);
    }
}
