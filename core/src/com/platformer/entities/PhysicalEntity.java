package com.platformer.entities;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import static com.platformer.stats.WorldConstants.GRAVITY;

/**
 * Created by alexander on 19.04.15.
 */
public class PhysicalEntity extends Actor {

    protected Vector2 position;
    protected Vector2 velocity;
    protected Vector2 acceleration;

    protected float maxVelocity;
    protected float friction;
    protected float gravityScale;
    protected float accelerationFactor;

    protected Rectangle bounds;

    public PhysicalEntity() {
        super();
        init();
    }

    private void init() {
        this.velocity = new Vector2();
        this.acceleration = new Vector2();
        this.gravityScale = 1.0f;
        this.friction = 0.9f;
        this.maxVelocity = 500.0f;
        this.accelerationFactor = 20.0f;
    }

    @Override
    public void act(float delta) {
        acceleration.y = -GRAVITY * gravityScale * delta;
        velocity.add(acceleration);

        if (acceleration.x == 0)
            velocity.x *= friction;

        velocity.x = MathUtils.clamp(velocity.x, -maxVelocity, maxVelocity);
    }

    @Override
    public void destroy() {
        //Nothing to destroy =)
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