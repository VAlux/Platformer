package com.platformer.abilities;

import com.platformer.abilities.effect.DamageEffect;

public class FistOfFire extends Ability {

    public FistOfFire() {
        super();
        cooldownTime = 1.0f;
        effects.add(new DamageEffect(10.0f));
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }
}
