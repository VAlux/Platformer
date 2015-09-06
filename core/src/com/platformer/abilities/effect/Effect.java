package com.platformer.abilities.effect;

import com.platformer.entities.Char;

public abstract class Effect {

    protected String name;
    protected String description;

    protected boolean isInstant;

    protected Effect() {
        this("UNDEFINED");
    }

    protected Effect(String name) {
        this(name, false);
    }

    protected Effect(String name, boolean isInstant) {
        this.name = name;
        this.isInstant = isInstant;
    }

    public boolean isInstant() {
        return isInstant;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public abstract void apply(Char actor);

    public abstract void remove(Char actor);
}
