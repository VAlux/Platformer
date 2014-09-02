package com.platformer.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class GameEntity implements RenderableEntity {

    protected double id;
    protected float stateTime;
    protected Vector2 position;
    protected Rectangle bounds;
    protected Texture texture;

    public GameEntity() {
        id = Math.random(); /// TODO: replace with hash later
    }

    @Override
    public Animation getCurrentAnimation() {
        return null;
    }

    public double getId() {
        return id;
    }

    public float getStateTime() {
        return stateTime;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(final float X, final float Y) {
        this.position.set(X, Y);
    }

    public Rectangle getBounds() {
        return bounds;
    }
}
