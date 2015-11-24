package com.platformer.entities.projectiles;

import com.badlogic.gdx.math.Vector2;
import com.platformer.entities.Char;

/**
 * Created by alexander on 06.09.15.
 */
public class DirectFireballProjectile extends FireballProjectile {

    public DirectFireballProjectile(Char source) {
        super(source);
        setGravityAffection(false); // the only thing, which differs from the usual fireball projectile.
    }
}
