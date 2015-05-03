package com.platformer.input;

import com.badlogic.gdx.*;
import com.platformer.entities.Char;
import com.platformer.items.ItemsPool;

import static com.platformer.states.CharacterState.*;

/**
 * Created by alvo on 28.12.14.
 */

/**
 * handles the input for the specified character.
 */
public class CharacterInputHandler implements InputHandler {

    /**
     * target to control by input handling.
     */
    private Char c;

    /**
     * Constructs the input handler for the specified character.
     * @param aChar character to control.
     */
    public CharacterInputHandler(Char aChar) {
        this.c = aChar;
    }

    /**
     * called once per frame. controls the current character.
     */
    @Override
    public void handle() {
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            if (c.isOnGround()) {
                c.getVelocity().y = c.getStats().jumpVelocity;
                c.setOnGround(false);
                Gdx.app.log("input", "REGULAR JUMP");
            }
            if (c.isOnWall() && c.isCanWallJump()) {
                c.getVelocity().y = c.getStats().jumpVelocity;
                c.setCanWallJump(false);
                c.setOnWall(false);
                Gdx.app.log("input", "WALL JUMP");
            }
            c.setState(JUMP);
        } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            c.getAcceleration().x = -c.getAccelerationFactor();
            if (c.getState() != JUMP)
                c.setState(WALK_LEFT);
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            c.getAcceleration().x = c.getAccelerationFactor();
            if (c.getState() != JUMP)
                c.setState(WALK_RIGHT);
        } else {
            if (c.getState() != JUMP)
                c.setState(IDLE);
            c.getAcceleration().x = 0;
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

    /**
     * invoked when the key is typed.
     * Needed for the ui actions
     * @param character typed character
     * @return event propagation status.
     */
    @Override
    public boolean keyTyped(char character) {
        if (character == 'e') {
            c.useItem(ItemsPool.IDs.HEAL_POTION);
            return true;
        }
        else if (character == 'i') {
            if (c.isActiveInventory()) c.deactivateInventory();
            else c.activateInventory();
            return true;
        }
        else if (character == 'q'){
            c.getAbilities().get(1).activate(); ///TODO: just testing.
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