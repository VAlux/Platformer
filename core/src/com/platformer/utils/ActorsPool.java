package com.platformer.utils;

import com.platformer.entities.Actor;

import java.util.Stack;

/**
 * Created by alexander on 23.11.15.
 * Some basic pooling functionality for in-game actors.
 */
public class ActorsPool<ActorType extends Actor> {

    protected Stack<ActorType> pool;

    public ActorsPool() {
        pool = new Stack<>();
    }

    /**
     * Retrieve the actor from the pool.
     * @return Actor object if it is possible to get it form the Pool. null - otherwise.
     */
    public Actor getActor() {
        return pool.pop();
    }

    public void putActor(ActorType actor) {
        pool.push(actor);
    }
}
