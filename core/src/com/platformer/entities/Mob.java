package com.platformer.entities;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.platformer.enums.ActorState;
import com.platformer.maps.Map;

import java.util.ArrayList;

public class Mob extends Actor {

    protected Actor attackTarget;
    protected Rectangle fieldOfView;
    protected ArrayList<Actor> actors;
    protected Vector2 fovSize;

    public Mob(Map map, Vector2 spawnPosition, ArrayList<Actor> actors) {
        super(map, spawnPosition);
        specialObjects = map.getSpecObjectsLayer().getObjects();
        collidableObjects = map.getCollisionLayer().getObjects();

        this.actors = actors;
        fovSize = new Vector2(10 * map.getTileWidth(), 10 * map.getTileHeight());
        fieldOfView = new Rectangle(position.x - fovSize.x / 2, position.y - fovSize.y / 2, fovSize.x, fovSize.y);
        state = ActorState.IDLE;
    }

    @Override
    public void act(float deltaTime) {
        super.act(deltaTime);
        fieldOfView.setPosition(position.x - fovSize.x / 2, position.y - fovSize.y / 2);
    }

    @Override
    public void move(float deltaTime) {
        super.move(deltaTime);
    }

    @Override
    public void spawn() {
        super.spawn();
    }

    public void setAttackTarget(Actor attackTarget) {
        this.attackTarget = attackTarget;
    }

    public Rectangle getFOV() {
        return fieldOfView;
    }
}
