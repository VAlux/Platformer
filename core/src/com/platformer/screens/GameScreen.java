package com.platformer.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.platformer.Platformer;
import com.platformer.entities.Mob;
import com.platformer.entities.World;
import com.platformer.fx.FXRenderer;
import com.platformer.input.CharacterInputHandler;
import com.platformer.input.InputQueueProcessor;
import com.platformer.ui.HUD;
import com.platformer.utils.DebugRenderer;

public class GameScreen implements Screen {
    private static final String TAG = GameScreen.class.getSimpleName();

    public static SpriteBatch batch;

    private InputQueueProcessor inputQueueProcessor;
    private World world;
    private OrthogonalTiledMapRenderer mapRenderer;
    private DebugRenderer debugRenderer;
    private OrthographicCamera camera;
    private Vector3 cameraLerpTarget;
    private Vector3 playerPosition; // camera position interpolation needs vector3 instead of vector2.
    private HUD hud;

    @Override
    public void show() {
        world = new World();
        inputQueueProcessor = new InputQueueProcessor();
        inputQueueProcessor.add(new CharacterInputHandler(world.player));
        mapRenderer = new OrthogonalTiledMapRenderer(world.map.getMap());
        batch = (SpriteBatch) mapRenderer.getSpriteBatch();
        camera = new OrthographicCamera();
        debugRenderer = new DebugRenderer(camera);
        playerPosition = new Vector3(world.player.getPosition(), 0);
        cameraLerpTarget = new Vector3(playerPosition);
        camera.position.set(playerPosition);
        hud = new HUD(mapRenderer.getSpriteBatch(), world.player);
    }

    /**
     * Rendering loop.
     * @param delta Time passed between frames in ms.
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        updateCamera(delta);
        mapRenderer.setView(camera);
        inputQueueProcessor.processInput();
        world.act(delta);

        batch.begin();
        mapRenderer.renderTileLayer(world.map.getBackgroundLayer());
        renderActors();
        mapRenderer.renderTileLayer(world.map.getForegroundLayer());
        FXRenderer.render(delta);
        batch.end();

        if (Platformer.DEBUG_INFO_ENABLED)
            renderDebugInfo();

        hud.update();
        hud.render(delta);
    }

    private void updateCamera(final float delta) {
        playerPosition.set(world.player.getPosition(), 0);
        cameraLerpTarget.lerp(playerPosition, 3 * delta);
        //horizontal camera movement constraint
        if (cameraLerpTarget.x - camera.viewportWidth / 2 > world.map.getPosition().x &&
                cameraLerpTarget.x + camera.viewportWidth / 2 < world.map.getMapWidth()) {
            camera.position.x = cameraLerpTarget.x;
        }
        //vertical camera movement constraint
        if (cameraLerpTarget.y - camera.viewportHeight / 2 > world.map.getPosition().y &&
                cameraLerpTarget.y + camera.viewportHeight / 2 < world.map.getMapHeight()) {
            camera.position.y = cameraLerpTarget.y;
        }
        camera.update();
    }

    private void renderActors() {
        batch.draw(world.player.getAnimation().getKeyFrame(world.player.getStateTime(), true),
                   world.player.getPosition().x,
                   world.player.getPosition().y);

        for (Mob mob : world.mobs) {
            batch.draw(mob.getAnimation().getKeyFrame(mob.getStateTime(), true),
                       mob.getPosition().x,
                       mob.getPosition().y);
        }
    }
    
    private void renderDebugInfo(){
        debugRenderer.renderActorsBounds(world.chars);
        debugRenderer.renderFOV(world.chars);
    }

    @Override
    public void resize(int width, int height) {
        hud.resize(width, height);
        camera.viewportWidth = width / 2;
        camera.viewportHeight = height / 2;
        updateCamera(Gdx.graphics.getDeltaTime());
    }

    @Override
    public void dispose() {
        world.dispose();
        mapRenderer.dispose();
        hud.dispose();
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