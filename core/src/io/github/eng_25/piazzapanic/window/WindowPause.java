package io.github.eng_25.piazzapanic.window;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import io.github.eng_25.piazzapanic.PiazzaPanic;
import io.github.eng_25.piazzapanic.screen.ScreenMenu;
import io.github.eng_25.piazzapanic.util.ResourceManager;
import io.github.eng_25.piazzapanic.util.UIHelper;

/**
 * Pause window, can change mute status of game and open guide window
 */
public class WindowPause extends WindowClosable {

    private final WindowGuide guideWindow;
    private final PiazzaPanic game;

    /**
     * Creates a new closable pause window, with an invisible guide window attached.
     *
     * @param title           title of window, made not visible
     * @param resourceManager a ResourceManager instance
     * @param game            the main Game instance
     */
    public WindowPause(String title, ResourceManager resourceManager, PiazzaPanic game) {
        super(title, resourceManager);
        guideWindow = new WindowGuide("pauseGuideWindow", resourceManager);
        guideWindow.setVisible(false);
        this.game = game;

        setupButtons();
    }

    /**
     * Sets up all buttons on the window
     */
    private void setupButtons() {
        Table table = getTitleTable();

        table.pack();
        float padLeft = table.getWidth() - resourceManager.buttonUp.getRegionWidth();
        float padTop = table.getHeight();

        final TextButton guideButton = UIHelper.createTextButton("How To Play", padLeft, padTop, table);
        final TextButton muteButton = UIHelper.createTextButton("Mute", padLeft, padTop, table);
        final TextButton quitButton = UIHelper.createTextButton("Quit to Menu", padLeft, padTop, table);

        // mute button listener
        muteButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.toggleMuted();
                String newText = game.isMuted() ? "Unmute" : "Mute";
                muteButton.setText(newText);
            }
        });

        // guide button listener
        guideButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                guideWindow.setVisible(true);
                guideWindow.toFront();
            }
        });

        // quit button listener
        quitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new ScreenMenu(game, resourceManager));
            }
        });
    }

    public WindowGuide getGuideWindow() {
        return guideWindow;
    }
}
