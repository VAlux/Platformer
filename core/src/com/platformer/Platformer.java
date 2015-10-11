package com.platformer;

import com.badlogic.gdx.Game;
import com.platformer.screens.GameScreen;

public class Platformer extends Game {

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
