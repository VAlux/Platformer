package com.platformer.entities;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.platformer.enums.ActorState;
import com.platformer.maps.Map;

import java.util.ArrayList;

import static com.platformer.enums.ActorState.DEAD;

public class Mob extends Character {

    protected Actor attackTarget;
    protected Rectangle fieldOfView;
    protected ArrayList<Actor> actors;
    protected Vector2 fovSize;

    public Mob(Map map, Vector2 spawnPosition, ArrayList<Actor> actors, int inventoryCapacity) {
        super(map, spawnPosition, inventoryCapacity);
        specialObjects = map.getSpecObjectsLayer().getObjects();
        collidableObjects = map.getCollisionLayer().getObjects();

        this.actors = actors;
        fovSize = new Vector2(10 * map.getTileWidth(), 10 * map.getTileHeight());
        fieldOfView = new Rectangle(position.x - fovSize.x / 2, position.y - fovSize.y / 2, fovSize.x, fovSize.y);
        state = ActorState.IDLE;
    }

    public Mob(Map map, Vector2 spawnPosition, ArrayList<Actor> actors){
        this(map,spawnPosition, actors, 0);
    }

    @Override
    public void act(float deltaTime) {
        if (isAlive()) {
            super.act(deltaTime);
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

    public void setAttackTarget(Actor attackTarget) {
        this.attackTarget = attackTarget;
    }

    public Rectangle getFOV() {
        return fieldOfView;
    }
}
