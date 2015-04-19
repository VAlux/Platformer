package com.platformer.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.platformer.maps.Map;

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

    protected RenderableEntity(Map map, Vector2 position) {
        this(map, position.x, position.y);
    }

    protected RenderableEntity(Map map, float X, float Y) {
        super(map);
        bounds = new Rectangle(X, Y, DEF_BOUNDS_SIZE, DEF_BOUNDS_SIZE);
        position = new Vector2(X, Y);
    }

    protected RenderableEntity(Map map, float X, float Y, int width, int height) {
        super(map);
        bounds = new Rectangle(X, Y, width, height);
        position = new Vector2(X, Y);
    }

    public void dispose() {
        texture.dispose();
    }
}