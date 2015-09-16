package com.platformer.entities.characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.platformer.abilities.DirectFireball;
import com.platformer.abilities.Fireball;
import com.platformer.entities.Player;
import com.platformer.maps.Map;
import com.platformer.utils.Tools;

public final class Elf extends Player {

    private Animation idleRightAnimation;
    private Animation idleLeftAnimation;
    private Animation walkLeftAnimation;
    private Animation walkRightAnimation;
    private Animation jumpAnimation;
    private Animation runAnimation;
    private Animation dyingAnimation;
    private Animation deadAnimation;

    public Elf(Map map) {
        super(map);
        this.texture = new Texture("tilesets/atlases/character-elven.png");
        splittedTextureAtlas = new TextureRegion(texture).split((int) map.getTileWidth(), (int) map.getTileHeight());
        createAnimations();
    }

    private void createAnimations() {
        walkRightAnimation = new Animation(0.1f, Tools.extractAnimation(splittedTextureAtlas, 0, 3));
        walkLeftAnimation = new Animation(0.1f, Tools.extractAnimation(splittedTextureAtlas, 9, 3));
        jumpAnimation = new Animation(0.1f, Tools.extractAnimation(splittedTextureAtlas, 4, 3));
        idleRightAnimation = new Animation(0.1f, splittedTextureAtlas[0][0]); //just a static image for now.
        idleLeftAnimation = new Animation(0.1f, splittedTextureAtlas[2][0]); // just a static image for now.
    }

    @Override
    protected void createAbilities() {
        super.createAbilities();
        this.abilities.add(new Fireball(this));
        this.abilities.add(new DirectFireball(this));
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
}