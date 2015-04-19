package com.platformer.entities.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.platformer.abilities.effect.DamageEffect;
import com.platformer.entities.Char;
import com.platformer.entities.Mob;
import com.platformer.entities.Player;
import com.platformer.enums.CharacterState;
import com.platformer.maps.Map;
import com.platformer.utils.Tools;

import java.util.ArrayList;

public final class SeekerMob extends Mob {

    private Animation idleAnimation;
    private Animation walkLeftAnimation;
    private Animation walkRightAnimation;

    private boolean targetLocated;

    public SeekerMob(Map map, Vector2 spawnPosition, ArrayList<Char> actors) {
        super(map, spawnPosition, actors);
        targetLocated = false;
        this.texture = new Texture("tilesets/atlases/character-elven.png");
        splittedTextureAtlas = new TextureRegion(texture).split((int) map.getTileWidth(), (int) map.getTileHeight());
        createAnimations();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        seek();
        if (targetLocated) {
            if (this.bounds.overlaps(attackTarget.getBounds())) { // target reached
                acceleration.set(0, 0);
                new DamageEffect(stats.offence).apply(attackTarget);
            } else {
                pursuit();
            }
        }
        else {
            acceleration.set(0, 0); // target lost, stop the pursuit.
        }
    }

    private void seek() {
        for (Char aChar : aChars) {
            ///TODO: replace this shit with target recognition mechanism.
            if (aChar instanceof Player && aChar.getBounds().overlaps(fieldOfView)){
                setAttackTarget(aChar);
                targetLocated = true;
                return;
            }
        }
        targetLocated = false;
    }

    private void pursuit() {
        if (attackTarget != null) {
            if (attackTarget.getPosition().x > this.position.x) {
                this.acceleration.x = accelerationFactor;
                state = CharacterState.WALK_RIGHT;
            } else if (attackTarget.getPosition().x < this.position.x){
                this.acceleration.x = -accelerationFactor;
                state = CharacterState.WALK_LEFT;
            }
            if (attackTarget.getPosition().y > this.position.y) {
                this.velocity.y = stats.jumpVelocity;
                state = CharacterState.JUMP;
            }
        }
    }

    private void createAnimations() {
        walkRightAnimation = new Animation(0.1f, Tools.extractAnimation(splittedTextureAtlas, 0, 3));
        walkLeftAnimation = new Animation(0.1f, Tools.extractAnimation(splittedTextureAtlas, 9, 3));
        idleAnimation = new Animation(0.1f, splittedTextureAtlas[0][0]); // need to be added. no art now :(
    }

    @Override
    public Animation getAnimation() {
        switch (state){
            case IDLE:
                return idleAnimation;
            case WALK_LEFT:
                return walkLeftAnimation;
            case WALK_RIGHT:
                return walkRightAnimation;
            default:
                return idleAnimation;
        }
    }
}
