package com.platformer.entities.characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.platformer.Constants;
import com.platformer.abilities.DirectFireball;
import com.platformer.abilities.Fireball;
import com.platformer.entities.Player;
import com.platformer.maps.Map;
import com.platformer.utils.GraphicTools;

public final class Kirby extends Player {

    private Animation idleRightAnimation;
    private Animation idleLeftAnimation;
    private Animation walkLeftAnimation;
    private Animation walkRightAnimation;
    private Animation jumpAnimation;
    private Animation runAnimation;
    private Animation dyingAnimation;
    private Animation deadAnimation;

    private Fireball fireballAbility;
    private DirectFireball directFireballAbility;

    public Kirby(Map map) {
        super(map);
        this.texture = new Texture(Constants.ATL_KIRBY);
        splittedTextureAtlas = new TextureRegion(texture).split((int) map.getTileWidth(), (int) map.getTileHeight());
        createAnimations();
    }

    private void createAnimations() {
        walkRightAnimation = new Animation(0.1f, GraphicTools.extractAnimation(splittedTextureAtlas, 0, 4));
        walkRightAnimation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

        walkLeftAnimation = GraphicTools.flipAnimation(walkRightAnimation, true, false);
        walkRightAnimation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

        jumpAnimation = new Animation(0.1f, GraphicTools.extractAnimation(splittedTextureAtlas, 0, 4));

        idleRightAnimation = new Animation(0.1f, GraphicTools.extractAnimation(splittedTextureAtlas, 0, 4));
        idleRightAnimation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

        idleLeftAnimation = GraphicTools.flipAnimation(idleRightAnimation, true, false);
        idleLeftAnimation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
    }

    @Override
    protected void createAbilities() {
        super.createAbilities();
        fireballAbility = new Fireball(this);
        directFireballAbility = new DirectFireball(this);
        this.abilities.addAll(fireballAbility, directFireballAbility);
    }

    @Override
    public Animation getAnimation(){
        switch (state) {
            case IDLE_LEFT:
                return idleLeftAnimation;
            case IDLE_RIGHT:
                return idleRightAnimation;
            case RUN:
                return runAnimation;
            case JUMP:
                return jumpAnimation;
            case WALK_LEFT:
                return walkLeftAnimation;
            case WALK_RIGHT:
                return walkRightAnimation;
            case DYING:
                return dyingAnimation;
            case DEAD:
                return deadAnimation;
            default:
                return idleRightAnimation;
        }
    }

    public Fireball getFireballAbility() {
        return fireballAbility;
    }

    public void setFireballAbility(Fireball fireballAbility) {
        this.fireballAbility = fireballAbility;
    }

    public DirectFireball getDirectFireballAbility() {
        return directFireballAbility;
    }

    public void setDirectFireballAbility(DirectFireball directFireballAbility) {
        this.directFireballAbility = directFireballAbility;
    }
}