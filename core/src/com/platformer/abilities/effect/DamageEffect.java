package com.platformer.abilities.effect;

import com.platformer.entities.Character;

public class DamageEffect extends Effect {

    private static final String TAG = DamageEffect.class.getSimpleName();

    private float damageAmount;

    public DamageEffect(float amount) {
        name = "Damage";
        isInstant = true;
        damageAmount = amount;
    }

    @Override
    public void apply(Character character) {
        damageAmount -= (damageAmount / 100) * character.getStats().defense; // characters resists (defense - characters % of clear damage resistance)
        character.getStats().health -= damageAmount; // hit!
    }

    @Override
    public void remove(Character character) {

    }
}
