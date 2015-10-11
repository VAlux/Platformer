package com.platformer.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Vector2;
import com.platformer.abilities.Ability;
import com.platformer.states.CharacterState;
import com.platformer.states.Orientation;
import com.platformer.stats.CharacterStats;

import java.util.ArrayList;

import static com.platformer.states.CharacterState.*;
import static com.platformer.states.Orientation.RIGHT;

public class Char extends RenderableEntity {

    protected Vector2 spawnPosition;
    protected CharacterStats stats;
    protected CharacterStats etalonStats;
    protected CharacterState state;
    protected Orientation orientation;
    protected ArrayList<Ability> abilities;
    protected boolean isOnWall;
    protected boolean canWallJump;

    protected Char(float xPos, float yPos) {
        super(xPos, yPos);
        spawnPosition = new Vector2(xPos, yPos);
        velocity = new Vector2();
        acceleration = new Vector2();
        etalonStats = new CharacterStats();
        etalonStats.loadDefaults();
        stats = new CharacterStats();
        stats.copy(etalonStats);
        orientation = RIGHT;
        createAbilities();
    }

    protected Char(Vector2 spawnPosition) {
        this(spawnPosition.x, spawnPosition.y);
    }

    protected void createAbilities() {
        abilities = new ArrayList<>();
    }

    public void spawn() {
        bounds.setPosition(spawnPosition);
        position.set(bounds.x, bounds.y);
        velocity.set(0, 0);
        acceleration.set(0, 0);
        state = SPAWN;
        orientation = RIGHT;
        stats.copy(etalonStats);
    }

    @Override
    public void act(final float delta) {
        super.act(delta);

        for (Ability ability : abilities) {
            ability.act(delta);
        }

        move(delta);
    }

    @Override
    public void move(final float deltaTime) {
        super.move(deltaTime);
        if (hasXCollision) {
            isOnWall = !isOnGround;
        }
        if (hasYCollision){
            canWallJump = true;
            isOnWall = false;
            if (state == JUMP) {
                switchToIdleState();
            }
        }
    }

    /**
     * determine the character orientation i.e.: left or right and return the corresponding state.
     */
    public void switchToIdleState() {
        if (velocity.x > 0) {
            state = IDLE_RIGHT;
        } else {
            state = IDLE_LEFT;
        }
    }

    /**
     * Character jumping action.
     * Some logic to not to allow jumping if the character is not on the ground and
     * allowing only 1 wall jump.
     */
    public void jump() {
        if (isOnGround()) {
            velocity.y = stats.jumpVelocity;
            setOnGround(false);
        }
        if (isOnWall  && canWallJump) {
            velocity.y = stats.jumpVelocity;
            setCanWallJump(false);
            setOnWall(false);
        }
        setState(JUMP);
    }

    /**
     * Character is alive if It is not fallen below the map, or health is > 0, and It's state is not set to DEAD.
     * @return Is character alive?
     */
    protected boolean isAlive() {
        return position.y > 0.0f && stats.health > 0 && state != DEAD;
    }

    @Override
    public Animation getAnimation() {
        return null;
    }

    public CharacterStats getStats() {
        return stats;
    }

    public CharacterState getState() {
        return state;
    }

    public void setState(CharacterState state) {
        this.state = state;
    }

    public void setAcceleration(float x, float y) {
        this.acceleration.set(x, y);
    }

    public Vector2 getSpawnPosition() {
        return spawnPosition;
    }

    public boolean isOnGround() {
        return isOnGround;
    }

    public void setOnGround(boolean isOnGround) {
        this.isOnGround = isOnGround;
    }

    public boolean isOnWall() {
        return isOnWall;
    }

    public void setOnWall(boolean isOnWall) {
        this.isOnWall = isOnWall;
    }

    public boolean isCanWallJump() {
        return canWallJump;
    }

    public void setCanWallJump(boolean canWallJump) {
        this.canWallJump = canWallJump;
    }

    public ArrayList<Ability> getAbilities() {
        return abilities;
    }

    public boolean hasXCollision() {
        return hasXCollision;
    }

    public boolean hasYCollision() {
        return hasYCollision;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }
}