package com.platformer.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.platformer.Platformer;
import com.platformer.entities.Actor;
import com.platformer.entities.Mob;

import java.util.ArrayList;

public class DebugRenderer {

    private ShapeRenderer renderer;
    private BitmapFont font;
    private OrthographicCamera camera;
    private SpriteBatch batch;

    public DebugRenderer(final OrthographicCamera camera) {
        renderer = new ShapeRenderer(256);
        this.camera = camera;
        font = new BitmapFont(Gdx.files.internal("fonts/font_white.fnt"), false);
        batch = new SpriteBatch();
    }

    public void renderActorsBounds(final ArrayList<Actor> actors) {
        renderer.setProjectionMatrix(camera.combined);
        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.setColor(Color.GREEN);
        Rectangle actorBounds;
        for (Actor actor : actors) {
            actorBounds = actor.getBounds();
            renderer.rect(actorBounds.x, actorBounds.y, actorBounds.width, actorBounds.height);
        }
        renderer.end();
    }

    public void renderFOV(final ArrayList<Actor> actors) {
        renderer.setProjectionMatrix(camera.combined);
        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.setColor(Color.RED);
        Mob mob;
        Rectangle fov;
        for (Actor actor : actors) {
            if (actor instanceof Mob) {
                mob = (Mob) actor;
                fov = mob.getFOV();
                renderer.rect(fov.x, fov.y, fov.width, fov.height);
            }
        }
        renderer.end();
    }


    public void renderStats(final Actor actor) {
        batch.begin();
        font.setScale(.5f);
        font.drawMultiLine(batch, actor.getStats().toString(), 0, Platformer.HEIGHT);
        batch.end();
    }
}