package com.platformer.entities.projectiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.platformer.utils.Tools;

/**
 * Created by alexander on 06.09.15.
 */
public class SmallFireballProjectile extends FireballProjectile {

    public SmallFireballProjectile(Vector2 position) {
        super(position);
    }

    @Override
    protected void createAnimations() {
        texture = new Texture("tilesets/fx/explosion_small.png");
        splittedTextureAtlas = new TextureRegion(texture).split(60, 60);
        flyingAnimation = new Animation(0.1f, Tools.extractAnimation(splittedTextureAtlas, 1, 3));
        explodeAnimation = new Animation(0.1f, Tools.extractAnimation(splittedTextureAtlas, 6, 10));
    }
}
