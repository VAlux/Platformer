package com.platformer.entities.projectiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.platformer.utils.Tools;

import static com.badlogic.gdx.graphics.g2d.Animation.PlayMode.LOOP;

/**
 * Created by alvo on 15.09.15.
 */
public class DirectFireballProjectileFactory extends ProjectileFactory {

    public DirectFireballProjectileFactory() {
        texture = new Texture("tilesets/fx/explosion_small.png");
        splittedTextureAtlas = new TextureRegion(texture).split(60, 60);
        flyingAnimation = new Animation(0.1f, Tools.extractAnimation(splittedTextureAtlas, 1, 3));
        explodeAnimation = new Animation(0.1f, Tools.extractAnimation(splittedTextureAtlas, 6, 10));
        flyingAnimation.setPlayMode(LOOP);
        explodeAnimation.setPlayMode(LOOP);
    }

    @Override
    public Projectile createProjectile(Vector2 position) {
        final DirectFireballProjectile projectile = new DirectFireballProjectile(position);
        projectile.setFlyingAnimation(flyingAnimation);
        projectile.setExplodeAnimation(explodeAnimation);
        return projectile;
    }
}
