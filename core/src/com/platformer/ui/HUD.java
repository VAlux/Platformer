package com.platformer.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.platformer.entities.Char;

import static com.platformer.Constants.PROP_DEBUG_INFO_ENABLED;

public class HUD extends InputAdapter {

    private StatsBars bars;
    private Stage stage;
    private DebugInfo debugInfo;

    public HUD(Batch batch, Char player) {
        debugInfo = new DebugInfo(player);

        stage = new Stage(new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()), batch);

        Table table = new Table();
        table.setFillParent(true);

        bars = new StatsBars(player.getStats());
        table.left().top();
        table.add(bars);

        if (PROP_DEBUG_INFO_ENABLED){
            table.debug();
            table.debugTable();
            table.row();
            table.add(debugInfo).left();
        }

        stage.addActor(table);
    }

    public void render(float delta) {
        stage.act(delta);
        stage.draw();
    }

    public void update(){
        bars.update();

        if (PROP_DEBUG_INFO_ENABLED)
            debugInfo.update();
    }

    public void resize(int width, int height){
        stage.getViewport().update(width, height);
    }

    public void dispose() {
        stage.dispose();
        bars.dispose();
    }
}