package com.platformer.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.platformer.Platformer;
import com.platformer.entities.RenderableEntity;
import com.platformer.entities.World;
import com.platformer.fx.FXRenderer;
import com.platformer.input.CharacterInputHandler;
import com.platformer.input.InputQueueProcessor;
import com.platformer.ui.HUD;
import com.platformer.utils.DebugRenderer;

public class GameScreen implements Screen {
    private static final String TAG = GameScreen.class.getSimpleName();

    /**
     * Sprite batch to draw all the stuff in the GameScreen.
     */
    public static SpriteBatch batch;

    /**
     * World actor, contains all of the other actors, which are present in the game.
     * We need it to be static, because it is needed in a many places in the game logic and different game
     * entities, so we don't need to pass it as an argument to every entity, which would mess up the code.
     */
    public static World world;

    /**
     * Processes the inputs, which are flowing into the input queue.
     */
    private InputQueueProcessor inputQueueProcessor;

    /**
     * Just renders the tiled map from the tmx file.
     */
    private OrthogonalTiledMapRenderer mapRenderer;

    /**
     * Renders different debug info, such as physics parameters, FOV, etc.
     */
    private DebugRenderer debugRenderer;

    /**
     * Main camera in the GameScreen
     */
    private OrthographicCamera camera;

    /**
     * Camera interpolation target. Needed to implement smooth camera following.
     */
    private Vector3 cameraLerpTarget;

    /**
     * Position of the player to be followed by camera.
     */
    private Vector3 playerPosition; // camera position interpolation needs vector3 instead of vector2.

    /**
     * Head-up display. Contains all of the stage2d ui elements.
     */
    private HUD hud;

    @Override
    public void show() {
        world = new World();
        world.init();
        inputQueueProcessor = new InputQueueProcessor();
        inputQueueProcessor.add(new CharacterInputHandler(world.getPlayer()));
        mapRenderer = new OrthogonalTiledMapRenderer(world.getMap().getTiledMap());
        batch = (SpriteBatch) mapRenderer.getSpriteBatch();
        camera = new OrthographicCamera();
        debugRenderer = new DebugRenderer(camera);
        playerPosition = new Vector3(world.getPlayer().getPosition(), 0);
        cameraLerpTarget = new Vector3(playerPosition);
        camera.position.set(playerPosition);
        hud = new HUD(mapRenderer.getSpriteBatch(), world.getPlayer());
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
        mapRenderer.renderTileLayer(world.getMap().getBackgroundLayer());
        renderActors();
        mapRenderer.renderTileLayer(world.getMap().getForegroundLayer());
        FXRenderer.render(delta);
        batch.end();

        if (Platformer.DEBUG_INFO_ENABLED)
            renderDebugInfo();

        hud.update();
        hud.render(delta);
    }

    private void updateCamera(final float delta) {
        playerPosition.set(world.getPlayer().getPosition(), 0);
        cameraLerpTarget.lerp(playerPosition, 3 * delta);
        //horizontal camera movement constraint
        if (cameraLerpTarget.x - camera.viewportWidth / 2 > world.getMap().getPosition().x &&
                cameraLerpTarget.x + camera.viewportWidth / 2 < world.getMap().getMapWidth()) {
            camera.position.x = cameraLerpTarget.x;
        }
        //vertical camera movement constraint
        if (cameraLerpTarget.y - camera.viewportHeight / 2 > world.getMap().getPosition().y &&
                cameraLerpTarget.y + camera.viewportHeight / 2 < world.getMap().getMapHeight()) {
            camera.position.y = cameraLerpTarget.y;
        }
        camera.update();
    }

    private void renderActors() {
        for (RenderableEntity actor : world.getRenderableActors()) {
            batch.draw(actor.getAnimation().getKeyFrame(actor.getStateTime(), true),
                       actor.getPosition().x,
                       actor.getPosition().y);
        }
    }
    
    private void renderDebugInfo(){
        debugRenderer.renderActorsBounds(world.getChars());
        debugRenderer.renderFOV(world.getChars());
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
        world.destroy();
        mapRenderer.dispose();
        hud.dispose();
        debugRenderer.dispose();
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