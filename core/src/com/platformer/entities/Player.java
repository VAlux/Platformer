package com.platformer.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.platformer.items.ItemsPool;
import com.platformer.maps.Map;

import static com.platformer.enums.ActorState.*;

public class Player extends Character implements InputProcessor {

    protected boolean isOnWall;
    protected boolean canWallJump;

    public Player(Map map, int inventoryCapacity) {
        super(map, map.getSpawnPoint(), inventoryCapacity);
        state = IDLE;
    }

    @Override
    public void act(final float deltaTime) {
        if (isAlive()) {
            processKeys();
            super.act(deltaTime);
        } else {
            state = DEAD;
            spawn();
        }
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
        } else {
            if (state != JUMP)
                state = IDLE;
            acceleration.x = 0;
        }
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        if (character == 'e') {
            useItem(ItemsPool.IDs.HEAL_POTION);
            return true;
        }
        else if (character == 'i') {
            if (isActiveInventory) deactivateInventory();
            else activateInventory();
            return true;
        }
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}