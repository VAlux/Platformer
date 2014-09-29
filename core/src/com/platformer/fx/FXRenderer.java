package com.platformer.fx;

import com.platformer.screens.GameScreen;

import java.util.ArrayList;

public class FXRenderer {

    private static ArrayList<FX> fxes;

    static {
        fxes = new ArrayList<FX>(512);
    }

    public static void addEffect(FX fx) {
        fxes.add(fx);
    }

    public static void render(final float delta) {
        for (FX fx : fxes) {
            if (fx.isRunning) {
                GameScreen.batch.draw(fx.getAnimation().getKeyFrame(fx.getStateTime(), false),
                                      fx.getPosition().x,
                                      fx.getPosition().y);
            }
        }
    }
}