package io.github.eng_25.piazzapanic.window;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import io.github.eng_25.piazzapanic.PiazzaPanic;
import io.github.eng_25.piazzapanic.screen.ScreenBase;
import io.github.eng_25.piazzapanic.util.ResourceManager;
import io.github.eng_25.piazzapanic.util.UIHelper;

/**
 * Pause window, can change mute status of game and open guide window
 */
public class WindowPause extends WindowClosable {

    private final WindowGuide guideWindow;
    private final PiazzaPanic game;

    public WindowPause(String title, ResourceManager resourceManager, PiazzaPanic game) {
        super(title, resourceManager);
        guideWindow = new WindowGuide("pauseGuideWindow", resourceManager);
        guideWindow.setVisible(false);
        this.game = game;

        setupButtons();
    }

    private void setupButtons() {
        Table table = getTitleTable(); //TODO: change padTop to non-hardcoded value?

        table.pack();
        float padLeft = table.getWidth()-resourceManager.buttonUp.getRegionWidth();
        float padTop = table.getHeight();

        TextButton guideButton = UIHelper.createTextButton("How To Play", padLeft, padTop, table);
        TextButton muteButton = UIHelper.createTextButton("Mute", padLeft, padTop, table);

        muteButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.toggleMuted();
            }
        });

        guideButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                guideWindow.setVisible(true);
                guideWindow.toFront();
            }
        });
    }

    public WindowGuide getGuideWindow() {
        return guideWindow;
    }
}
