package com.platformer.abilities;

import com.platformer.abilities.effect.DamageEffect;
import com.platformer.fx.FXExplosion;

public class FistOfFire extends Ability {

    private DamageEffect damageEffect;
    private FXExplosion explosionFX;

    public FistOfFire(final float X, final float Y) {
        super();

        cooldownTime = 1.0f;
        damageEffect = new DamageEffect(10.0f);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }
}
