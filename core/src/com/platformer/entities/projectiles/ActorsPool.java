package com.platformer.entities.projectiles;

import com.badlogic.gdx.utils.Array;
import com.platformer.entities.Actor;

import java.util.HashMap;

/**
 * Created by alexander on 23.11.15.
 */
public class ActorsPool {

    private HashMap<Class<?>, Array<Actor>> pool;

    public ActorsPool() {
        pool = new HashMap<>();
    }

    /**
     * Add actor to the actors pool.
     * Determine, if the actors category already exists and if yes -> append actor to it,
     * otherwise create new category for this type of actors and add it to it.
     * @param actor actor to add.
     */
    public void addActor(Actor actor) {
        final Array<Actor> category = pool.get(actor.getClass());
        if (category == null) {
            insertCategory(actor);
        } else {
            category.add(actor);
        }
    }

    private void insertCategory(Actor actor) {
        Array<Actor> category = new Array<>();
        category.add(actor);
        pool.put(actor.getClass(), category);
    }
}
