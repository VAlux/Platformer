package com.platformer.abilities.effect;

import com.badlogic.gdx.Gdx;
import com.platformer.entities.Actor;

public class FreezeEffect extends Effect{
    private static final String TAG = FreezeEffect.class.getSimpleName();

    private float freezeEffect;
    private float velocityMult = 10;

    public FreezeEffect(float amount) {
        name = "Freeze";
        isInstant = true;
        freezeEffect = amount;
    }

    @Override
    public void apply(Actor actor) {
        actor.getStats().MAX_VELOCITY -= freezeEffect * velocityMult;
        actor.getStats().ACCELERATION -= freezeEffect;
        Gdx.app.log(TAG, "Players velocity: " + actor.getStats().MAX_VELOCITY);
        Gdx.app.log(TAG, "Players acceleration: " + actor.getStats().ACCELERATION);
    }

    @Override
    public void remove(Actor actor) {
        actor.getStats().MAX_VELOCITY += freezeEffect * velocityMult;
        actor.getStats().ACCELERATION += freezeEffect;
        Gdx.app.log(TAG, "Players velocity: " + actor.getStats().MAX_VELOCITY);
        Gdx.app.log(TAG, "Players acceleration: " + actor.getStats().ACCELERATION);
    }
}
