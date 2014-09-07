package com.platformer.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.platformer.entities.ActorStats;

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

    private ActorStats stats;

    public StatsBars(ActorStats stats) {
        super();
        this.stats = stats;
        atlas = new TextureAtlas(Gdx.files.internal("ui/bars/bars.pack"));
        skin = new Skin(atlas);
        this.setSkin(skin);

        healthBarStyle = new ProgressBarStyle(skin.getDrawable("barBack_horizontalMid"), skin.getDrawable("barRed_horizontalMid"));
        healthBarStyle.knobBefore = healthBarStyle.knob;

        energyBarStyle = new ProgressBarStyle(skin.getDrawable("barBack_horizontalMid"), skin.getDrawable("barBlue_horizontalMid"));
        energyBarStyle.knobBefore = energyBarStyle.knob;

        expBarStyle = new ProgressBarStyle(skin.getDrawable("barBack_horizontalMid"), skin.getDrawable("barGreen_horizontalMid"));
        expBarStyle.knobBefore = expBarStyle.knob;

        health = new ProgressBar(0, stats.maxHealth, 1, false, healthBarStyle);
        energy = new ProgressBar(0, stats.maxEnergy, 1, false, energyBarStyle);
        experience = new ProgressBar(0, 100, 1, false, expBarStyle);

        add(health).padBottom(5).padLeft(5).padTop(5).row();
        add(energy).padBottom(5).padLeft(5).row();
        add(experience).padLeft(5);
    }

    public void update() {
        health.setValue(stats.health);
        energy.setValue(stats.energy);
        experience.setValue(stats.experience);
    }
}
