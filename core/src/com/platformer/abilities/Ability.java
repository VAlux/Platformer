package com.platformer.abilities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.platformer.entities.Actor;
import com.platformer.entities.RenderableEntity;

public abstract  class Ability extends RenderableEntity {

    protected float cooldownTime;
    protected boolean isAvailable;
    protected Actor source;

    public abstract void activate();

    protected Ability(final Actor source) {
        super();
        cooldownTime = 0;
        isAvailable = true;
        this.source = source;
    }

    public void updateCooldown(final float delta) {
        cooldownTime -= delta;

        if (cooldownTime <= 0) {
            cooldownTime = 1.0f;
            isAvailable = true;
        }
    }

    @Override
    public void act(float delta) {
        stateTime += delta;
        if (!isAvailable)
            updateCooldown(delta);
    }

    @Override
    public Animation getAnimation() {
        return null;
    }
}
