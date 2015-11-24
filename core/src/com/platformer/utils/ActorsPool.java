package com.platformer.utils;

import com.platformer.entities.Actor;

import java.util.HashMap;
import java.util.Stack;

/**
 * Created by alexander on 23.11.15.
 * Some basic pooling functionality for in-game actors.
 */
public class ActorsPool {

    protected HashMap<Class<?>, Stack<Actor>> pool;

    public ActorsPool() {
        pool = new HashMap<>();
    }

    /**
     * Add actor to the actors pool.
     * Determine, if the actors category already exists and if yes -> append actor to it,
     * otherwise create new category for this type of actors and add it to it.
     * @param actor actor to add.
     */
    protected void addActor(Actor actor) {
        final Stack<Actor> category = getCategory(actor.getClass());
        if (category == null) {
            insertCategory(actor);
        } else {
            category.push(actor);
        }
    }

    /**
     * Retrieve the actor from the pool.
     * @param actorType type of the actor to get.
     * @return Actor object if it is possible to get it form the Pool. null - otherwise.
     */
    public Actor getActor(Class<?> actorType) {
        final Stack<Actor> category = getCategory(actorType);
        if (category != null && !category.empty()) {
            return getCategory(actorType).pop();
        }
        return null;
    }

    public void putActorBack(Actor actor) {
        addActor(actor);
    }

    protected Stack<Actor> getCategory(Class<?> actorType) {
        return pool.get(actorType);
    }

    protected void insertCategory(Actor actor) {
        Stack<Actor> category = new Stack<>();
        category.push(actor);
        pool.put(actor.getClass(), category);
    }
}
