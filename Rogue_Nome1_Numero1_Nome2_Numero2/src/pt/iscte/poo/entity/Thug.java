package pt.iscte.poo.entity;

import pt.iscte.poo.engine.Engine;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

public class Thug extends Entity {
    int lastTurn = Engine.turns;
    public Thug(Point2D position) {
        super("Thug", position, 10, 3, 0);
    }

    @Override
    public void attack(Entity e) {
        if (e instanceof Hero && Math.random() > e.getDef() / 100) {
            if (Math.random() < 0.3) {
                e.setHp(e.getHp() - getAtk());
            }
        }
    }

    @Override
    public void move() {
        if (lastTurn != Engine.turns) {
            Point2D newPosition;

            Point2D heroPosition = Hero.getInstance().getPosition();

            int difX = heroPosition.getX() - getPosition().getX();
            int difY = heroPosition.getY() - getPosition().getY();

            newPosition = getPosition().plus(new Vector2D((difX == 0) ? 0 : difX / Math.abs(difX), (difY == 0) ? 0 : difY / Math.abs(difY)));

            checkForCollision(newPosition);
        }
        lastTurn = Engine.turns;
    }
}
