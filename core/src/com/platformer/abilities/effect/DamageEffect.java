package com.platformer.abilities.effect;

import com.platformer.entities.Char;

public class DamageEffect extends Effect {

    private static final String TAG = DamageEffect.class.getSimpleName();

    private float damageAmount;

    public DamageEffect(float amount) {
        name = "Damage";
        isInstant = true;
        damageAmount = amount;
    }

    @Override
    public void apply(Char aChar) {
        damageAmount -= (damageAmount / 100) * aChar.getStats().defense; // characters resists (defense - characters % of clear damage resistance)
        aChar.getStats().health -= damageAmount; // hit!
    }

    @Override
    public void remove(Char aChar) {

    }
}
