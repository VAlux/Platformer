package com.platformer.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.platformer.enums.ActorState;
import com.platformer.maps.Map;

import static com.platformer.enums.ActorState.*;

public class Actor implements RenderableEntity {

    protected long id;
    protected float stateTime;
    protected Vector2 position;
    protected Rectangle bounds;
    protected Texture texture;

    protected Vector2 velocity;
    protected Vector2 acceleration;
    protected Vector2 spawnPosition;
    protected ActorStats stats;
    protected ActorStats etalonStats;
    protected ActorState state;
    protected MapObjects specialObjects;
    protected MapObjects collidableObjects;
    protected TextureRegion[][] splittedTextureAtlas;
    protected boolean isOnGround;

    protected Actor(final float X, final float Y){
        id = (long) Math.random() * 1000L; /// TODO: replace with hash later
        velocity = new Vector2();
        acceleration = new Vector2();
        position = new Vector2(X, Y);
        bounds = new Rectangle(X, Y, 32, 32);
        etalonStats = new ActorStats();
        etalonStats.loadDefaults();
        stats = new ActorStats();
        stats.copy(etalonStats);
        stateTime = 0;
    }

    protected Actor(Map map, Vector2 spawnPosition) {
        this(spawnPosition.x, spawnPosition.y);
        this.spawnPosition = spawnPosition;
        specialObjects = map.getSpecObjectsLayer().getObjects();
        collidableObjects = map.getCollisionLayer().getObjects();
    }

    protected Actor(Map map, float xPos, float yPos) {
        this(xPos, yPos);
        this.spawnPosition = new Vector2(xPos, yPos);
        specialObjects = map.getSpecObjectsLayer().getObjects();
        collidableObjects = map.getCollisionLayer().getObjects();
    }

    public void act(final float deltaTime) {
        acceleration.y = -stats.GRAVITY * deltaTime;
        velocity.add(acceleration);

        if (acceleration.x == 0)
            velocity.x *= stats.FRICTION;

        velocity.x = MathUtils.clamp(velocity.x, -stats.MAX_VELOCITY, stats.MAX_VELOCITY);
        move(deltaTime);
        stateTime += deltaTime;
    }

    public void move(final float deltaTime) {
        bounds.x += velocity.x * deltaTime;
        Rectangle collRect;
        for (RectangleMapObject object : collidableObjects.getByType(RectangleMapObject.class)) {
            collRect = object.getRectangle();
            if (bounds.overlaps(collRect)) {
                if (velocity.x > 0)
                    bounds.x = collRect.x - bounds.width - 0.01f;
                else
                    bounds.x = collRect.x + collRect.width + 0.01f;

                velocity.x = 0;
            }
        }
        bounds.y += velocity.y * deltaTime;
        for (RectangleMapObject object : collidableObjects.getByType(RectangleMapObject.class)) {
            collRect = object.getRectangle();
            if (bounds.overlaps(collRect)) {
                if (velocity.y > 0) {
                    bounds.y = collRect.y - bounds.height - 0.01f;
                }
                else {
                    bounds.y = collRect.y + collRect.height + 0.01f;
                    isOnGround = true;
                }
                velocity.y = 0;
            }
        }
        position.set(bounds.x, bounds.y);
    }

    public void spawn() {
        this.bounds.setPosition(spawnPosition);
        position.set(bounds.x, bounds.y);
        velocity.set(0, 0);
        acceleration.set(0, 0);
        state = SPAWN;
    }

    protected TextureRegion[] extractAnimation(final int startFrame, final int framesAmount){
        int frameIndex = 0;
        int framesAdded = 0;
        final TextureRegion[] frames = new TextureRegion[framesAmount];
        for (TextureRegion[] textureRegionRows : this.splittedTextureAtlas) {
            for (TextureRegion splittedTexture : textureRegionRows) {
                if (frameIndex++ >= startFrame && framesAdded < framesAmount)
                    frames[framesAdded++] = splittedTexture;
                else if (framesAdded == framesAmount)
                    return frames;
            }
        }
        return frames;
    }

    public ActorStats getStats() {
        return stats;
    }

    protected boolean isAlive(){
        return position.y > 0.0f && stats.health > 0 && state != DEAD;
    }

    public void dispose() {
        texture.dispose();
    }

    @Override
    public Animation getCurrentAnimation() {
        return null;
    }

    public double getId() {
        return id;
    }

    public Vector2 getPosition() {
        return position;
    }

    public float getStateTime() {
        return stateTime;
    }

    public Rectangle getBounds() {
        return bounds;
    }
}