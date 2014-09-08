package com.platformer.utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.platformer.entities.Actor;
import com.platformer.entities.Mob;

import java.util.ArrayList;

public class DebugRenderer {

    private ShapeRenderer renderer;
    private OrthographicCamera camera;

    public DebugRenderer(final OrthographicCamera camera) {
        this.camera = camera;
        renderer = new ShapeRenderer(256);
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
}