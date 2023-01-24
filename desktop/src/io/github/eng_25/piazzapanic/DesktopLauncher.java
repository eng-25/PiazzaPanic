package io.github.eng_25.piazzapanic;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import io.github.eng_25.piazzapanic.PiazzaPanic;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.useVsync(false);
		//config.setWindowIcon(..);
		//config.setFullscreenMode(Lwjgl3ApplicationConfiguration.getDisplayMode());
		config.setWindowedMode(PiazzaPanic.DEFAULT_WIDTH, PiazzaPanic.DEFAULT_HEIGHT);
		config.setTitle("Piazza Panic!");
		new Lwjgl3Application(new PiazzaPanic(), config);
	}
}
