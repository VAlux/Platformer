package com.platformer.abilities;

import com.platformer.entities.Actor;
import com.platformer.entities.Entity;
import com.platformer.entities.projectiles.Projectile;
import com.platformer.screens.GameScreen;
import com.platformer.states.Orientation;

public abstract  class Ability extends Entity {

    /**
     * Ability owner.
     */
    protected Actor source;

    /**
     * Time left to recharge the ability.
     */
    protected float currentCooldown;

    /**
     * Total time to recharge the ability.
     */
    protected float cooldownTime;

    /**
     * Amount of energy needed to activate the ability.
     */
    protected int energyCost;

    /**
     * Is the ability currently cooling down.
     */
    protected boolean isCoolingDown;

    /**
     * Override this method to describe the mechanism of activating the ability.
     */
    public abstract void activate();

    protected Ability(final Actor source) {
        super();
        cooldownTime = currentCooldown = 1.0f; // default cooldown.
        energyCost = 0; // default energy consumption.
        isCoolingDown = true;
        this.source = source;
    }

    /**
     * updates ability's cooldown and checks, is the ability ready to be refreshed.
     * @param delta time passed between frames in ms.
     */
    public void updateCooldown(final float delta) {
        currentCooldown -= delta;
        if (currentCooldown <= 0.0f) {
            refresh();
        }
    }

    /**
     * Launch the specified projectile in the direction, defined by character orientation.
     * @param projectile projectile to launch.
     * @param vel target vel.
     * @param acc target acc.
     * @param orientation character orientation to define the projectile flight direction.
     */
    protected void launchProjectile(Projectile projectile, float vel, float acc, Orientation orientation) {
        switch (orientation) {
            case LEFT:
                projectile.setVelocity(0.0f, vel);
                projectile.setAcceleration(-acc, acc * 2);
                break;
            case RIGHT:
                projectile.setVelocity(0.0f, vel);
                projectile.setAcceleration(acc, acc * 2);
                break;
        }
        GameScreen.world.addProjectile(projectile);
    }

    /**
     * Prepares the ability to the next use.
     */
    public void refresh() {
        this.isCoolingDown = true;
        this.currentCooldown = cooldownTime;
    }

    public void setCooldownTime(float cooldownTime) {
        this.cooldownTime = cooldownTime;
    }

    public int getEnergyCost() {
        return energyCost;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (!isCoolingDown)
            updateCooldown(delta);
    }
}
