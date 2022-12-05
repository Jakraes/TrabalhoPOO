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
    private int baseAtk;
    private int atk;
    private int def;
    private int lastTurn = Engine.getTurns();
    private ArrayList<StatusEffect> effects;

    public Entity(String name, Point2D position, int maxHp, int atk, int def) {
        super(name, position, 3);
        this.maxHp = maxHp;
        this.hp = maxHp;
        this.baseAtk = atk;
        this.atk = baseAtk;
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
    public int getBaseAtk() {
        return baseAtk;
    }

    @Override
    public void setBaseAtk(int baseAtk) {
        this.baseAtk = baseAtk;
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
        if (lastTurn != Engine.getTurns()) {
            lastTurn = Engine.getTurns();
            applyAllEffects();
        }
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

    public void applyAllEffects() {
        ArrayList<StatusEffect> decayedEffects = new ArrayList<>();
        effects.forEach(o -> {
            if (o.getRemainingTurns() > 0) {
                o.applyEffect(this);
            } else {
                decayedEffects.add(o);
            }
        });
        effects.removeAll(decayedEffects);
    }

    public void addEffect(StatusEffect s) {
        for (StatusEffect e : effects) {
            if (e.getClass().equals(s.getClass())) { // Faz com que não haja efeitos duplicados
                return;
            }
        }
        effects.add(s);
    }

    public void endEffect(int i) {
        effects.remove(i);
    }

    public ArrayList<StatusEffect> getEffects() {
        return new ArrayList<>(effects);
    }

    public void checkDeath(Entity e) {
        if (e.getHp() <= 0) {
            e.onDeath();
            ImageMatrixGUI.getInstance().removeImage(e);
            Engine.getInstance().getRoom().remove(e);
        }
    }
}
