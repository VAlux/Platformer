package com.platformer.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class RenderableEntity extends PhysicalEntity {

    protected Texture texture;
    protected TextureRegion[][] splittedTextureAtlas;
    protected Vector2 visualPosition;
    protected Vector2 visualPositionOffset;

    public abstract Animation getAnimation();

    protected RenderableEntity(float x, float y) {
        super(x, y);
        position = new Vector2(x, y);
        visualPosition = new Vector2(position);
        visualPositionOffset = new Vector2(0.0f, 0.0f);
    }

    protected RenderableEntity() {
        this(0, 0);
    }

    protected RenderableEntity(float x, float y, int width, int height) {
        this(x, y);
        bounds = new Rectangle(x, y, width, height);
    }

    public void destroy() {
        if (texture != null) {
            texture.dispose();
        }
    }

    public Vector2 getVisualPosition() {
        return visualPosition;
    }

    public void setVisualPosition(Vector2 visualPosition) {
        this.visualPosition = visualPosition;
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        visualPosition.set(x + visualPositionOffset.x, y + visualPositionOffset.y);
    }

    public Vector2 getVisualPositionOffset() {
        return visualPositionOffset;
    }

    public void setVisualPositionOffset(Vector2 visualPositionOffset) {
        this.visualPositionOffset = visualPositionOffset;
    }

    public void setVisualPositionOffset(float x, float y) {
        this.visualPositionOffset.set(x, y);
    }
}