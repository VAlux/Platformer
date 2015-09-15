package com.platformer.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class RenderableEntity extends PhysicalEntity {

    private static final int DEF_BOUNDS_SIZE = 32;

    protected Texture texture;
    protected TextureRegion[][] splittedTextureAtlas;

    public abstract Animation getAnimation();

    protected RenderableEntity() {
        this(0, 0);
    }

    protected RenderableEntity(float X, float Y) {
        super();
        position = new Vector2(X, Y);
        bounds = new Rectangle(X, Y, DEF_BOUNDS_SIZE, DEF_BOUNDS_SIZE);
    }

    public void destroy() {
        if (texture != null) {
            texture.dispose();
        }
    }
}