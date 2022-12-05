package pt.iscte.poo.interfaces;

import pt.iscte.poo.entity.Entity;

public interface Attackable {
    int getMaxHp();

    void setMaxHp(int maxHp);

    int getHp();

    void setHp(int hp);

    int getBaseAtk();

    void setBaseAtk(int baseAtk);

    int getAtk();

    void setAtk(int atk);

    int getDef();

    void setDef(int def);

    void attack(Entity e);

    void onDeath();

    void applyAllEffects();
}
