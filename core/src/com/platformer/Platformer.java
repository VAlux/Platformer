package com.platformer;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.platformer.screens.GameScreen;

public class Platformer extends Game {

    public static final int WIDTH = 1280;
    public static final int HEIGHT = 960;
    public static final boolean DEBUG_INFO_ENABLED = true;

    private GameScreen gameScreen;
    private Stage stage;

    @Override
    public void create () {
        gameScreen = new GameScreen();
        setScreen(gameScreen);
    }

    @Override
    public void render () {
        super.render();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
