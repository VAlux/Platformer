package com.platformer.fx;

import com.badlogic.gdx.Gdx;
import com.platformer.screens.GameScreen;

import java.util.concurrent.CopyOnWriteArrayList;

public class FXRenderer {

    private static CopyOnWriteArrayList<FX> effects;

    static {
        effects = new CopyOnWriteArrayList<FX>();
    }

    public static void addEffect(FX fx) {
        effects.add(fx);
        Gdx.app.log("FXRenderer", "New effect added [" + fx.toString() + "] effects amount: " + effects.size());
    }

    public static void render(final float delta) {
        for (FX fx : effects) {
            fx.act(delta);
            if (fx.isRunning) {
                GameScreen.batch.draw(fx.getAnimation().getKeyFrame(fx.getStateTime(), false),
                                      fx.getPosition().x,
                                      fx.getPosition().y);
            } else {
                effects.remove(fx);
                Gdx.app.log("FXRenderer", "Effect removed: [" + fx.toString() + "] effects amount: " + effects.size());
            }
        }
    }
}