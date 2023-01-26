package io.github.eng_25.piazzapanic;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.eng_25.piazzapanic.screen.ScreenMenu;
import io.github.eng_25.piazzapanic.util.ResourceManager;

public class PiazzaPanic extends Game {

    // default values for window's height and width
    public static final int DEFAULT_WIDTH = 1920;
    public static final int DEFAULT_HEIGHT = 1080;

    private SpriteBatch batch;
    private ResourceManager resourceManager;
    private ScreenMenu menuScreen;
    private boolean isMuted;

    public void toggleMuted() {
        isMuted = !isMuted;
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        resourceManager = new ResourceManager();
        menuScreen = new ScreenMenu(this, resourceManager);
        isMuted = false;

        this.setScreen(menuScreen);
    }

    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
        menuScreen.dispose();
        resourceManager.dispose();
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    public ScreenMenu getMenuScreen() {
        return menuScreen;
    }

    public boolean isMuted() {
        return isMuted;
    }
}
