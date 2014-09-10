package com.platformer.fx;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.platformer.entities.RenderableEntity;

public abstract class FX extends RenderableEntity {

    protected Animation animation;
    protected int width;
    protected int height;

    protected FX(float X, float Y) {
        super(X, Y);
    }

    protected abstract void createAnimations();

    public void act(final float delta) {
        stateTime += delta;
    }

    public Animation getCurrentAnimation() {
        return animation;
    }
}
