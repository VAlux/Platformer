package com.platformer.abilities.effect;

import com.badlogic.gdx.Gdx;
import com.platformer.entities.Actor;

public class DamageEffect extends Effect {
    private static final String TAG = DamageEffect.class.getSimpleName();

    private float damageAmount;

    public DamageEffect(float amount) {
        name = "Damage";
        isInstant = true;
        damageAmount = amount;
    }

    @Override
    public void apply(Actor actor) {
        damageAmount -= (damageAmount / 100) * actor.getStats().defense; // actors resists (defense - actors % of clear damage resistance)
        actor.getStats().health -= damageAmount; // hit!
        Gdx.app.log(TAG, "Players health: " + actor.getStats().health);
    }

    @Override
    public void remove(Actor actor) {

    }
}
