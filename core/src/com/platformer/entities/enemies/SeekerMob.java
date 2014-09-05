package com.platformer.entities.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.platformer.skills.abilities.DamageEffect;
import com.platformer.entities.Actor;
import com.platformer.entities.Mob;
import com.platformer.enums.ActorState;
import com.platformer.maps.Map;

import java.util.ArrayList;

public final class SeekerMob extends Mob {

    private Animation idleAnimation;
    private Animation walkLeftAnimation;
    private Animation walkRightAnimation;

    private boolean targetLocated;

    public SeekerMob(Map map, Vector2 spawnPosition, ArrayList<Actor> actors) {
        super(map, spawnPosition, actors);
        targetLocated = false;
        this.texture = new Texture("tilesets/atlases/character-elven.png");
        splittedTextureAtlas = new TextureRegion(texture).split((int) map.getTileWidth(), (int) map.getTileHeight());
        createAnimations();
    }

    @Override
    public void act(float deltaTime) {
        super.act(deltaTime);
        seek();
        if (targetLocated) {
            if (this.bounds.overlaps(attackTarget.getBounds())) { // target reached
                acceleration.set(0, 0);
                new DamageEffect(attackTarget, stats.offence).run();
            } else {
                pursuit();
            }
        }
        else {
            acceleration.set(0, 0); // target lost, stop the pursuit.
        }
    }

    private void seek() {
        for (Actor actor : actors) {
            if (actor.getBounds().overlaps(fieldOfView)){
                setAttackTarget(actor);
                targetLocated = true;
                return;
            }
        }
        targetLocated = false;
    }

    private void pursuit() {
        if (attackTarget != null) {
            if (attackTarget.getPosition().x > this.position.x) {
                this.acceleration.x = stats.ACCELERATION;
                state = ActorState.WALK_RIGHT;
            } else if (attackTarget.getPosition().x < this.position.x){
                this.acceleration.x = -stats.ACCELERATION;
                state = ActorState.WALK_LEFT;
            }
        }
    }

    private void createAnimations() {
        walkRightAnimation = new Animation(0.1f, extractAnimation(0, 3));
        walkLeftAnimation = new Animation(0.1f, extractAnimation(9, 3));
        idleAnimation = new Animation(0.1f, splittedTextureAtlas[0][0]); // need to be added. no art now :(
    }

    @Override
    public Animation getCurrentAnimation() {
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
