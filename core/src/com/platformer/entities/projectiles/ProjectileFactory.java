package com.platformer.entities.projectiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by alvo on 15.09.15.
 */
public abstract class ProjectileFactory {

    protected Texture texture;
    protected Animation flyingAnimation;
    protected Animation explodeAnimation;
    protected TextureRegion[][] splittedTextureAtlas;

    public abstract Projectile createProjectile(Vector2 position);
}
