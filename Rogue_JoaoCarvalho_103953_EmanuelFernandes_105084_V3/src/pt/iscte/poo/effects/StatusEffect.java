package pt.iscte.poo.effects;

import pt.iscte.poo.entity.Entity;

public abstract class StatusEffect {
    private int remainingTurns;

    public StatusEffect(int remainingTurns) {
        this.remainingTurns = remainingTurns;
    }

    public void decreaseRemainingTurns() {
        remainingTurns--;
    }

    public int getRemainingTurns() {
        return remainingTurns;
    }

    public abstract void applyEffect(Entity e);
}
