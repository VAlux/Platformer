package com.platformer.abilities;

import com.badlogic.gdx.utils.Predicate;
import com.platformer.abilities.effect.DamageEffect;
import com.platformer.entities.Actor;
import com.platformer.entities.Char;
import com.platformer.entities.RenderableEntity;
import com.platformer.entities.projectiles.ParabolicFireballProjectileFactory;
import com.platformer.entities.projectiles.FireballProjectile;
import com.platformer.entities.projectiles.Projectile;
import com.platformer.screens.GameScreen;

import static com.platformer.states.ProjectileState.EXPLODING;

/**
 * Created by alexander on 01.05.15.
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
     * All of the fireball projectiles in the world.
     */
    protected Iterable<Projectile> fireballProjectiles;

    /**
     * Factory for creating renderable fireball projectiles.
     */
    private ParabolicFireballProjectileFactory fireballProjectileFactory;

    /**
     * The fireball filtering predicate.
     * Needed as a selection condition
     * to obtain the list of fireball projectiles from the general projectiles list.
     */
    protected Predicate<Projectile> selectFireballsPredicate = new Predicate<Projectile>() {
        @Override
        public boolean evaluate(Projectile projectile) {
            return projectile instanceof FireballProjectile;
        }
    };

    public Fireball(Actor source) {
        super(source);
        sourceChar = (Char) source;
        cooldownTime = 0.1f;
        energyCost = 10;
        damageEffect = new DamageEffect(10.0f);
        fireballProjectileFactory = new ParabolicFireballProjectileFactory();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        refreshProjectilesList();
        for (Projectile fireballProjectile : fireballProjectiles) {
            final RenderableEntity collisionTarget = fireballProjectile.getCollisionTarget();
            if (collisionTarget instanceof Char && fireballProjectile.getState() == EXPLODING) {
                damageEffect.apply((Char) collisionTarget);
            }
        }
    }

    /**
     * Get all of the projectiles of the fireball type.
     * TODO: Maybe need to get this method to the upper level of abstraction.
     */
    private void refreshProjectilesList() {
        fireballProjectiles = GameScreen.world.getProjectiles().select(selectFireballsPredicate);
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
        final FireballProjectile projectile = (FireballProjectile) fireballProjectileFactory.createProjectile(sourceChar.getPosition());
        if (isAvailable()) {
            launchProjectile(projectile, 300, 250, sourceChar.getOrientation());
            sourceChar.getStats().energy -= energyCost;
            isCoolingDown = false;
        }
    }
}