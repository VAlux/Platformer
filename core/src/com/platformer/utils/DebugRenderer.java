package com.platformer.utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.platformer.entities.Char;
import com.platformer.entities.Mob;
import com.platformer.entities.RenderableEntity;

public final class DebugRenderer implements Disposable {

    private ShapeRenderer renderer;
    private OrthographicCamera camera;

    public DebugRenderer(final OrthographicCamera camera) {
        this.camera = camera;
        renderer = new ShapeRenderer(256);
    }

    public void renderActorsBounds(final Array<RenderableEntity> entities) {
        renderer.setProjectionMatrix(camera.combined);
        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.setColor(Color.GREEN);
        for (RenderableEntity entity : entities) {
            final Rectangle actorBounds = entity.getBounds();
            renderer.rect(actorBounds.x, actorBounds.y, actorBounds.width, actorBounds.height);
        }
        renderer.end();
    }

    public void renderCollidableObjects(final Array<RectangleMapObject> objects) {
        renderer.setProjectionMatrix(camera.combined);
        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.setColor(Color.YELLOW);
        for (RectangleMapObject object : objects) {
            final Rectangle rectangle = object.getRectangle();
            renderer.rect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
        }
        renderer.end();
    }

    public void renderFOV(final Array<Char> aChars) {
        renderer.setProjectionMatrix(camera.combined);
        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.setColor(Color.RED);
        Mob mob;
        Rectangle fov;
        for (Char aChar : aChars) {
            if (aChar instanceof Mob) {
                mob = (Mob) aChar;
                fov = mob.getFOV();
                renderer.rect(fov.x, fov.y, fov.width, fov.height);
            }
        }
        renderer.end();
    }

    public void dispose() {
        renderer.dispose();
    }
}