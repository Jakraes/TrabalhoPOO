package pt.iscte.poo.entity;

import pt.iscte.poo.engine.Engine;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

public class Skeleton extends Entity {
    public Skeleton(Point2D position) {
        super("Skeleton", position, 5, 1, 0);
    }

    @Override
    public void attack(Entity e) {
        if (e instanceof Hero && Math.random() > e.getDef() / 100.0) {
            e.setHp(e.getHp() - getAtk());
        }
        //checkDeath(e);
    }

    @Override
    public void move() {
        if (getLastTurn() != Engine.getTurns() && Engine.getTurns() % 2 == 0) {
            Point2D nextPosition = getPosition().plus(Vector2D.movementVector(getPosition(), Hero.getInstance().getPosition()));
            checkForCollision(nextPosition);
        }
        updateTurn();
    }
}
