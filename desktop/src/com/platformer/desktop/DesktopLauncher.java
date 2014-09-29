package com.platformer.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.platformer.Platformer;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.resizable = false;
        config.useGL30 = false;
        config.width = Platformer.WIDTH;
        config.height = Platformer.HEIGHT;
        new LwjglApplication(new Platformer(), config);
	}
}
