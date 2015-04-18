package com.platformer.entities;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.platformer.enums.CharacterState;
import com.platformer.maps.Map;

import java.util.ArrayList;

import static com.platformer.enums.CharacterState.DEAD;

public class Mob extends Char {

    private static final int FOV_SCALE = 16;

    protected Char attackTarget;
    protected Rectangle fieldOfView;
    protected ArrayList<Char> aChars;
    protected Vector2 fovSize;

    public Mob(Map map, Vector2 spawnPosition, ArrayList<Char> aChars, int inventoryCapacity) {
        super(map, spawnPosition, inventoryCapacity);
        specialObjects = map.getSpecObjectsLayer().getObjects();
        collidableObjects = map.getCollisionLayer().getObjects();

        this.aChars = aChars;
        fovSize = new Vector2(FOV_SCALE * map.getTileWidth(), FOV_SCALE * map.getTileHeight());
        fieldOfView = new Rectangle(position.x - fovSize.x / 2, position.y - fovSize.y / 2, fovSize.x, fovSize.y);
        state = CharacterState.IDLE;
    }

    public Mob(Map map, Vector2 spawnPosition, ArrayList<Char> aChars){
        this(map,spawnPosition, aChars, 0);
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

    public void setAttackTarget(Char attackTarget) {
        this.attackTarget = attackTarget;
    }

    public Rectangle getFOV() {
        return fieldOfView;
    }
}
