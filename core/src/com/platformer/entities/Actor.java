package com.platformer.entities;

public abstract class Actor {

    protected long id;
    protected float stateTime;

    public abstract void act(final float delta);

    public abstract void destroy();

    public Actor() {
        id = (int) (Math.random() * 1000L); /// TODO: replace with hash later
        stateTime = 0.0f;
    }

    public long getId() {
        return id;
    }

    public float getStateTime() {
        return stateTime;
    }
}