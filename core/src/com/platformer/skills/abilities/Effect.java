package com.platformer.skills.abilities;

import com.platformer.entities.Actor;

public abstract class Effect {

    protected String name;
    protected Actor target;
    protected boolean isInstant;

    protected Effect(Actor target) {
        this.target = target;
    }

    public boolean isInstant() {
        return isInstant;
    }

    public String getName() {
        return name;
    }

    public abstract void run(); // effect mechanism here.
}
