package com.platformer.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.platformer.entities.Character;
import com.platformer.stats.CharacterStats;

public class DebugInfo extends Table {

    private Label lblStats;
    private Label lblPosition;
    private Label lblFPS;
    private Label lblIsOnGround;
    private CharacterStats stats;
    private Character character;

    public DebugInfo(final Character character) {
        this.character = character;
        stats = character.getStats();
        BitmapFont font = new BitmapFont(Gdx.files.internal("fonts/font_white.fnt"));
        font.setScale(.6f);

        final Label.LabelStyle statsLabelStyle = new Label.LabelStyle(font, Color.WHITE);
        final Label.LabelStyle posLabelStyle = new Label.LabelStyle(font, Color.GREEN);
        final Label.LabelStyle fpsLabelStyle = new Label.LabelStyle(font, Color.MAGENTA);
        final Label.LabelStyle iogLabelStyle = new Label.LabelStyle(font, Color.YELLOW);

        lblStats = new Label(stats.toString(), statsLabelStyle);
        lblPosition = new Label("PosX: " + character.getPosition().x + "\nPosY: " + character.getPosition().y, posLabelStyle);
        lblFPS = new Label("FPS: " + String.valueOf(Gdx.graphics.getFramesPerSecond()), fpsLabelStyle);
        lblIsOnGround = new Label("Is On Ground: " + String.valueOf(character.isOnGround()), iogLabelStyle);

        lblFPS.setWrap(true);
        lblStats.setWrap(true);
        lblPosition.setWrap(true);
        lblIsOnGround.setWrap(true);

        this.debug();
        this.debugTable();
        this.add(lblStats).row();
        this.add(lblPosition).row();
        this.add(lblIsOnGround).row();
        this.add(lblFPS);
    }

    public void update(){
        lblStats.setText(stats.toString());
        lblPosition.setText("PosX: " + character.getPosition().x + "\nPosY: " + character.getPosition().y);
        lblIsOnGround.setText("Is On Ground: " + String.valueOf(character.isOnGround()));
        lblFPS.setText("FPS: " + String.valueOf(Gdx.graphics.getFramesPerSecond()));
    }
}
