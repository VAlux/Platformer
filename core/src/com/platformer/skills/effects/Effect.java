package com.platformer.skills.effects;

import com.platformer.entities.Actor;

public abstract class Effect {

    protected String name;
    protected Actor actor;
    protected boolean isInstant;

    protected Effect(Actor actor) {
        this.actor = actor;
    }

    public boolean isInstant() {
        return isInstant;
    }

    public String getName() {
        return name;
    }

    public abstract void run(); // effect mechanism here.
}
