package com.platformer.skills.effects;

import com.badlogic.gdx.Gdx;
import com.platformer.entities.Actor;

public class DamageEffect extends Effect {

    private float damageAmount;

    public DamageEffect(Actor actor, float amount) {
        super(actor);
        name = "Damage";
        isInstant = true;
        damageAmount = amount;
    }

    @Override
    public void run() {
        damageAmount -= (damageAmount / 100) * actor.getStats().defense; // actors resists (defence - actors % of clear damage resistance)
        actor.getStats().health -= damageAmount; // hit!
        Gdx.app.log("DAMAGE", "players health: " + actor.getStats().health);
    }
}
