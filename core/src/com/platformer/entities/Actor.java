package com.platformer.entities;

import com.platformer.maps.Map;

public abstract class Actor {

    protected long id;
    protected float stateTime;
    protected Map map;

    public abstract void act(final float delta);

    public Actor() {
        id = (int) (Math.random() * 1000L); /// TODO: replace with hash later
        stateTime = 0;
    }

    protected Actor(final Map map) {
        this();
        this.map = map;
    }

    public long getId() {
        return id;
    }

    public float getStateTime() {
        return stateTime;
    }

    public void dispose(){
        map.dispose();
    }
}