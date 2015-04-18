package com.platformer.abilities;

import com.platformer.abilities.effect.DamageEffect;
import com.platformer.entities.Actor;
import com.platformer.entities.Char;
import com.platformer.fx.FXExplosion;
import com.platformer.fx.FXRenderer;

public class FistOfFire extends Ability {

    private DamageEffect damageEffect;
    private FXExplosion explosionFX;
    private Char sourceChar;

    public FistOfFire(final Actor source) {
        super(source);
        sourceChar = (Char) source;
        cooldownTime = 0.3f;
        damageEffect = new DamageEffect(10.0f);
        explosionFX = new FXExplosion(sourceChar.getPosition());
    }

    @Override
    public void activate() {
        if (isAvailable) {
            explosionFX = new FXExplosion(sourceChar.getPosition());
            explosionFX.setRunning(true);
            isAvailable = false;
            FXRenderer.addEffect(explosionFX);
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        explosionFX.act(delta);
    }
}
