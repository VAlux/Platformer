package com.platformer.abilities;

import com.platformer.entities.Actor;

public abstract  class Ability extends Actor {

    protected Actor source;
    protected float currentCooldown;
    protected float cooldownTime;
    protected boolean isAvailable;

    public abstract void activate();

    protected Ability(final Actor source) {
        super();
        cooldownTime = 1.0f;
        currentCooldown = 0;
        isAvailable = true;
        this.source = source;
    }

    public void updateCooldown(final float delta) {
        currentCooldown -= delta;
        if (currentCooldown <= 0) {
            currentCooldown = cooldownTime;
            isAvailable = true;
        }
    }

    @Override
    public void act(float delta) {
        stateTime += delta;
        if (!isAvailable)
            updateCooldown(delta);
    }
}
