package com.platformer.skills.abilities;

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
        damageAmount -= (damageAmount / 100) * target.getStats().defense; // actors resists (defence - actors % of clear damage resistance)
        target.getStats().health -= damageAmount; // hit!
        Gdx.app.log("DAMAGE", "players health: " + target.getStats().health);
    }
}
