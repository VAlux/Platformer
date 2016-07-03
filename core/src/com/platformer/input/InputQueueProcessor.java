package com.platformer.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alvo on 28.12.14.
 */

/**
 * Handles the inputs queue.
 */
public class InputQueueProcessor {
    /**
     * a queue of multiple input handlers,
     * which will be processed one by one.
     */
    private List<InputHandler> inputQueue;

    /**
     * Input multiplexer, which handles multiple inputs from game stages (HUD, etc.)
     */
    private InputMultiplexer inputMultiplexer;

    /**
     * You'd newer guess, but it is a constructor.
     */
    public InputQueueProcessor() {
        inputQueue = new ArrayList<InputHandler>();
        inputMultiplexer = new InputMultiplexer();
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    /**
     * adds a new input handler to the input processing queue.
     * @param handler handler to add.
     */
    public final void addInputHandler(final InputHandler handler){
        inputQueue.add((InputHandler) handler);
    }

    /**
     * Adds a new input processor to the input multiplexing queue.
     * @param processor processor to add.
     */
    public final void addInputProcessor(final InputProcessor processor) {
        inputMultiplexer.addProcessor(processor);
    }

    /**
     * removes input handler from the queue.
     * @param handler handler to remove
     */
    public final void remove(final InputHandler handler) {
        inputQueue.remove(handler);
    }

    /**
     * indicates, is the input queue is empty or not.
     * @return input queue emptiness.
     */
    public final boolean isEmpty() {
        return inputQueue.isEmpty();
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