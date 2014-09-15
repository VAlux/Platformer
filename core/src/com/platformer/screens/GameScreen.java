package com.platformer.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.platformer.Platformer;
import com.platformer.entities.World;
import com.platformer.ui.HUD;
import com.platformer.utils.DebugRenderer;

public class GameScreen implements Screen {
    private static final String TAG = GameScreen.class.getSimpleName();

    private World world;
    private OrthogonalTiledMapRenderer mapRenderer;
    private OrthographicCamera camera;
    private Vector3 cameraLerpTarget;
    private Vector3 playerPosition; // camera position interpolation needs vector3 instead of vector2.
    private HUD hud;
    private DebugRenderer debugRenderer;

    @Override
    public void show() {
        world = new World();
        mapRenderer = new OrthogonalTiledMapRenderer(world.map.getMap());
        camera = new OrthographicCamera();
        debugRenderer = new DebugRenderer(camera);
        playerPosition = new Vector3(world.player.getPosition(), 0);
        cameraLerpTarget = new Vector3(playerPosition);
        camera.position.set(playerPosition);
        hud = new HUD(mapRenderer.getSpriteBatch(), world.player);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        updateCamera(delta);

        world.act(delta);

        mapRenderer.setView(camera);
        mapRenderer.getSpriteBatch().begin();
        mapRenderer.renderTileLayer(world.map.getBackgroundLayer());
        renderActors();
        mapRenderer.renderTileLayer(world.map.getForegroundLayer());
        mapRenderer.getSpriteBatch().end();

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
        mapRenderer.getSpriteBatch().draw(world.player.getCurrentAnimation().getKeyFrame(world.player.getStateTime(), true),
                                          world.player.getPosition().x,
                                          world.player.getPosition().y);


        mapRenderer.getSpriteBatch().draw(world.mob.getCurrentAnimation().getKeyFrame(world.mob.getStateTime(), true),
                                          world.mob.getPosition().x,
                                          world.mob.getPosition().y);

    }
    
    private void renderDebugInfo(){
        debugRenderer.renderActorsBounds(world.characters);
        debugRenderer.renderFOV(world.characters);
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