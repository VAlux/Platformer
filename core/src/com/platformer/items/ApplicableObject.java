package com.platformer.items;

import com.platformer.entities.Char;

public interface ApplicableObject {
    /** Applies effects to given actor. */
    public void apply(Char actor);

    /** Removes effects from given actor. */
    public void remove(Char actor);
}
