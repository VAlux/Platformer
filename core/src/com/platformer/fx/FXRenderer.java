package com.platformer.fx;

import com.platformer.screens.GameScreen;

import java.util.ArrayList;

public class FXRenderer {

    private static ArrayList<FX> effects;

    static {
        effects = new ArrayList<FX>(512);
    }

    public static void addEffect(FX fx) {
        effects.add(fx);
    }

    public static void render() {
        for (FX fx : effects) {
            if (fx.isRunning) {
                GameScreen.batch.draw(fx.getAnimation().getKeyFrame(fx.getStateTime(), false),
                                      fx.getPosition().x,
                                      fx.getPosition().y);
            }
        }
    }
}