package com.platformer.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.platformer.entities.Actor;
import com.platformer.entities.Character;
import com.platformer.entities.characters.Elf;
import com.platformer.entities.enemies.SeekerMob;
import com.platformer.exceptions.MapLayerNotFoundException;
import com.platformer.exceptions.MapObjectNotFoundException;
import com.platformer.items.ItemsPool;
import com.platformer.maps.Map;
import com.platformer.ui.HUD;
import com.platformer.utils.DebugRenderer;

import java.util.ArrayList;

public class GameScreen implements Screen {
    private static final String TAG = GameScreen.class.getSimpleName();

    private Map map;
    private Character player;
    private Character mob;
    private OrthogonalTiledMapRenderer mapRenderer;
    private OrthographicCamera camera;
    private Vector3 cameraLerpTarget;
    private Vector3 playerPosition; // camera position interpolation needs vector3 instead of vector2.
    private FPSLogger fpsLogger;
    private HUD hud;

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
        ItemsPool.initPool();
        mapRenderer = new OrthogonalTiledMapRenderer(map.getMap());
        players = new ArrayList<Actor>();
        actors = new ArrayList<Actor>();

        camera = new OrthographicCamera();

        debugRenderer = new DebugRenderer(camera);
        player = new Elf(map, 10);
        players.add(player);
        /// TODO: test picking items
        player.pickItem(ItemsPool.IDs.SCOUT_SWORD, 1);
        player.pickItem(ItemsPool.IDs.STONE_RING, 3);
        player.pickItem(ItemsPool.IDs.ICE_SWORD, 1);
        player.pickItem(ItemsPool.IDs.JET_PACK, 1);
        player.pickItem(ItemsPool.IDs.HEAL_POTION, 5);

        mob = new SeekerMob(map, new Vector2(27.0f * 32.0f, 8.0f * 32.0f), players);
        actors.add(player);
        actors.add(mob);
        playerPosition = new Vector3(player.getPosition(), 0);
        cameraLerpTarget = new Vector3(playerPosition);
        camera.position.set(playerPosition);

        hud = new HUD(mapRenderer.getSpriteBatch(), player.getStats());
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
        hud.render(delta);

        //fpsLogger.log();
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

        debugRenderer.renderStats(player.getStats());
        debugRenderer.renderInventory(player);
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
        Gdx.app.log(TAG, "game disposed");
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