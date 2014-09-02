package com.platformer.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.platformer.maps.Map;

import static com.platformer.enums.ActorState.*;

public class Player extends Actor {

    protected boolean isOnWall;
    protected boolean canWallJump;

    public Player(Map map) {
        super(map, map.getSpawnPoint());
        state = IDLE;
    }

    @Override
    public void act(final float deltaTime) {
        processKeys();
        checkAlive();
        acceleration.y = -stats.GRAVITY * deltaTime;
        velocity.add(acceleration);

        if (acceleration.x == 0)
            velocity.x *= stats.FRICTION;

        velocity.x = MathUtils.clamp(velocity.x, -stats.MAX_VELOCITY, stats.MAX_VELOCITY);
        move(deltaTime);
        stateTime += deltaTime;
    }

    @Override
    public void spawn() {
        this.bounds.setPosition(spawnPosition);
        velocity.set(0, 0);
        acceleration.set(0, 0);
        state = SPAWN;
    }

    @Override
    public void move(final float deltaTime) {
        bounds.x += velocity.x * deltaTime;
        Rectangle collRect;
        for (RectangleMapObject object : collidableObjects.getByType(RectangleMapObject.class)) {
            collRect = object.getRectangle();
            if (bounds.overlaps(collRect)) {
                if (velocity.x > 0)
                    bounds.x = collRect.x - bounds.width - 0.01f;
                else
                    bounds.x = collRect.x + collRect.width + 0.01f;

                velocity.x = 0;
                isOnWall = !isOnGround;
            }
        }
        bounds.y += velocity.y * deltaTime;
        for (RectangleMapObject object : collidableObjects.getByType(RectangleMapObject.class)) {
            collRect = object.getRectangle();
            if (bounds.overlaps(collRect)) {
                if (velocity.y > 0) {
                    bounds.y = collRect.y - bounds.height - 0.01f;
                }
                else {
                    bounds.y = collRect.y + collRect.height + 0.01f;
                    isOnGround = true;
                    canWallJump = true;
                    isOnWall = false;
                    if (state == JUMP)
                        state = IDLE;
                }
                velocity.y = 0;
            }
        }
        position.set(bounds.x, bounds.y);
    }

    protected boolean checkAlive(){
        if (!super.checkAlive()) {
            state = DEAD;
            spawn();
            return false;
        }
        return true;
    }

    private void processKeys(){
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            if (isOnGround) {
                velocity.y = stats.JUMP_VELOCITY;
                isOnGround = false;
            }
            if (isOnWall && canWallJump) {
                velocity.y = stats.JUMP_VELOCITY;
                canWallJump = false;
                isOnWall = false;
            }
            state = JUMP;
        } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            acceleration.x = -stats.ACCELERATION;
            if (state != JUMP)
                state = WALK_LEFT;
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            acceleration.x = stats.ACCELERATION;
            if (state != JUMP)
                state = WALK_RIGHT;
        }
        else {
            if (state != JUMP)
                state = IDLE;
            acceleration.x = 0;
        }
    }
}