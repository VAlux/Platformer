package com.platformer.abilities;

import com.platformer.entities.Actor;
import com.platformer.entities.Entity;

public abstract  class Ability extends Entity {

    protected Actor source;
    protected float currentCooldown;
    protected float cooldownTime;
    protected boolean isAvailable;

    public abstract void activate();

    protected Ability(final Actor source) {
        super();
        cooldownTime = currentCooldown = 1.0f; // default cooldown.
        isAvailable = true;
        this.source = source;
    }

    /**
     * updates ability's cooldown and checks, is the ability ready to be refreshed.
     * @param delta time passed between frames in ms.
     */
    public void updateCooldown(final float delta) {
        currentCooldown -= delta;
        if (currentCooldown <= 0.0f) {
            refresh();
        }
    }

    /**
     * Prepares the ability to the next use.
     */
    public void refresh() {
        this.isAvailable = true;
        this.currentCooldown = cooldownTime;
    }

    public void setCooldownTime(float cooldownTime) {
        this.cooldownTime = cooldownTime;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (!isAvailable)
            updateCooldown(delta);
    }
}
