package com.platformer.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;

public class InventoryWindow {

    private Window window;
    private Window.WindowStyle windowStyle;
    private BitmapFont font;
    private Skin skin;
    private TextureAtlas atlas;

    public InventoryWindow() {
        font = new BitmapFont(Gdx.files.internal("fonts/font_white.fnt"));
        atlas = new TextureAtlas(Gdx.files.internal("ui/buttons/button.pack"));
        skin = new Skin(atlas);
        windowStyle = new Window.WindowStyle(font, Color.BLACK, skin.getDrawable("btndown"));
        window = new Window("Inventory", windowStyle);
        window.padTop(64);
        window.setCenterPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        window.setSize(256, 128);
    }

    public Window getWindow() {
        return window;
    }

    public void show(boolean visible) {
        window.setVisible(visible);
    }
}
