package pt.iscte.poo.entity;

import pt.iscte.poo.engine.Engine;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

public class Thug extends Entity {
    public Thug(Point2D position) {
        super("Thug", position, 10, 3, 0);
    }

    @Override
    public void attack(Entity e) {
        if (e instanceof Hero && Math.random() > e.getDef() / 100.0) {
            if (Math.random() < 0.3) {
                e.setHp(e.getHp() - getAtk());
            }
        }
        //checkDeath(e);
    }

    @Override
    public void move() {
        if (getLastTurn() != Engine.getTurns()) {
            Point2D nextPosition = getPosition().plus(Vector2D.movementVector(getPosition(), Hero.getInstance().getPosition()));
            checkForCollision(nextPosition);
        }
        updateTurn();
    }
}
