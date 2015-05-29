package com.platformer.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.platformer.entities.Char;
import com.platformer.items.ItemsPool;

import static com.platformer.states.CharacterOrientation.LEFT;
import static com.platformer.states.CharacterOrientation.RIGHT;
import static com.platformer.states.CharacterState.*;

/**
 * Created by alvo on 28.12.14.
 */

/**
 * handles the input for the specified character.
 */
public class CharacterInputHandler extends InputAdapter implements InputHandler {

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
            c.jump();
        } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            c.getAcceleration().x = -c.getAccelerationFactor();
            if (c.getState() != JUMP) {
                c.setState(WALK_LEFT);
                c.setOrientation(LEFT);
            }
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            c.getAcceleration().x = c.getAccelerationFactor();
            if (c.getState() != JUMP) {
                c.setState(WALK_RIGHT);
                c.setOrientation(RIGHT);
            }
        } else {
            if (c.getState() != JUMP)
                c.switchToIdleState();
            c.getAcceleration().x = 0;
        }
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
            c.getAbilities().get(0).activate(); ///TODO: just testing.
            return true;
        }
        return false;
    }
}