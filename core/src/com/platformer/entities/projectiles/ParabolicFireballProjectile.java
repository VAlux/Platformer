package com.platformer.entities.projectiles;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Rectangle;
import com.platformer.entities.PhysicalEntity;
import com.platformer.screens.GameScreen;

import static com.platformer.states.ProjectileState.EXPLODED;
import static com.platformer.states.ProjectileState.EXPLODING;

/**
 * Created by alexander on 05.09.15.
 * Logic for the general fireball projectile.
 */
public class ParabolicFireballProjectile extends Projectile {

    protected Animation flyingAnimation;
    protected Animation explodeAnimation;

    public ParabolicFireballProjectile(PhysicalEntity source) {
        super(source, PROJECTILE_SIZE, PROJECTILE_SIZE);
        setVisualPositionOffset(-(PROJECTILE_SIZE + bounds.width / PROJECTILE_SCALE_FACTOR),
                                -(PROJECTILE_SIZE + bounds.height / PROJECTILE_SCALE_FACTOR));
    }

    @Override
    protected void handleVerticalCollisions() {
        for (Rectangle collRect: GameScreen.world.getCollisionObjects()) {
            if (collRect != source.getBounds() && bounds != collRect && bounds.overlaps(collRect)) {
                hasYCollision = true;
                velocity.y = 0;
            }
        }
    }

    @Override
    protected void handleHorizontalCollisions() {
        for (Rectangle collRect : GameScreen.world.getCollisionObjects()) {
            if (collRect != source.getBounds() && bounds != collRect && bounds.overlaps(collRect)) {
                hasXCollision = true;
                velocity.x = 0;
            }
        }
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