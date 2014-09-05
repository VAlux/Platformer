package com.platformer.items;

import com.platformer.entities.Actor;

public interface ApplicableObject {
    /** Applies effects to given actor. */
    public void apply(Actor actor);

    /** Removes effects from given actor. */
    public void remove(Actor actor);
}
