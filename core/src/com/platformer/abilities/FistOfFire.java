package com.platformer.abilities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Vector2;
import com.platformer.abilities.effect.DamageEffect;
import com.platformer.entities.Actor;
import com.platformer.entities.Character;
import com.platformer.fx.FXExplosion;
import com.platformer.fx.FXRenderer;

public class FistOfFire extends Ability {

    private DamageEffect damageEffect;
    private FXExplosion explosionFX;
    private Character sourceCharacter;

    public FistOfFire(final Actor source) {
        super(source);
        sourceCharacter = (Character) source;
        position = new Vector2();
        cooldownTime = 1.0f;
        damageEffect = new DamageEffect(10.0f);
        explosionFX = new FXExplosion(sourceCharacter.getPosition().x, sourceCharacter.getPosition().y);
    }

    @Override
    public void activate() {
        stateTime = 0;
        position.set(sourceCharacter.getPosition());
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
