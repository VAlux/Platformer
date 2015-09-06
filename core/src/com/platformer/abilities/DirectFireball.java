package com.platformer.abilities;

import com.platformer.entities.Actor;
import com.platformer.entities.projectiles.SmallFireballProjectile;

/**
 * Created by alexander on 06.09.15.
 */
public class DirectFireball extends Fireball {
    public DirectFireball(Actor source) {
        super(source);
    }

    @Override
    public void activate() {
        final SmallFireballProjectile projectile = new SmallFireballProjectile(sourceChar.getPosition());
        if (isAvailable()) {
            projectile.setGravityAffection(false);
            launchProjectile(projectile, 0, 650, sourceChar.getOrientation());
            sourceChar.getStats().energy -= energyCost;
            isCoolingDown = false;
        }
    }
}
