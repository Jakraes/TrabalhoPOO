package pt.iscte.poo.entity;

import pt.iscte.poo.engine.Engine;
import pt.iscte.poo.item.Item;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

public class Thief extends Entity {
    private Item stolenItem = null;

    public Thief(Point2D position) {
        super("Thief", position, 5, 0, 0);
    }

    @Override
    public void onDeath() {
        stolenItem.drop(getPosition());
    }

    @Override
    public void attack(Entity e) {
        if (e instanceof Hero && Math.random() > e.getDef() / 100.0) {
            if (stolenItem == null) {
                int index = (int)(Math.random() * Hero.getInstance().getInventory().size());
                stolenItem = Hero.getInstance().getItem(index);
                Hero.getInstance().removeItem(stolenItem);
            }
        }
    }

    @Override
    public void move() {
        if (getLastTurn() != Engine.getTurns()) {
            Point2D nextPosition;
            if (stolenItem == null) {
                nextPosition = getPosition().plus(Vector2D.movementVector(getPosition(), Hero.getInstance().getPosition()));
            }
            else {
                nextPosition = getPosition().plus(Direction.forVector(Vector2D.movementVector(getPosition(), Hero.getInstance().getPosition())).opposite().asVector());
            }
            checkForCollision(nextPosition);
        }
        updateTurn();
    }
}
