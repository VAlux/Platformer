package com.platformer.entities.projectiles;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.platformer.abilities.Ability;
import com.platformer.entities.Char;
import com.platformer.entities.PhysicalEntity;
import com.platformer.entities.RenderableEntity;
import com.platformer.screens.GameScreen;
import com.platformer.states.ProjectileState;

import static com.platformer.Constants.GM_COLLISION_GAP;
import static com.platformer.states.ProjectileState.FLYING;

public abstract class Projectile extends RenderableEntity {

    protected static final int PROJECTILE_TILE_SIZE = 64;
    protected static final int PROJECTILE_SCALE_FACTOR = 4;
    protected static final int PROJECTILE_SIZE = PROJECTILE_TILE_SIZE / PROJECTILE_SCALE_FACTOR;
    protected ProjectileState state;
    protected PhysicalEntity source;
    protected Ability sourceAbility;
    protected float timeToLive;

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

    public Projectile(Vector2 position, int width, int height) {
        this(position.x, position.y, width, height);
    }

    public Projectile(PhysicalEntity source, int width, int height) {
        this(source.getPosition(), width, height);
        this.source = source;
    }

    protected void init() {
        this.timeToLive = 1.0f; // 1 second default TTL.
        state = FLYING;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        super.move(delta);
        timeToLive -= delta;
    }

    @Override
    protected void handleVerticalCollisions() {
        for (Rectangle collRect: GameScreen.world.getCollisionObjects()) {
           if (collRect != source.getBounds() && bounds != collRect && bounds.overlaps(collRect)) {
               hasYCollision = true;
                if (velocity.y > 0) {
                    bounds.y = collRect.y - bounds.height - GM_COLLISION_GAP;
                } else {
                    bounds.y = collRect.y + collRect.height + GM_COLLISION_GAP;
                }
                velocity.y = 0;
            }
        }
    }

    @Override
    protected void handleHorizontalCollisions() {
        for (Rectangle collRect : GameScreen.world.getCollisionObjects()) {
            if (collRect != source.getBounds() && bounds != collRect && bounds.overlaps(collRect)) {
                hasXCollision = true;
                if (velocity.x > 0) {
                    bounds.x = collRect.x - bounds.width - GM_COLLISION_GAP;
                } else {
                    bounds.x = collRect.x + collRect.width + GM_COLLISION_GAP;
                }
                velocity.x = 0;
            }
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        GameScreen.world.removeRenderableActor(this);
    }

    public boolean ttlExpired() {
        return timeToLive <= 0;
    }

    public float getTimeToLive() {
        return timeToLive;
    }

    public void setTimeToLive(float timeToLive) {
        this.timeToLive = timeToLive;
    }

    public ProjectileState getState() {
        return state;
    }

    public void setState(ProjectileState state) {
        this.state = state;
    }

    public PhysicalEntity getSource() {
        return source;
    }

    public void setSource(Char source) {
        this.source = source;
    }

    public Ability getSourceAbility() {
        return sourceAbility;
    }

    public void setSourceAbility(Ability sourceAbility) {
        this.sourceAbility = sourceAbility;
    }
}