package com.platformer.input;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alvo on 28.12.14.
 */

/**
 * Handles the inputs queue.
 */
public class InputProcessor {
    /**
     * a queue of multiple input handlers,
     * which will be processed one by one.
     */
    private List<InputHandler> inputQueue;

    /**
     * You'd newer guess, but it is a constructor.
     */
    public InputProcessor() {
        inputQueue = new ArrayList<InputHandler>();
    }

    /**
     * adds a new input handler to the input processing queue.
     * @param handler handler to add.
     */
    public final void add(final InputHandler handler) {
        inputQueue.add(handler);
    }

    /**
     * removes input handler from the queue.
     * @param handler handler to remove
     */
    public final void remove(final InputHandler handler) {
        inputQueue.remove(handler);
    }

    /**
     * iterate through the inputs queue and process them.
     */
    public final void processInput() {
        for (InputHandler inputHandler : inputQueue) {
            inputHandler.handle();
        }
    }
}