package com.platformer.abilities;

import com.platformer.Constants;
import com.platformer.abilities.effect.DamageEffect;
import com.platformer.entities.Actor;
import com.platformer.entities.Char;
import com.platformer.entities.projectiles.FireballProjectile;
import com.platformer.entities.projectiles.ParabolicFireballProjectileFactory;

/**
 * Created by alexander on 01.05.15.
 * Code for handling the Fireball ability.
 */
public class Fireball extends Ability {

    /**
     * The initiator of the fireball ability.
     */
    protected Char sourceChar;

    /**
     * Fireball damage effect.
     */
    protected DamageEffect damageEffect;

    /**
     * Factory for creating renderable fireball projectiles.
     */
    private ParabolicFireballProjectileFactory fireballProjectileFactory;

    public Fireball(Actor source) {
        super(source);
        sourceChar = (Char) source;
        cooldownTime = Constants.GM_FIREBALL_COOLDOWN_TIME;
        energyCost = Constants.GM_FIREBALL_ENERGY_COST;
        damageEffect = new DamageEffect(10.0f);
        fireballProjectileFactory = new ParabolicFireballProjectileFactory();
    }

    /**
     * Fireball hit the actor. If the target is Char -> apply Fireball effect to it.
     * @param target actor, which is hit by the projectile.
     */
    public void projectileHit(Actor target) {
        super.projectileHit(target);
        if (target instanceof Char) {
            damageEffect.apply((Char) target);
        }
    }

    /**
     * Ability is not cooling down and source character has enough energy to activate the ability.
     * @return If the ability is available.
     */
    protected boolean isAvailable() {
        return isCoolingDown && sourceChar.getStats().energy >= energyCost;
    }

    /**
     * Create fireball projectile and launch it.
     */
    @Override
    public void activate() {
        final FireballProjectile projectile =
                (FireballProjectile) fireballProjectileFactory.createProjectile(sourceChar, this);
        if (isAvailable()) {
            launchProjectile(projectile, 300, 250, sourceChar.getOrientation());
            sourceChar.getStats().energy -= energyCost;
            isCoolingDown = false;
        }
    }
}