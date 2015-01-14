package com.platformer.abilities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Vector2;
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
        position = new Vector2();
        cooldownTime = 1.0f;
        damageEffect = new DamageEffect(10.0f);
        explosionFX = new FXExplosion(sourceChar.getPosition().x, sourceChar.getPosition().y);
    }

    @Override
    public void activate() {
        stateTime = 0;
        position.set(sourceChar.getPosition());
        explosionFX.getPosition().set(position);
        explosionFX.setRunning(true);
        isAvailable = false;
        FXRenderer.addEffect(explosionFX);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        explosionFX.act(delta);
    }

    @Override
    public Animation getAnimation() {
        return explosionFX.getAnimation();
    }
}
