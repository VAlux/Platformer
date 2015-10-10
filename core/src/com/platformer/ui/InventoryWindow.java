package com.platformer.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;

/**
 * Redundant useless class.
 * It is here just because I will need it in the future.
 */
public class InventoryWindow {

    private Window window;

    public InventoryWindow() {
        BitmapFont font = new BitmapFont(Gdx.files.internal("fonts/font_white.fnt"));
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("ui/buttons/button.pack"));
        Skin skin = new Skin(atlas);
        Window.WindowStyle windowStyle = new Window.WindowStyle(font, Color.BLACK, skin.getDrawable("btndown"));
        window = new Window("Inventory", windowStyle);
        window.padTop(64);
        window.setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        window.setSize(256, 128);
    }

    public Window getWindow() {
        return window;
    }

    public void show(boolean visible) {
        window.setVisible(visible);
    }
}
