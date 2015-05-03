package com.platformer.entities.projectiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.platformer.entities.RenderableEntity;
import com.platformer.screens.GameScreen;
import com.platformer.states.ProjectileState;
import com.platformer.utils.Tools;

import static com.badlogic.gdx.graphics.g2d.Animation.PlayMode.NORMAL;
import static com.platformer.states.ProjectileState.FLYING;

public class Projectile extends RenderableEntity {

    protected Animation flyingAnimation;
    protected Animation explodeAnimation;
    protected ProjectileState state;

    public Projectile(float X, float Y) {
        super(X, Y);
        this.texture = new Texture("tilesets/fx/explosion_small.png");
        this.splittedTextureAtlas = new TextureRegion(texture).split(60, 60);
        createAnimations();
        state = FLYING;
    }

    public Projectile(Vector2 position) {
        this(position.x, position.y);
    }

    protected void createAnimations() {
        flyingAnimation = new Animation(0.1f, Tools.extractAnimation(splittedTextureAtlas, 0, 5));
        explodeAnimation = new Animation(0.1f, Tools.extractAnimation(splittedTextureAtlas, 3, 4));
        flyingAnimation.setPlayMode(NORMAL);
        explodeAnimation.setPlayMode(NORMAL);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        super.move(delta);
        if (hasCollision()) {
            destroy();
        }
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

    @Override
    public void destroy() {
        super.destroy();
        GameScreen.world.removeActor(this);
    }
}
