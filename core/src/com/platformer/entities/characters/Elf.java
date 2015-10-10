package com.platformer.entities.characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.platformer.abilities.DirectFireball;
import com.platformer.abilities.Fireball;
import com.platformer.entities.Player;
import com.platformer.maps.Map;
import com.platformer.utils.GraphicTools;

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
        this.texture = new Texture("tilesets/atlases/characters.png");
        splittedTextureAtlas = new TextureRegion(texture).split((int) map.getTileWidth(), (int) map.getTileHeight());
        createAnimations();
    }

    private void createAnimations() {
        walkRightAnimation = new Animation(0.1f, GraphicTools.extractAnimation(splittedTextureAtlas, 46, 4));
        walkLeftAnimation = GraphicTools.flipAnimation(walkRightAnimation, true, false);
        jumpAnimation = new Animation(0.1f, GraphicTools.extractAnimation(splittedTextureAtlas, 51, 5));
        idleRightAnimation = new Animation(0.1f, GraphicTools.extractAnimation(splittedTextureAtlas, 56, 5));
        idleLeftAnimation = GraphicTools.flipAnimation(idleRightAnimation, true, false);
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