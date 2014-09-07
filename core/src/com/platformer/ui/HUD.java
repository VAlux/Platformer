package com.platformer.ui;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.platformer.Platformer;
import com.platformer.entities.ActorStats;

public class HUD extends Group {

    private StatsBars bars;
    private Stage stage;
    private OrthographicCamera camera;
    private Table table;
    private Batch batch;

    public HUD(Batch batch, ActorStats stats) {
        this.batch = batch;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Platformer.WIDTH, Platformer.HEIGHT);
        stage = new Stage(new FillViewport(Platformer.WIDTH, Platformer.HEIGHT), batch);

        table = new Table();
        table.setFillParent(true);

        bars = new StatsBars(stats);
        table.left().top();
        table.add(bars);

        stage.addActor(table);
    }

    public void render(float delta) {
        bars.update();
        stage.act(delta);
        stage.draw();
    }
}
