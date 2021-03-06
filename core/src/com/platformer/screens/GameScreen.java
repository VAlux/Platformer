package com.platformer.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.platformer.Constants;
import com.platformer.entities.RenderableEntity;
import com.platformer.entities.World;
import com.platformer.input.CharacterGestureInputHandler;
import com.platformer.input.CharacterInputHandler;
import com.platformer.input.InputQueueProcessor;
import com.platformer.maps.ParallaxLayer;
import com.platformer.ui.HUD;
import com.platformer.utils.DebugRenderer;

import static com.platformer.Constants.*;

public class GameScreen implements Screen {

    /**
     * World actor, contains all of the other actors, which are present in the game.
     * We need it to be static, because it is needed in a many places in the game logic and different game
     * entities, so we don't need to pass it as an argument to every entity, which would mess up the code.
     */
    public static World world;

    /**
     * Sprite batch to draw all the stuff in the GameScreen.
     */
    public static SpriteBatch batch;

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
    private OrthographicCamera mainCamera;

    /**
     * Multiple cameras for parallax rendering.
     */
    private Array<OrthographicCamera> parallaxCameras;

    /**
     * Camera interpolation target. Needed to implement smooth mainCamera following.
     */
    private Vector3 cameraLerpTarget;

    /**
     * Position of the player to be followed by mainCamera.
     */
    private Vector3 playerPosition; // mainCamera position interpolation needs vector3 instead of vector2.

    /**
     * Head-up display. Contains all of the stage2d ui elements.
     */
    private HUD hud;

    /**
     * Reference to the map's parallax tile layer.
     */
    private ParallaxLayer parallaxLayer;

    @Override
    public void show() {
        world = new World();
        world.init();
        initInputHandling();
        mapRenderer = new OrthogonalTiledMapRenderer(world.getMap().getTiledMap());
        batch = (SpriteBatch) mapRenderer.getBatch();
        mainCamera = new OrthographicCamera();
        debugRenderer = new DebugRenderer(mainCamera);
        playerPosition = new Vector3(world.getPlayer().getPosition(), 0);
        cameraLerpTarget = new Vector3(playerPosition);
        mainCamera.position.set(playerPosition);
        initParallaxCameras();
        hud = new HUD(mapRenderer.getBatch(), world.getPlayer());
    }

    /**
     * Initialise all, that related to the user input.
     */
    private void initInputHandling() {
        final CharacterGestureInputHandler gestureInputHandler = new CharacterGestureInputHandler(world.getPlayer());
        final CharacterInputHandler characterInputHandler = new CharacterInputHandler(world.getPlayer());
        inputQueueProcessor = new InputQueueProcessor();

        // keyboard
        inputQueueProcessor.addInputHandler(characterInputHandler);
        inputQueueProcessor.addInputProcessor(characterInputHandler);

        // custom handling of gestures
        inputQueueProcessor.addInputHandler(gestureInputHandler);
        // standard gesture handling. (tap, pinch, zoom, etc.)
        inputQueueProcessor.addInputProcessor(new GestureDetector(gestureInputHandler));
    }

    /**
     * Rendering loop.
     * Rendering in 4(5) phases:
     * 1: render background layer.
     * 2: render actors.
     * 3: render foreground layer.
     * 4(optional): render debug info.
     * 5: render hud.
     * @param delta Time passed between frames in ms.
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.5f, 0.8f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        updateCamera(mainCamera, delta, GM_DEF_CAMERA_LERP_FACTOR);
        mapRenderer.setView(mainCamera);
        inputQueueProcessor.processInput();
        world.act(delta);

        batch.begin();
        for (TiledMapTileLayer parallaxLayer : world.getMap().getParallaxBackground().getLayers()) {
            mapRenderer.renderTileLayer(parallaxLayer);
        }
        mapRenderer.renderTileLayer(world.getMap().getBackgroundLayer());
        renderActors();
        mapRenderer.renderTileLayer(world.getMap().getForegroundLayer());
        batch.end();

//        GenericTools.printArrayAsMatrix(MapTools.getTilesAroundActor(world.getPlayer(), 5, 5), 10);

        if (Constants.PROP_DEBUG_INFO_ENABLED)
            renderDebugInfo();

        hud.update();
        hud.render(delta);
    }

    private boolean checkCameraHorizontalConstraint(final Vector3 constraint) {
        return  constraint.x - mainCamera.viewportWidth / 2 > world.getMap().getPosition().x &&
                constraint.x + mainCamera.viewportWidth / 2 < world.getMap().getMapWidth();
    }

    private boolean checkCameraVerticalConstraint(final Vector3 constraint) {
        return  constraint.y - mainCamera.viewportHeight / 2 > world.getMap().getPosition().y &&
                constraint.y + mainCamera.viewportHeight / 2 < world.getMap().getMapHeight();
    }

    private void initParallaxCameras() {
        parallaxLayer = world.getMap().getParallaxBackground();
        final int parallaxLayersAmount = parallaxLayer.getLayersAmount();
        parallaxCameras = new Array<>(parallaxLayersAmount);
        for (int i = 0; i < parallaxLayersAmount; i++) {
            parallaxCameras.add(new OrthographicCamera(PROP_GAME_WIDTH, PROP_GAME_HEIGHT));
        }
    }

    private void updateCamera(OrthographicCamera camera, float delta, float interpolationFactor) {
        playerPosition.set(world.getPlayer().getPosition(), 0);
        cameraLerpTarget.lerp(playerPosition, interpolationFactor * delta);
        if (checkCameraHorizontalConstraint(cameraLerpTarget)) {
            camera.position.x = cameraLerpTarget.x;
        }
        if (checkCameraVerticalConstraint(cameraLerpTarget)) {
            camera.position.y = cameraLerpTarget.y;
        }
        camera.update();
    }

    private void renderActors() {
        for (RenderableEntity actor : world.getRenderableActors()) {
            batch.draw(actor.getAnimation().getKeyFrame(actor.getStateTime(), true),
                       actor.getVisualPosition().x,
                       actor.getVisualPosition().y);
        }
    }
    
    private void renderDebugInfo(){
        debugRenderer.renderActorsBounds(world.getRenderableActors());
        debugRenderer.renderFOV(world.getChars());
        debugRenderer.renderCollidableObjects(world.getMap().getMapCollidables());
    }

    @Override
    public void resize(int width, int height) {
        hud.resize(width, height);
        mainCamera.viewportWidth = width * PROP_GAME_SCREEN_SCALE_FACTOR;
        mainCamera.viewportHeight = height * PROP_GAME_SCREEN_SCALE_FACTOR;
        updateCamera(mainCamera, Gdx.graphics.getDeltaTime(), GM_DEF_CAMERA_LERP_FACTOR);
    }

    @Override
    public void dispose() {
        world.destroy();
        mapRenderer.dispose();
        hud.dispose();
        debugRenderer.dispose();
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