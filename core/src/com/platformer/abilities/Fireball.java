package com.platformer.abilities;

import com.badlogic.gdx.math.Vector2;
import com.platformer.abilities.effect.DamageEffect;
import com.platformer.entities.Actor;
import com.platformer.entities.Char;
import com.platformer.entities.projectiles.Projectile;
import com.platformer.screens.GameScreen;

/**
 * Created by alexander on 01.05.15.
 */
public class Fireball extends Ability {

    private Char sourceChar;
    private DamageEffect damageEffect;

    public Fireball(Actor source) {
        super(source);
        sourceChar = (Char) source;
        cooldownTime = 0.1f;
        damageEffect = new DamageEffect(10.0f);
    }

    @Override
    public void activate() {
        if (isAvailable) {
            final Projectile fireball = new Projectile(sourceChar.getPosition());
            switch (sourceChar.getOrientation()) {
                case LEFT:
                    fireball.setVelocity(new Vector2(0.0f, 300.0f));
                    fireball.setAcceleration(new Vector2(-250.0f, 500.0f));
                    break;
                case RIGHT:
                    fireball.setVelocity(new Vector2(0.0f, 300.0f));
                    fireball.setAcceleration(new Vector2(250.0f, 500.0f));
                    break;
            }
            GameScreen.world.addRenderableActor(fireball);
            isAvailable = false;
        }
    }
}
