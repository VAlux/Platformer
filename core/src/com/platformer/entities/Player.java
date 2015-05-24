package com.platformer.entities;

import com.platformer.maps.Map;

import static com.platformer.states.CharacterState.DEAD;

public class Player extends Char {

    public Player(Map map, int inventoryCapacity) {
        super(map.getSpawnPoint(), inventoryCapacity);
        switchToIdleState();
    }

    @Override
    public void act(final float delta) {
        if (isAlive()) {
            super.act(delta);
        } else {
            state = DEAD;
            spawn();
        }
    }
}