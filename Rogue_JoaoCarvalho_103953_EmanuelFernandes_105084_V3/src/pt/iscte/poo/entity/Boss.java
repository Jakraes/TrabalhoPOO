package pt.iscte.poo.entity;

import pt.iscte.poo.engine.Engine;
import pt.iscte.poo.item.Key;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

public class Boss extends Entity {
    private Key key;

    public Boss(Point2D position, int keyNumber) {
        super("Boss", position, 20, 3, 30);
        key = new Key(getPosition(), keyNumber);
    }

    @Override
    public void onDeath() {
        if (key != null) {
            key.drop(getPosition());
        }
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
        if (getLastTurn() != Engine.getTurns()) {
            Point2D nextPosition = getPosition().plus(Vector2D.movementVector(getPosition(), Hero.getInstance().getPosition()));
            checkForCollision(nextPosition);
        }
        updateTurn();
    }
}
