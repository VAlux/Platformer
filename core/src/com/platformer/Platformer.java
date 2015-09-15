package com.platformer;

import com.badlogic.gdx.Game;
import com.platformer.screens.GameScreen;

public class Platformer extends Game {

    public static final int WIDTH = 1024;
    public static final int HEIGHT = 768;
    public static final boolean DEBUG_INFO_ENABLED = true;

    @Override
    public void create () {
        setScreen(new GameScreen());
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
