package io.github.eng_25.piazzapanic.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.eng_25.piazzapanic.PiazzaPanic;
import io.github.eng_25.piazzapanic.util.ResourceManager;
import io.github.eng_25.piazzapanic.util.UIHelper;

/**
 * Ending Screen, used to display game's timer, end message and a button to return to the main menu.
 */
public class ScreenGameEnd extends ScreenBase {

    private final String endMessage;
    private final int timeTaken;
    private Table table;

    /**
     * @param game       main Game instance
     * @param rm         an instance of ResourceManager, to access Assets
     * @param endMessage the ending message to be displayed
     * @param timeTaken  the game timer
     */
    public ScreenGameEnd(PiazzaPanic game, ResourceManager rm, String endMessage, float timeTaken) {
        super(game, rm, new ScreenViewport());
        this.endMessage = endMessage;
        this.timeTaken = (int) Math.floor(timeTaken);
    }

    /**
     * Writes text to the screen
     */
    private void addText() {
        Label.LabelStyle style = new Label.LabelStyle(resourceManager.font, Color.WHITE);
        table.add(new Label(endMessage, style));
        table.row();
        int mins = timeTaken / 60;
        String timeString = mins > 0 ? (mins + "m " + (timeTaken - (mins * 60)) + "s ") : (timeTaken + "s ");
        table.add(new Label("Time spent playing: " + timeString, style));
        table.row();
    }

    /**
     * Creates button to return to main menu
     */
    private void addReturnButton() {
        final TextButton button = UIHelper.createTextButton("Main Menu", 0, 10, table);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new ScreenMenu(game, resourceManager));
            }
        });
    }

    @Override
    public void show() {
        table = createTable();
        table.top().padTop(viewport.getScreenHeight() * 0.1f);

        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);

        addText();

        addReturnButton();
    }

    @Override
    public void dispose() {
        super.dispose();
        table.remove();
    }

    @Override
    public void resize(int width, int height) {
        table.top().padTop(height * 0.1f);
        super.resize(width, height);
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
