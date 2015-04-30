package com.platformer.entities.projectiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.platformer.entities.RenderableEntity;
import com.platformer.maps.Map;
import com.platformer.screens.GameScreen;
import com.platformer.states.ProjectileState;
import com.platformer.utils.Tools;

import static com.platformer.states.ProjectileState.FLYING;

public class Projectile extends RenderableEntity {

    protected Animation flyingAnimation;
    protected Animation explodeAnimation;
    protected ProjectileState state;

    public Projectile(float X, float Y) {
        super(X, Y);
        Map map = GameScreen.world.getMap();
        this.texture = new Texture("tilesets/fx/explosion_small.png");
        this.splittedTextureAtlas = new TextureRegion(texture).split((int) map.getTileWidth(), (int) map.getTileHeight());
        createAnimations();
        state = FLYING;
    }

    protected void createAnimations() {
        flyingAnimation = new Animation(0.1f, Tools.extractAnimation(splittedTextureAtlas, 0, 3));
        explodeAnimation = new Animation(0.1f, Tools.extractAnimation(splittedTextureAtlas, 3, 4));
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public Animation getAnimation() {
        switch (state) {
            case FLYING:
                return flyingAnimation;
            case EXPLODING:
                return explodeAnimation;
            default:
                return flyingAnimation;
        }
    }
}
