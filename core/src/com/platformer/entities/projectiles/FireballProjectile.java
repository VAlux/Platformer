package com.platformer.entities.projectiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.platformer.utils.Tools;

import static com.platformer.states.ProjectileState.EXPLODED;
import static com.platformer.states.ProjectileState.EXPLODING;

/**
 * Created by alexander on 05.09.15.
 */
public class FireballProjectile extends Projectile {

    protected Animation flyingAnimation;
    protected Animation explodeAnimation;

    public FireballProjectile(Vector2 position) {
        super(position);
        createAnimations();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if ((hasCollision() || ttlExpired()) && state != EXPLODING) {
            stateTime = 0.0f;
            state = EXPLODING;
            setDynamic(false); // stop moving while exploding.
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

    protected void createAnimations() {
        /// TODO: It is needed to load texture once for every instance of the projectile!
        texture = new Texture("tilesets/fx/explosion_small.png");
        splittedTextureAtlas = new TextureRegion(texture).split(60, 60);
        flyingAnimation = new Animation(0.1f, Tools.extractAnimation(splittedTextureAtlas, 1, 3));
        explodeAnimation = new Animation(0.1f, Tools.extractAnimation(splittedTextureAtlas, 6, 10));
    }
}