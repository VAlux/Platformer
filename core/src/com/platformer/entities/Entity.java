package com.platformer.entities;

/**
 * Created by alexander on 03.05.15.
 */
public class Entity extends Actor {

    public Entity() {
        super();
    }

    @Override
    public void act(float delta) {
        stateTime += delta;
    }

    @Override
    public void destroy() {
        //Nothing to destroy. =)
    }
}
