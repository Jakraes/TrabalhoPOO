package pt.iscte.poo.entity;

import pt.iscte.poo.engine.Engine;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

public class Skeleton extends Entity {
    private int lastTurn = Engine.turns;
    public Skeleton(Point2D position) {
        super("Skeleton", position, 5, 1, 0);
    }

    @Override
    public void attack(Entity e) {
        if (e instanceof Hero && Math.random() > e.getDef() / 100) {
            e.setHp(e.getHp() - getAtk());
        }
        System.out.println("Skeleton attacks " + e.getName());
    }

    @Override
    public void move() {
        if (Engine.turns != lastTurn && Engine.turns % 2 == 0) {
            Point2D heroPosition = Hero.getInstance().getPosition();

            int difX = heroPosition.getX() - getPosition().getX();
            int difY = heroPosition.getY() - getPosition().getY();

            Point2D newPosition = getPosition().plus(new Vector2D((difX == 0) ? 0 : difX / Math.abs(difX), (difY == 0) ? 0 : difY / Math.abs(difY)));

            checkForCollision(newPosition);
        }
        lastTurn = Engine.turns;
    }
}
