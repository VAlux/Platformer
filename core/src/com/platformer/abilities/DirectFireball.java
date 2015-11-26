package com.platformer.abilities;

import com.platformer.entities.Actor;
import com.platformer.entities.projectiles.DirectFireballProjectile;
import com.platformer.entities.projectiles.DirectFireballProjectileFactory;

/**
 * Created by alexander on 06.09.15.
 */
public class DirectFireball extends Fireball {

    protected DirectFireballProjectileFactory projectileFactory;

    public DirectFireball(Actor source) {
        super(source);
        projectileFactory = new DirectFireballProjectileFactory();
    }

    @Override
    public void activate() {
        final DirectFireballProjectile projectile =
                (DirectFireballProjectile) projectileFactory.createProjectile(sourceChar, this);
        if (isAvailable()) {
            launchProjectile(projectile, 0, 650, sourceChar.getOrientation());
            sourceChar.getStats().energy -= energyCost;
            isCoolingDown = false;
        }
    }
}
