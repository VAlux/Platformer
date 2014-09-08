package com.platformer.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.platformer.entities.Player;

import static com.platformer.Platformer.DEBUG_INFO_ENABLED;

public class HUD extends InputAdapter {

    private StatsBars bars;
    private Stage stage;
    private Table table;
    private InventoryWindow inventoryWindow;
    private InputMultiplexer inputMultiplexer;
    private Player player;
    private DebugInfo debugInfo;

    public HUD(Batch batch, Player player) {
        this.player = player;
        inventoryWindow = new InventoryWindow();
        debugInfo = new DebugInfo(player);

        stage = new Stage(new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()), batch);

        table = new Table();
        table.setFillParent(true);
        table.debug();
        table.debugTable();

        bars = new StatsBars(player.getStats());
        table.left().top();
        table.add(bars);

        if (DEBUG_INFO_ENABLED){
            table.row();
            table.add(debugInfo).left();
        }

        stage.addActor(table);
        stage.addActor(inventoryWindow.getWindow());

        inputMultiplexer = new InputMultiplexer(this, player, stage);

        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    @Override
    public boolean keyTyped(char character) {
        if (character == 'i')
            inventoryWindow.show(!player.isActiveInventory());
        return false;
    }

    public void render(float delta) {
        stage.act(delta);
        stage.draw();
        Table.drawDebug(stage);
    }

    public void update(){
        bars.update();

        if (DEBUG_INFO_ENABLED)
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
