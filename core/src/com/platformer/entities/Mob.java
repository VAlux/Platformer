package com.platformer.entities;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.platformer.enums.CharacterState;
import com.platformer.maps.Map;

import java.util.ArrayList;

import static com.platformer.enums.CharacterState.DEAD;

public class Mob extends Character {

    protected Character attackTarget;
    protected Rectangle fieldOfView;
    protected ArrayList<Character> characters;
    protected Vector2 fovSize;

    public Mob(Map map, Vector2 spawnPosition, ArrayList<Character> characters, int inventoryCapacity) {
        super(map, spawnPosition, inventoryCapacity);
        specialObjects = map.getSpecObjectsLayer().getObjects();
        collidableObjects = map.getCollisionLayer().getObjects();

        this.characters = characters;
        fovSize = new Vector2(10 * map.getTileWidth(), 10 * map.getTileHeight());
        fieldOfView = new Rectangle(position.x - fovSize.x / 2, position.y - fovSize.y / 2, fovSize.x, fovSize.y);
        state = CharacterState.IDLE;
    }

    public Mob(Map map, Vector2 spawnPosition, ArrayList<Character> characters){
        this(map,spawnPosition, characters, 0);
    }

    @Override
    public void act(float delta) {
        if (isAlive()) {
            super.act(delta);
            fieldOfView.setPosition(position.x - fovSize.x / 2, position.y - fovSize.y / 2);
        } else {
            state = DEAD;
            spawn();
        }
    }

    @Override
    public void move(float deltaTime) {
        super.move(deltaTime);
    }

    public void setAttackTarget(Character attackTarget) {
        this.attackTarget = attackTarget;
    }

    public Rectangle getFOV() {
        return fieldOfView;
    }
}
