package com.platformer.fx;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.platformer.utils.Tools;

import static com.badlogic.gdx.graphics.g2d.Animation.PlayMode.NORMAL;

public class FXExplosion extends FX {

    public FXExplosion(float X, float Y) {
        super(X, Y);
        width = height = 96;
        texture = new Texture("tilesets/fx/explosion_big.png");
        splittedTextureAtlas = new TextureRegion(texture).split(width, height);
        createAnimations();
    }

    public FXExplosion(Vector2 position) {
        this(position.x, position.y);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (animation.isAnimationFinished(stateTime)) {
            setRunning(false);
        }
    }

    @Override
    protected void createAnimations() {
        animation = new Animation(0.1f, Tools.extractAnimation(splittedTextureAtlas, 0, 5));
        animation.setPlayMode(NORMAL);
    }
}