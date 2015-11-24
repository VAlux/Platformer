package com.platformer.entities.projectiles;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.platformer.entities.Char;

import static com.platformer.states.ProjectileState.EXPLODED;
import static com.platformer.states.ProjectileState.EXPLODING;

/**
 * Created by alexander on 05.09.15.
 */
public class FireballProjectile extends Projectile {

    private static final int TILE_SIZE = 64;
    protected Animation flyingAnimation;
    protected Animation explodeAnimation;

    public FireballProjectile(Char source) {
        super(source, TILE_SIZE, TILE_SIZE);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if ((hasCollision() || ttlExpired()) && state != EXPLODING) {
            stateTime = 0.0f;
            state = EXPLODING;
            setDynamic(false); // stop moving while exploding.
            sourceAbility.projectileHit(getCollisionTarget());
        }

        if (explodeAnimation.isAnimationFinished(stateTime)) {
            state = EXPLODED;
            destroy();
        }
    }

    @Override
    public Animation getAnimation() {
        switch (state) {
            case FLYING:
                return flyingAnimation;
            case EXPLODING:
                return explodeAnimation;
            default:
                return flyingAnimation;
        }
    }

    public void setFlyingAnimation(Animation flyingAnimation) {
        this.flyingAnimation = flyingAnimation;
    }

    public void setExplodeAnimation(Animation explodeAnimation) {
        this.explodeAnimation = explodeAnimation;
    }
}