package com.platformer.utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.platformer.entities.Character;
import com.platformer.entities.Mob;

import java.util.ArrayList;

public class DebugRenderer {

    private ShapeRenderer renderer;
    private OrthographicCamera camera;

    public DebugRenderer(final OrthographicCamera camera) {
        this.camera = camera;
        renderer = new ShapeRenderer(256);
    }

    public void renderActorsBounds(final ArrayList<Character> characters) {
        renderer.setProjectionMatrix(camera.combined);
        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.setColor(Color.GREEN);
        Rectangle actorBounds;
        for (Character character : characters) {
            actorBounds = character.getBounds();
            renderer.rect(actorBounds.x, actorBounds.y, actorBounds.width, actorBounds.height);
        }
        renderer.end();
    }

    public void renderFOV(final ArrayList<Character> characters) {
        renderer.setProjectionMatrix(camera.combined);
        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.setColor(Color.RED);
        Mob mob;
        Rectangle fov;
        for (Character character : characters) {
            if (character instanceof Mob) {
                mob = (Mob) character;
                fov = mob.getFOV();
                renderer.rect(fov.x, fov.y, fov.width, fov.height);
            }
        }
        renderer.end();
    }
}