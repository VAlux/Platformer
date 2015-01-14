package com.platformer.input;

/**
 * Created by alvo on 28.12.14.
 */

import com.badlogic.gdx.InputProcessor;

/**
 * represents the user input handling method abstraction.
 */
public interface InputHandler extends InputProcessor {
    /**
     * input handling mechanism.
     */
    void handle();
}
