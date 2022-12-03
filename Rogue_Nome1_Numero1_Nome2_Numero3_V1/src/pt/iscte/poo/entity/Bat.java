package pt.iscte.poo.entity;

import pt.iscte.poo.engine.Engine;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

public class Bat extends Entity {
    public Bat(Point2D position) {
        super("Bat", position, 3, 1, 0);
    }

    @Override
    public void attack(Entity e) {
        if (e instanceof Hero && Math.random() > e.getDef() / 100.0) {
            if (Math.random() > 0.5) {
                e.setHp(e.getHp() - getAtk());
                int heal = 1 + (int) (Math.random() * 3);
                setHp(getHp() + heal);
            }
        }
        checkDeath(e);
    }

    @Override
    public void move() {
        if (getLastTurn() != Engine.getTurns()) {
            Point2D nextPosition;
            if (Math.random() > 0.5) {
                nextPosition = getPosition().plus(Direction.random().asVector());
            } else {
                nextPosition = getPosition().plus(Vector2D.movementVector(getPosition(), Hero.getInstance().getPosition()));
            }
            checkForCollision(nextPosition);
            updateTurn();
        }
    }
}
