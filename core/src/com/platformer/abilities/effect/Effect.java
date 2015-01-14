package com.platformer.abilities.effect;

import com.platformer.entities.Char;
import com.platformer.items.ApplicableObject;

public abstract class Effect implements ApplicableObject {

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

    @Override
    public abstract void apply(Char actor);

    @Override
    public abstract void remove(Char actor);
}
