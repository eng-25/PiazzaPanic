package io.github.eng_25.piazzapanic.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.eng_25.piazzapanic.PiazzaPanic;
import io.github.eng_25.piazzapanic.util.ResourceManager;

public class ScreenMenu extends ScreenBase {

    private Table table;

    private final float TABLE_TOP_PAD_MULTIPLIER = 4/10f;

    public ScreenMenu(PiazzaPanic game, ResourceManager rm) {
        super(game, rm, new ScreenViewport());
    }

    /**
     * Convenience to call all button methods, allows more buttons to be added neatly here
     */
    private void addButtons() {
        addPlayButton();
        addMuteButton();
    }

    /**
     * Adds a mute button to the Menu Screen's table, toggling the isMuted boolean in the game class
     */
    private void addMuteButton() {
        Actor button = createTextButton("Mute", 0, 40, table);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.toggleMuted();
            }
        });
    }

    /**
     * Adds a play button to the Menu Screen's table, changing the screen to a new ScreenGame
     */
    private void addPlayButton() {
        Actor button = createTextButton("Play", 0, 0, table);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new ScreenGame(game, resourceManager, viewport.getScreenWidth(), viewport.getScreenHeight()));
            }
        });
    }

    @Override
    public void show() {
        table = createTable();
        table.top().padTop(PiazzaPanic.DEFAULT_HEIGHT*TABLE_TOP_PAD_MULTIPLIER); // set table's gravity to top and pad top a little
        //table.debug(); //TODO: remove

        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);

        addButtons();
    }

    @Override
    public void render(float delta) {
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void dispose() {
        super.dispose();
        table.remove();
    }

    @Override
    public void resize(int width, int height) {
        table.top().padTop(height*TABLE_TOP_PAD_MULTIPLIER);
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