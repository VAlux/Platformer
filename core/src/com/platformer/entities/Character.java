package com.platformer.entities;

import com.badlogic.gdx.math.Vector2;
import com.platformer.maps.Map;

public class Character extends Actor{

    protected Character(float X, float Y) {
        super(X, Y);
    }

    protected Character(Map map, Vector2 spawnPosition) {
        super(map, spawnPosition);
    }

    protected Character(Map map, float xPos, float yPos) {
        super(map, xPos, yPos);
    }
}
