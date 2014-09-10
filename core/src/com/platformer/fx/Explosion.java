package com.platformer.fx;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.platformer.utils.Tools;

public class Explosion extends FX {

    public Explosion(float X, float Y) {
        super(X, Y);
        width = height = 96;
        texture = new Texture("tilesets/fx/explosion_big.png");
        splittedTextureAtlas = new TextureRegion(texture).split(width, height);
    }

    @Override
    protected void createAnimations() {
        this.animation = new Animation(0.1f, Tools.extractAnimation(splittedTextureAtlas, 0, 5));
    }
}
