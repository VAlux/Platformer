package com.platformer.abilities;

import com.badlogic.gdx.utils.Array;
import com.platformer.abilities.effect.Effect;
import com.platformer.entities.Actor;
import com.platformer.fx.FX;

public class Ability extends Actor {

    protected float cooldownTime;
    protected Array<Effect> effects;
    protected Array<FX> visualEffects;
    protected boolean isAvailable;

    protected Ability() {
        cooldownTime = 0;
        effects = new Array<Effect>();
        visualEffects = new Array<FX>();
        isAvailable = true;
    }

    public void updateCooldown(final float delta) {
        if (!isAvailable)
            cooldownTime -= delta;

        if (cooldownTime <= 0) {
            cooldownTime = 1.0f;
            isAvailable = true;
        }
    }

    @Override
    public void act(float delta) {
        updateCooldown(delta);
    }
}
