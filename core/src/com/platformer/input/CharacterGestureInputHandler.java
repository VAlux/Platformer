package com.platformer.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.input.GestureDetector;
import com.platformer.Constants;
import com.platformer.entities.Char;

import static com.platformer.states.CharacterState.*;

/**
 * Created by alexander on 04.05.15.
 * Handling the user input gestures.
 */
public class CharacterGestureInputHandler extends GestureDetector.GestureAdapter implements InputHandler {

    private Char character;

    public CharacterGestureInputHandler(Char character) {
        this.character = character;
    }

    @Override
    public void handle() {
        if (Gdx.input.isTouched()) {
            if (Gdx.input.getX() < Constants.PROP_GAME_WIDTH / 2){
                character.getVelocity().x = -character.getMaxVelocity();
                if (character.getState() != JUMP)
                    character.setState(WALK_LEFT);
            } else {
                character.getVelocity().x = character.getMaxVelocity();
                if (character.getState() != JUMP)
                    character.setState(WALK_RIGHT);
            }
        }
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        character.jump();
        return true;
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        if (x < Constants.PROP_GAME_WIDTH / 2) {
            character.getVelocity().x = -character.getMaxVelocity();
            if (character.getState() != JUMP)
                character.setState(WALK_LEFT);
        } else {
            character.getVelocity().x = character.getMaxVelocity();
            if (character.getState() != JUMP)
                character.setState(WALK_RIGHT);
        }
        return true;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        if (velocityX < 0) {
            character.getVelocity().x = -character.getMaxVelocity();
            if (character.getState() != JUMP)
                character.setState(WALK_LEFT);
        } else {
            character.getVelocity().x = character.getMaxVelocity();
            if (character.getState() != JUMP)
                character.setState(WALK_RIGHT);
        }
        return true;
    }
}