package com.platformer.fx;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.platformer.entities.RenderableEntity;

public abstract class FX extends RenderableEntity {

    protected int width;
    protected int height;
    protected boolean isRunning;
    protected Animation animation;

    protected FX(float X, float Y) {
        super(X, Y);
        isRunning = false;
    }

    protected abstract void createAnimations();

    public void act(final float delta) {
        stateTime += delta;
    }

    public Animation getAnimation() {
        return animation;
    }

    public void setRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }
}
