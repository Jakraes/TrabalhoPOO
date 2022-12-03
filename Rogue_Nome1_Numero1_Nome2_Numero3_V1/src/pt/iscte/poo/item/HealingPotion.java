package pt.iscte.poo.item;

import pt.iscte.poo.entity.Hero;
import pt.iscte.poo.interfaces.Usable;
import pt.iscte.poo.utils.Point2D;

public class HealingPotion extends Item implements Usable {

    public HealingPotion(Point2D position) {
        super("HealingPotion", position);
    }

    @Override
    public void grab() {
        remove();
    }

    @Override
    public void drop(Point2D position) {
        add(position);
    }

    @Override
    public void use() {
        Hero.getInstance().setHp(Hero.getInstance().getMaxHp());
        Hero.getInstance().removeItem(this);
    }
}
