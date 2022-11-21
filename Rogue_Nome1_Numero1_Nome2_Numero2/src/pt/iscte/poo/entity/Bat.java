package pt.iscte.poo.entity;

import pt.iscte.poo.engine.Engine;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

public class Bat extends Entity {
    int lastTurn = 0;
    public Bat(Point2D position) {
        super("Bat", position, 3, 1, 0);
    }

    @Override
    public void attack(Entity e) {
        if (e instanceof Hero && Math.random() > e.getDef() / 100) {
            e.setHp(e.getHp() - getAtk());
            int heal = 1 + (int)(Math.random() * 3);
            setHp((getHp() + heal > getMaxHp()) ? getMaxHp() : getHp() + heal);
        }
    }

    @Override
    public void move() {
        if (lastTurn != Engine.turns) {
            Point2D newPosition;
            if (Math.random() < 0.5) {
                newPosition = getPosition().plus(Direction.random().asVector());
            } else {
                Point2D heroPosition = Hero.getInstance().getPosition();

                int difX = heroPosition.getX() - getPosition().getX();
                int difY = heroPosition.getY() - getPosition().getY();

                newPosition = getPosition().plus(new Vector2D((difX == 0) ? 0 : difX / Math.abs(difX), (difY == 0) ? 0 : difY / Math.abs(difY)));
            }
            checkForCollision(newPosition);
        }
        lastTurn = Engine.turns;
    }
}
