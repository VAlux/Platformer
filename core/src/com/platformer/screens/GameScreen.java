package com.platformer.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.platformer.utils.DebugRenderer;
import com.platformer.entities.Actor;
import com.platformer.entities.characters.Elf;
import com.platformer.entities.enemies.SeekerMob;
import com.platformer.exceptions.MapLayerNotFoundException;
import com.platformer.exceptions.MapObjectNotFoundException;
import com.platformer.maps.Map;

import java.util.ArrayList;

public class GameScreen implements Screen {

    private Map map;
    private Actor player;
    private Actor mob;
    private OrthogonalTiledMapRenderer mapRenderer;
    private OrthographicCamera camera;
    private Vector3 cameraLerpTarget;
    private Vector3 playerPosition; // camera position interpolation needs vector3 instead of vector2.
    private FPSLogger fpsLogger;

    private ArrayList<Actor> players;
    private ArrayList<Actor> actors;
    private DebugRenderer debugRenderer;

    @Override

    public void show() {
        try {
            map = new Map("maps/greece.tmx");
        } catch (MapObjectNotFoundException e) {
            e.printStackTrace();
            Gdx.app.exit();
        } catch (MapLayerNotFoundException e) {
            e.printStackTrace();
            Gdx.app.exit();
        }

        mapRenderer = new OrthogonalTiledMapRenderer(map.getMap());
        players = new ArrayList<Actor>();
        actors = new ArrayList<Actor>();

        camera = new OrthographicCamera();
        debugRenderer = new DebugRenderer(camera);
        player = new Elf(map);
        players.add(player);

        mob = new SeekerMob(map, new Vector2(27.0f * 32.0f, 8.0f * 32.0f), players);
        actors.add(player);
        actors.add(mob);
        playerPosition = new Vector3(player.getPosition(), 0);
        cameraLerpTarget = new Vector3(playerPosition);
        camera.position.set(playerPosition);
        fpsLogger = new FPSLogger();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        updateCamera(delta);

        player.act(delta);
        mob.act(delta);

        mapRenderer.setView(camera);
        mapRenderer.getSpriteBatch().begin();
        mapRenderer.renderTileLayer(map.getBackgroundLayer());
        renderActors();
        mapRenderer.renderTileLayer(map.getForegroundLayer());
        mapRenderer.getSpriteBatch().end();

        renderDebugInfo();

        fpsLogger.log();
    }

    private void updateCamera(final float delta) {
        playerPosition.set(player.getPosition(), 0);
        cameraLerpTarget.lerp(playerPosition, 3 * delta);
        if (cameraLerpTarget.x - camera.viewportWidth / 2 > map.getPosition().x &&
                cameraLerpTarget.x + camera.viewportWidth / 2 < map.getMapWidth()) {
            camera.position.x = cameraLerpTarget.x;
        }
        if (cameraLerpTarget.y - camera.viewportHeight / 2 > map.getPosition().y &&
                cameraLerpTarget.y + camera.viewportHeight / 2 < map.getMapHeight()) {
            camera.position.y = cameraLerpTarget.y;
        }
        camera.update();
    }

    private void renderActors() {
        mapRenderer.getSpriteBatch().draw(player.getCurrentAnimation().getKeyFrame(player.getStateTime(), true),
                player.getPosition().x, player.getPosition().y);

        mapRenderer.getSpriteBatch().draw(mob.getCurrentAnimation().getKeyFrame(mob.getStateTime(), true),
                mob.getPosition().x, mob.getPosition().y);
    }

    private void renderDebugInfo(){
        debugRenderer.renderActorsBounds(actors);
        debugRenderer.renderFOV(actors);
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width / 2;
        camera.viewportHeight = height / 2;
        updateCamera(Gdx.graphics.getDeltaTime());
    }

    @Override
    public void dispose() {
        map.dispose();
        player.dispose();
        mapRenderer.dispose();
        Gdx.app.log("GameScreen", "game disposed");
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }
}