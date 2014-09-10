package com.platformer.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.platformer.maps.Map;

public abstract class RenderableEntity extends Actor {

    protected Rectangle bounds;
    protected Vector2 position;
    protected Texture texture;
    protected TextureRegion[][] splittedTextureAtlas;

    public abstract Animation getCurrentAnimation();

    protected RenderableEntity(final float X, final float Y) {
        super();
        bounds = new Rectangle(X, Y, 32, 32);
    }

    protected RenderableEntity(Map map, Vector2 position) {
        this(map, position.x, position.y);
    }

    protected RenderableEntity(Map map, final float X, final float Y) {
        super(map);
        bounds = new Rectangle(X, Y, 32, 32);
        position = new Vector2(X, Y);
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void dispose() {
        texture.dispose();
    }
}
