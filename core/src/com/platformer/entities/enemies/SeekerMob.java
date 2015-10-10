package com.platformer.entities.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.platformer.abilities.effect.DamageEffect;
import com.platformer.entities.Char;
import com.platformer.entities.Mob;
import com.platformer.entities.Player;
import com.platformer.utils.GraphicTools;

import static com.platformer.states.CharacterState.WALK_LEFT;
import static com.platformer.states.CharacterState.WALK_RIGHT;

public final class SeekerMob extends Mob {

    private Animation idleRightAnimation;
    private Animation idleLeftAnimation;
    private Animation walkLeftAnimation;
    private Animation walkRightAnimation;

    private boolean targetLocated;

    public SeekerMob(Vector2 spawnPosition) {
        super(spawnPosition);
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

    private void patroll() {

    }

    private void attack() {

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
                walk(WALK_RIGHT);
            } else if (attackTarget.getPosition().x < this.position.x){
                walk(WALK_LEFT);
            }
            if (attackTarget.getPosition().y > this.position.y && isOnGround) {
                jump();
            }
        }
    }

    private void createAnimations() {
        walkRightAnimation = new Animation(0.1f, GraphicTools.extractAnimation(splittedTextureAtlas, 0, 3));
        walkLeftAnimation = new Animation(0.1f, GraphicTools.extractAnimation(splittedTextureAtlas, 9, 3));
        idleRightAnimation = new Animation(0.1f, splittedTextureAtlas[0][0]);
        idleLeftAnimation = new Animation(0.1f, splittedTextureAtlas[2][0]);
    }

    @Override
    public Animation getAnimation() {
        switch (state){
            case IDLE_LEFT:
                return idleRightAnimation;
            case IDLE_RIGHT:
                return idleLeftAnimation;
            case WALK_LEFT:
                return walkLeftAnimation;
            case WALK_RIGHT:
                return walkRightAnimation;
            default:
                return idleRightAnimation;
        }
    }
}
