package com.platformer.items;

import com.platformer.entities.Character;

public interface ApplicableObject {
    /** Applies effects to given actor. */
    public void apply(Character actor);

    /** Removes effects from given actor. */
    public void remove(Character actor);
}
