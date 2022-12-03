package pt.iscte.poo.effects;

import pt.iscte.poo.entity.Entity;

public class PoisonEffect extends StatusEffect {

    public PoisonEffect() {
        super(0x7FFFFFFF); // Infinito de 64 bits
    }

    @Override
    public void applyEffect(Entity e) {
        e.setHp(e.getHp() - 1);
        e.checkDeath(e);
        decreaseRemainingTurns();
    }
}
