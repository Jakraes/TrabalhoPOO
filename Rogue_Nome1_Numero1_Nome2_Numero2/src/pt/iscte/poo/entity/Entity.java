package pt.iscte.poo.entity;

import pt.iscte.poo.engine.Engine;
import pt.iscte.poo.engine.GameElement;
import pt.iscte.poo.interfaces.Attackable;
import pt.iscte.poo.interfaces.Movable;
import pt.iscte.poo.tile.Tile;
import pt.iscte.poo.utils.Point2D;

public abstract class Entity extends GameElement implements Movable, Attackable {
    private int maxHp;
    private int hp;
    private int atk;
    private int def;

    public Entity(String name, Point2D position, int maxHp, int atk, int def) {
        super(name, position, 2, false);
        this.maxHp = maxHp;
        hp = maxHp;
        this.atk = atk;
        this.def = def;
    }

    @Override
    public int getMaxHp() {
        return maxHp;
    }

    @Override
    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }

    @Override
    public int getHp() {
        return hp;
    }

    @Override
    public void setHp(int hp) {
        this.hp = hp;
    }

    @Override
    public int getAtk() {
        return atk;
    }

    @Override
    public void setAtk(int atk) {
        this.atk = atk;
    }

    @Override
    public int getDef() {
        return def;
    }

    @Override
    public void setDef(int def) {
        this.def = def;
    }

    protected void checkForCollision(Point2D newPosition) {
        for (Entity e : Engine.getInstance().getRoom().getEntities()) {
            if (e.getPosition().equals(newPosition)) {
                attack(e);
                return;
            }
        }
        for (Tile t: Engine.getInstance().getRoom().getTiles()) {
            if (t.getPosition().equals(newPosition) && t.isWalkable()) {
                setPosition(newPosition);
                return;
            }
        }
    }

    public boolean checkAlive() {
        return getHp() > 0;
    }
}
