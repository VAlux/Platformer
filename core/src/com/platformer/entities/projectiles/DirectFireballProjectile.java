package com.platformer.entities.projectiles;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by alexander on 06.09.15.
 */
public class DirectFireballProjectile extends FireballProjectile {

    public DirectFireballProjectile(Vector2 position) {
        super(position);
        setGravityAffection(false); // the only thing, which differs from the usual fireball projectile.
    }
}
