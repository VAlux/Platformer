package com.platformer.entities.projectiles;

import com.platformer.entities.PhysicalEntity;

/**
 * Created by alexander on 06.09.15.
 */
public class DirectFireballProjectile extends ParabolicFireballProjectile {

    public DirectFireballProjectile(PhysicalEntity source) {
        super(source);
        setGravityAffection(false); // the only thing, which differs from the usual fireball projectile.
    }
}
