package com.platformer.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.platformer.Constants;
import com.platformer.Platformer;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.resizable = false;
        config.useGL30 = true;
        config.width = Constants.PROP_GAME_WIDTH;
        config.height = Constants.PROP_GAME_HEIGHT;
        config.vSyncEnabled = true;
        new LwjglApplication(new Platformer(), config);
	}
}
