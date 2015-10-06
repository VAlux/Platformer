package com.platformer.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.platformer.entities.Char;
import com.platformer.screens.GameScreen;
import com.platformer.stats.CharacterStats;

public class DebugInfo extends Table {

    private Label lblStats;
    private Label lblPosition;
    private Label lblFPS;
    private Label lblIsOnGround;
    private Label lblActorsAmount;
    private CharacterStats stats;
    private Char aChar;

    public DebugInfo(final Char aChar) {
        this.aChar = aChar;
        stats = aChar.getStats();
        BitmapFont font = new BitmapFont(Gdx.files.internal("fonts/font_white.fnt"));
        font.getData().setScale(.5f);

        final Label.LabelStyle statsLabelStyle = new Label.LabelStyle(font, Color.WHITE);
        final Label.LabelStyle posLabelStyle = new Label.LabelStyle(font, Color.GREEN);
        final Label.LabelStyle fpsLabelStyle = new Label.LabelStyle(font, Color.MAGENTA);
        final Label.LabelStyle iogLabelStyle = new Label.LabelStyle(font, Color.YELLOW);
        final Label.LabelStyle acmLabelStyle = new Label.LabelStyle(font, Color.BLUE);

        lblStats = new Label(stats.toString(), statsLabelStyle);
        lblPosition = new Label("PosX: " + aChar.getPosition().x + "\nPosY: " + aChar.getPosition().y, posLabelStyle);
        lblFPS = new Label("FPS: " + String.valueOf(Gdx.graphics.getFramesPerSecond()), fpsLabelStyle);
        lblIsOnGround = new Label("Is On Ground: " + String.valueOf(aChar.isOnGround()), iogLabelStyle);
        lblActorsAmount= new Label("Actors amount: " + String.valueOf(GameScreen.world.getActors().size()), acmLabelStyle);

        lblFPS.setWrap(true);
        lblStats.setWrap(true);
        lblPosition.setWrap(true);
        lblIsOnGround.setWrap(true);
        lblActorsAmount.setWrap(true);

        this.debug();
        this.debugTable();
        this.add(lblStats).row();
        this.add(lblActorsAmount).row();
        this.add(lblPosition).row();
        this.add(lblIsOnGround).row();
        this.add(lblFPS);
    }

    public void update(){
        lblStats.setText(stats.toString());
        lblPosition.setText("PosX: " + aChar.getPosition().x + "\nPosY: " + aChar.getPosition().y);
        lblIsOnGround.setText("Is On Ground: " + String.valueOf(aChar.isOnGround()));
        lblFPS.setText("FPS: " + String.valueOf(Gdx.graphics.getFramesPerSecond()));
        lblActorsAmount.setText("Actors amount: " + String.valueOf(GameScreen.world.getActors().size()));
    }
}
