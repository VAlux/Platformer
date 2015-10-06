package com.platformer.entities.projectiles;

import com.badlogic.gdx.math.Vector2;
import com.platformer.entities.RenderableEntity;
import com.platformer.screens.GameScreen;
import com.platformer.states.ProjectileState;

import static com.platformer.states.ProjectileState.FLYING;

public abstract class Projectile extends RenderableEntity {

    protected ProjectileState state;
    protected float TTL;

    public Projectile(Vector2 position) {
        this(position.x, position.y);
    }

    public Projectile(float X, float Y) {
        super(X, Y);
        init();
    }

    public Projectile(float X, float Y, int width, int height) {
        super(X, Y, width, height);
        init();
    }

    public Projectile(Vector2 pos, int width, int height) {
        this(pos.x, pos.y, width, height);
    }

    protected void init() {
        this.TTL = 1.0f; // 1 second default TTL.
        state = FLYING;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        super.move(delta);
        TTL -= delta;
    }

    @Override
    public void destroy() {
        super.destroy();
        GameScreen.world.removeRenderableActor(this);
    }

    public boolean ttlExpired() {
        return TTL <= 0;
    }

    public float getTTL() {
        return TTL;
    }

    public void setTTL(float TTL) {
        this.TTL = TTL;
    }

    public ProjectileState getState() {
        return state;
    }

    public void setState(ProjectileState state) {
        this.state = state;
    }
}