package com.platformer.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.platformer.entities.Actor;
import com.platformer.entities.ActorStats;

public class DebugInfo extends Table {

    private BitmapFont font;
    private Label lblStats;
    private Label lblPosition;
    private Label lblFPS;
    private ActorStats stats;
    private Actor actor;

    public DebugInfo(final Actor actor) {
        this.actor = actor;
        stats = actor.getStats();
        font = new BitmapFont(Gdx.files.internal("fonts/font_white.fnt"));
        font.setScale(.8f);

        final Label.LabelStyle statsLabelStyle = new Label.LabelStyle(font, Color.WHITE);
        final Label.LabelStyle posLabelStyle = new Label.LabelStyle(font, Color.GREEN);
        final Label.LabelStyle fpsLabelStyle = new Label.LabelStyle(font, Color.MAGENTA);

        lblStats = new Label(stats.toString(), statsLabelStyle);
        lblStats.setWrap(true);
        lblPosition = new Label("PosX: " + actor.getPosition().x + "\nPosY: " + actor.getPosition().y, posLabelStyle);
        lblPosition.setWrap(true);
        lblFPS = new Label("FPS: " + String.valueOf(Gdx.graphics.getFramesPerSecond()), fpsLabelStyle);
        lblFPS.setWrap(true);

        this.debug();
        this.debugTable();
        this.add(lblStats).row();
        this.add(lblPosition).row();
        this.add(lblFPS);
    }

    public void update(){
        lblStats.setText(stats.toString());
        lblPosition.setText("PosX: " + actor.getPosition().x + "\nPosY: " + actor.getPosition().y);
        lblFPS.setText("FPS: " + String.valueOf(Gdx.graphics.getFramesPerSecond()));
    }
}
