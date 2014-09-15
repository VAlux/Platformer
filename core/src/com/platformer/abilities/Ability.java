package com.platformer.abilities;

import com.platformer.entities.Actor;

public class Ability extends Actor {

    protected float cooldownTime;
    protected boolean isAvailable;

    protected Ability() {
        cooldownTime = 0;
        isAvailable = true;
    }

    public void updateCooldown(final float delta) {
        cooldownTime -= delta;

        if (cooldownTime <= 0) {
            cooldownTime = 1.0f;
            isAvailable = true;
        }
    }

    @Override
    public void act(float delta) {
        if (!isAvailable)
            updateCooldown(delta);
    }
}
