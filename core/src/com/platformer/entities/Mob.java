package com.platformer.entities;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.platformer.screens.GameScreen;
import com.platformer.states.CharacterState;

import java.util.ArrayList;

import static com.platformer.states.CharacterState.DEAD;

public class Mob extends Char {

    private static final int FOV_SCALE = 16;

    protected Char attackTarget;
    protected Rectangle fieldOfView;
    protected ArrayList<Char> aChars;
    protected Vector2 fovSize;

    public Mob(Vector2 spawnPosition){
        super(spawnPosition, 0);
        specialObjects = map.getSpecObjectsLayer().getObjects();
        collidableObjects = map.getCollisionLayer().getObjects();

        this.aChars = GameScreen.world.getChars();
        fovSize = new Vector2(FOV_SCALE * map.getTileWidth(), FOV_SCALE * map.getTileHeight());
        fieldOfView = new Rectangle(position.x - fovSize.x / 2, position.y - fovSize.y / 2, fovSize.x, fovSize.y);
        switchToIdleState();
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

    protected void walk(CharacterState direction) {
        switch (direction) {
            case WALK_LEFT:
                this.acceleration.x = -accelerationFactor;
                state = CharacterState.WALK_LEFT;
                break;
            case WALK_RIGHT:
                this.acceleration.x = accelerationFactor;
                state = CharacterState.WALK_RIGHT;
                break;
        }
    }

    public void setAttackTarget(Char attackTarget) {
        this.attackTarget = attackTarget;
    }

    public Rectangle getFOV() {
        return fieldOfView;
    }
}
