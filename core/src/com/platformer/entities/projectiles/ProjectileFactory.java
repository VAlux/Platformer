package com.platformer.entities.projectiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.platformer.abilities.Ability;
import com.platformer.entities.PhysicalEntity;

/**
 * Created by alvo on 15.09.15.
 * Abstract Factory for creating projectiles.
 */
public abstract class ProjectileFactory {

    protected Texture texture;
    protected Animation flyingAnimation;
    protected Animation explodeAnimation;
    protected TextureRegion[][] splittedTextureAtlas;

    public abstract Projectile createProjectile(PhysicalEntity source, Ability sourceAbility);
}
