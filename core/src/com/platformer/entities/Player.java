package com.platformer.entities;

import com.platformer.maps.Map;

import static com.platformer.enums.CharacterState.DEAD;
import static com.platformer.enums.CharacterState.IDLE;

public class Player extends Char {

    public Player(Map map, int inventoryCapacity) {
        super(map, map.getSpawnPoint(), inventoryCapacity);
        state = IDLE;
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