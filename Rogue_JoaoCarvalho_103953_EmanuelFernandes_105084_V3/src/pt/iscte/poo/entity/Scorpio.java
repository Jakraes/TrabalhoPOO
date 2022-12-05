package pt.iscte.poo.entity;

import pt.iscte.poo.effects.PoisonEffect;
import pt.iscte.poo.engine.Engine;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

public class Scorpio extends Entity {
    public Scorpio(Point2D position) {
        super("Scorpio", position, 2, 0, 0);
    }

    @Override
    public void attack(Entity e) {
        if (e instanceof Hero && Math.random() > e.getDef() / 100.0) {
            e.addEffect(new PoisonEffect());
        }
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
