package pt.iscte.poo.entity;

import pt.iscte.poo.effects.StatusEffect;
import pt.iscte.poo.engine.Engine;
import pt.iscte.poo.engine.GameElement;
import pt.iscte.poo.gui.ImageMatrixGUI;
import pt.iscte.poo.interfaces.Attackable;
import pt.iscte.poo.interfaces.Movable;
import pt.iscte.poo.interfaces.Walkable;
import pt.iscte.poo.tile.Tile;
import pt.iscte.poo.utils.Point2D;

import java.util.ArrayList;

public abstract class Entity extends GameElement implements Movable, Attackable, Walkable {
    private int maxHp;
    private int hp;
    private int atk;
    private int def;
    private int lastTurn = Engine.getTurns();
    private ArrayList<StatusEffect> effects;

    public Entity(String name, Point2D position, int maxHp, int atk, int def) {
        super(name, position, 3);
        this.maxHp = maxHp;
        this.hp = maxHp;
        this.atk = atk;
        this.def = def;
        effects = new ArrayList<>();
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
        this.hp = Math.min(hp, maxHp);
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

    @Override
    public boolean isWalkable() {
        return false;
    }

    @Override
    public void onDeath() {
        // Does nothing
    }

    protected void updateTurn() {
        lastTurn = Engine.getTurns();
    }

    protected int getLastTurn() {
        return lastTurn;
    }

    protected void checkForCollision(Point2D nextPosition) {
        if (!nextPosition.equals(getPosition())) {
            for (Object o : Engine.getInstance().getRoom().get(o -> o instanceof Entity)) {
                if (((Entity) o).getPosition().equals(nextPosition)) {
                    attack((Entity) o);
                    checkDeath((Entity) o);
                    if (this instanceof Hero) {
                        Engine.addTurn();
                    }

                    return;
                }
            }

            for (Object o : Engine.getInstance().getRoom().get(o -> o instanceof Tile)) {
                if (((Tile) o).getPosition().equals(nextPosition)) {
                    if (((Tile) o).isWalkable()) {
                        setPosition(nextPosition);

                        if (this instanceof Hero) {
                            Engine.addTurn();
                        }

                        return;
                    }
                }
            }
        }
    }

    public void checkDeath(Entity e) {
        if (e.getHp() <= 0) {
            ImageMatrixGUI.getInstance().removeImage(e);
            Engine.getInstance().getRoom().remove(e);
        }
    }
}