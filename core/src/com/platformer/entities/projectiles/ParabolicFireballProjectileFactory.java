package com.platformer.entities.projectiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.platformer.Constants;
import com.platformer.abilities.Ability;
import com.platformer.entities.PhysicalEntity;
import com.platformer.utils.GraphicTools;

import static com.badlogic.gdx.graphics.g2d.Animation.PlayMode.LOOP;

/**
 * Created by alvo on 15.09.15.
 */
public class ParabolicFireballProjectileFactory extends ProjectileFactory {

    public ParabolicFireballProjectileFactory() {
        texture = new Texture(Constants.ATL_EXPLOSION_SMALL);
        splittedTextureAtlas = new TextureRegion(texture).split(60, 60);
        flyingAnimation = new Animation(0.1f, GraphicTools.extractAnimation(splittedTextureAtlas, 1, 3));
        explodeAnimation = new Animation(0.1f, GraphicTools.extractAnimation(splittedTextureAtlas, 6, 10));
        flyingAnimation.setPlayMode(LOOP);
        explodeAnimation.setPlayMode(LOOP);
    }

    @Override
    public Projectile createProjectile(PhysicalEntity source, Ability parabolicFireball) {
        final ParabolicFireballProjectile projectile = new ParabolicFireballProjectile(source);
        projectile.setSourceAbility(parabolicFireball);
        projectile.setFlyingAnimation(flyingAnimation);
        projectile.setExplodeAnimation(explodeAnimation);
        return projectile;
    }
}