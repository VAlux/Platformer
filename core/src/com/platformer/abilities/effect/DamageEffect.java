package com.platformer.abilities.effect;

import com.platformer.entities.Char;

public class DamageEffect extends Effect {

    private float damageAmount;

    public DamageEffect(float amount) {
        name = "Damage";
        isInstant = true;
        damageAmount = amount;
    }

    @Override
    public void apply(Char aChar) {
        final float pureDamage = damageAmount - ((damageAmount / 100.0f) * aChar.getStats().defense); // characters resists (defense - characters % of clear damage resistance)
        aChar.getStats().health -= pureDamage; // hit!
    }

    @Override
    public void remove(Char aChar) {
        // we can't remove the basic damage effect from the target.
    }
}