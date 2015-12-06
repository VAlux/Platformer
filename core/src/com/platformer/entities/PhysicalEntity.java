package com.platformer.entities;

import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.platformer.maps.Map;
import com.platformer.screens.GameScreen;

import static com.platformer.Constants.GM_COLLISION_GAP;
import static com.platformer.Constants.GM_DEF_ACTOR_BOUNDS_SIZE;
import static com.platformer.Constants.GM_GRAVITY;

/**
 * Created by alexander on 19.04.15.
 * Represents every entity in the game world, which could be affected by physics.
 */
public class PhysicalEntity extends Entity {

    /**
     * Entity's position in the world.
     */
    protected Vector2 position;

    /**
     * Entity's velocity.
     */
    protected Vector2 velocity;

    /**
     * Entity's acceleration.
     */
    protected Vector2 acceleration;

    /**
     * Maximal entity's velocity for clamping.
     */
    protected float maxVelocity;

    /**
     * Entity's friction.
     */
    protected float friction;

    /**
     * The level of gravity affection on the entity.
     */
    protected float gravityScale;

    /**
     * Entity's acceleration factor.
     */
    protected float accelerationFactor;

    /**
     * Physical bound of the entity.
     */
    protected Rectangle bounds;

    /**
     * has collision on X-axis.
     */
    protected boolean hasXCollision;

    /**
     * has collision on X-axis.
     */
    protected boolean hasYCollision;

    /**
     *  is the entity is touching the ground.
     */
    protected boolean isOnGround;

    /**
     * Is the entity is affected by physics simulation.
     */
    protected boolean isDynamic;

    /**
     * Set of special objects on the map.
     */
    protected MapObjects specialObjects;

    /**
     * Reference to the world map.
     */
    protected Map map;

    /**
     * Default gravity scale.
     * Needed to make possible the restoring the default value for gravity scale.
     */
    protected float defaultGravityScale;

    public PhysicalEntity(float x, float y) {
        super();
        //default physics parameters.
        velocity = new Vector2();
        acceleration = new Vector2();
        gravityScale = defaultGravityScale = 1.0f; // normal Gravity affection.
        friction = 0.9f;
        maxVelocity = 500.0f;
        accelerationFactor = 20.0f;
        isDynamic = true;
        bounds = new Rectangle(x, y, GM_DEF_ACTOR_BOUNDS_SIZE, GM_DEF_ACTOR_BOUNDS_SIZE);
        map = GameScreen.world.getMap();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if(isDynamic) {
            acceleration.y = -GM_GRAVITY * gravityScale * delta;
            velocity.add(acceleration);

            if (acceleration.x == 0)
                velocity.x *= friction;

            velocity.x = MathUtils.clamp(velocity.x, -maxVelocity, maxVelocity);
        }
    }

    public void move(final float delta) {
        if (isDynamic) { // do we actually need to move it?
            hasXCollision = hasYCollision = false;

            bounds.x += velocity.x * delta;
            handleHorizontalCollisions();

            bounds.y += velocity.y * delta;
            handleVerticalCollisions();

            if (!hasYCollision) {
                isOnGround = false;
            }

           setPosition(bounds.x, bounds.y);
        }
    }

    protected void handleVerticalCollisions() {
        for (Rectangle collRect: GameScreen.world.getCollisionObjects()) {
            if (bounds != collRect && bounds.overlaps(collRect)) {
                hasYCollision = true;
                if (velocity.y > 0) {
                    bounds.y = collRect.y - bounds.height - GM_COLLISION_GAP;
                } else {
                    bounds.y = collRect.y + collRect.height + GM_COLLISION_GAP;
                    isOnGround = true;
                }
                velocity.y = 0;
            }
        }
    }

    protected void handleHorizontalCollisions() {
        for (Rectangle collRect : GameScreen.world.getCollisionObjects()) {
            if (bounds != collRect && bounds.overlaps(collRect)) {
                hasXCollision = true;
                if (velocity.x > 0)
                    bounds.x = collRect.x - bounds.width - GM_COLLISION_GAP;
                else
                    bounds.x = collRect.x + collRect.width + GM_COLLISION_GAP;

                velocity.x = 0;
            }
        }
    }

    public RenderableEntity getCollisionTarget() {
        if (hasCollision()) {
            for (RenderableEntity entity : GameScreen.world.getRenderableActors()) {
                if (entity.bounds != this.bounds && this.bounds.overlaps(entity.bounds)) {
                    return entity;
                }
            }
        }
        return null;
    }

    public boolean isDynamic() {
        return isDynamic;
    }

    public void setDynamic(boolean isDynamic) {
        this.isDynamic = isDynamic;
    }

    /**
     * Set is the entity is affected by gravity.
     * @param isAffected true -> Is affected by gravity, false -> not affected.
     */
    public void setGravityAffection(boolean isAffected) {
        if (isAffected) {
            setGravityScale(defaultGravityScale);
        } else {
            setGravityScale(0.0f);
        }
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

    public void setVelocity(float x, float y) {
        this.velocity.set(x, y);
    }

    public Vector2 getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(Vector2 acceleration) {
        this.acceleration = acceleration;
    }

    public void setAcceleration(float x, float y) {
        this.acceleration.set(x, y);
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

    public void setPosition(float x, float y) {
        position.set(x, y);
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

    @Override
    public void destroy() {
        super.destroy();
    }
}