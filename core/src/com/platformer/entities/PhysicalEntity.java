package com.platformer.entities;

import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.platformer.maps.Map;
import com.platformer.screens.GameScreen;

import static com.platformer.stats.WorldConstants.GRAVITY;

/**
 * Created by alexander on 19.04.15.
 */
public class PhysicalEntity extends Entity {

    protected Vector2 position;
    protected Vector2 velocity;
    protected Vector2 acceleration;

    protected float maxVelocity;
    protected float friction;
    protected float gravityScale;
    protected float accelerationFactor;

    protected Rectangle bounds;

    protected boolean hasXCollision;
    protected boolean hasYCollision;
    protected boolean isOnGround;
    protected boolean isDynamic;

    protected MapObjects specialObjects;
    protected MapObjects collidableObjects;

    protected Map map;

    public PhysicalEntity() {
        super();
        init();
    }

    private void init() {

        //default physics parameters.
        velocity = new Vector2();
        acceleration = new Vector2();
        gravityScale = 1.0f;
        friction = 0.9f;
        maxVelocity = 500.0f;
        accelerationFactor = 20.0f;
        isDynamic = true;

        map = GameScreen.world.getMap();
        specialObjects = map.getSpecObjectsLayer().getObjects();
        collidableObjects = map.getCollisionLayer().getObjects();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if(isDynamic) {
            acceleration.y = -GRAVITY * gravityScale * delta;
            velocity.add(acceleration);

            if (acceleration.x == 0)
                velocity.x *= friction;

            velocity.x = MathUtils.clamp(velocity.x, -maxVelocity, maxVelocity);
        }
    }

    public void move(final float delta) {
        if (isDynamic) {
            bounds.x += velocity.x * delta;
            hasXCollision = hasYCollision = false;
            Rectangle collRect;
            for (RectangleMapObject object : collidableObjects.getByType(RectangleMapObject.class)) {
                collRect = object.getRectangle();
                if (bounds.overlaps(collRect)) {
                    hasXCollision = true;
                    if (velocity.x > 0)
                        bounds.x = collRect.x - bounds.width - 0.01f;
                    else
                        bounds.x = collRect.x + collRect.width + 0.01f;

                    velocity.x = 0;
                }
            }
            bounds.y += velocity.y * delta;
            for (RectangleMapObject object : collidableObjects.getByType(RectangleMapObject.class)) {
                collRect = object.getRectangle();
                if (bounds.overlaps(collRect)) {
                    if (velocity.y > 0) {
                        bounds.y = collRect.y - bounds.height - 0.01f;
                    } else {
                        hasYCollision = true;
                        bounds.y = collRect.y + collRect.height + 0.01f;
                        isOnGround = true;
                    }
                    velocity.y = 0;
                }
            }
            if (!hasYCollision) {
                isOnGround = false;
            }
            position.set(bounds.x, bounds.y);
        }
    }

    public boolean isDynamic() {
        return isDynamic;
    }

    public void setDynamic(boolean isDynamic) {
        this.isDynamic = isDynamic;
    }

    @Override
    public void destroy() {
        //Nothing to destroy =)
    }

    public boolean hasCollision() {
        return hasXCollision || hasYCollision;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }

    public Vector2 getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(Vector2 acceleration) {
        this.acceleration = acceleration;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public float getMaxVelocity() {
        return maxVelocity;
    }

    public void setMaxVelocity(float maxVelocity) {
        this.maxVelocity = maxVelocity;
    }

    public float getFriction() {
        return friction;
    }

    public void setFriction(float friction) {
        this.friction = friction;
    }

    public float getGravityScale() {
        return gravityScale;
    }

    public void setGravityScale(float gravityScale) {
        this.gravityScale = gravityScale;
    }

    public float getAccelerationFactor() {
        return accelerationFactor;
    }

    public void setAccelerationFactor(float accelerationFactor) {
        this.accelerationFactor = accelerationFactor;
    }
}