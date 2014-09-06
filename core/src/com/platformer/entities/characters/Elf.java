package com.platformer.entities.characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.platformer.entities.Player;
import com.platformer.maps.Map;

public final class Elf extends Player {

    private Animation idleAnimation;
    private Animation walkLeftAnimation;
    private Animation walkRightAnimation;
    private Animation jumpAnimation;
    private Animation runAnimation;
    private Animation dyingAnimation;
    private Animation deadAnimation;

    public Elf(Map map, int inventoryCapacity) {
        super(map, inventoryCapacity);
        this.texture = new Texture("tilesets/atlases/character-elven.png");
        splittedTextureAtlas = new TextureRegion(texture).split((int) map.getTileWidth(), (int) map.getTileHeight());
        createAnimations();
    }

    private void createAnimations() {
        walkRightAnimation = new Animation(0.1f, extractAnimation(0, 3));
        walkLeftAnimation = new Animation(0.1f, extractAnimation(9, 3));
        jumpAnimation = new Animation(0.1f, extractAnimation(4, 3));
        idleAnimation = new Animation(0.1f, splittedTextureAtlas[0][0]); // need to be added. no art now :(
    }

    @Override
    public Animation getCurrentAnimation(){
        switch (state) {
            case IDLE:
                return idleAnimation;
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
                return idleAnimation;
        }
    }
}