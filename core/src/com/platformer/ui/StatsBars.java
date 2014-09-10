package com.platformer.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.platformer.stats.CharacterStats;

import static com.badlogic.gdx.scenes.scene2d.ui.ProgressBar.ProgressBarStyle;

public class StatsBars extends Table{

    private ProgressBar health;
    private ProgressBar energy;
    private ProgressBar experience;

    private ProgressBarStyle healthBarStyle;
    private ProgressBarStyle energyBarStyle;
    private ProgressBarStyle expBarStyle;

    private TextureAtlas atlas;
    private Skin skin;

    private CharacterStats stats;

    public StatsBars(CharacterStats stats) {
        super();
        this.stats = stats;
        atlas = new TextureAtlas(Gdx.files.internal("ui/bars/bars.pack"));
        skin = new Skin(atlas);
        this.setSkin(skin);

        TextureAtlas btnAtlas =new TextureAtlas(Gdx.files.internal("ui/buttons/button.pack"));
        Skin skinBG = new Skin(btnAtlas);
        this.background(skinBG.getDrawable("btndown"));

        healthBarStyle = new ProgressBarStyle(skin.getDrawable("barBack_horizontalMid"),
                                              skin.getDrawable("barRed_horizontalMid"));
        healthBarStyle.knobBefore = healthBarStyle.knob;

        energyBarStyle = new ProgressBarStyle(skin.getDrawable("barBack_horizontalMid"),
                                              skin.getDrawable("barBlue_horizontalMid"));
        energyBarStyle.knobBefore = energyBarStyle.knob;

        expBarStyle = new ProgressBarStyle(skin.getDrawable("barBack_horizontalMid"),
                                           skin.getDrawable("barGreen_horizontalMid"));
        expBarStyle.knobBefore = expBarStyle.knob;

        health = new ProgressBar(0, stats.maxHealth, 1, false, healthBarStyle);
        energy = new ProgressBar(0, stats.maxEnergy, 1, false, energyBarStyle);
        experience = new ProgressBar(0, 100, 1, false, expBarStyle);
        experience.setValue(100);

        add(health).padBottom(5).padLeft(5).padRight(5).row();
        add(energy).padBottom(5).padLeft(5).padRight(5).row();
        add(experience).padLeft(5);
    }

    public void update() {
        health.setValue(stats.health);
        energy.setValue(stats.energy);
        experience.setValue(stats.experience);
    }

    public void dispose() {
        skin.dispose();
        atlas.dispose();
    }
}
