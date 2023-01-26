package io.github.eng_25.piazzapanic.screen;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.eng_25.piazzapanic.PiazzaPanic;
import io.github.eng_25.piazzapanic.util.ResourceManager;

/**
 * A base screen class, implementing the ResourceManager, stage,
 * table and button creation for all other screens to use
 */
public abstract class ScreenBase implements Screen, InputProcessor {

    protected final PiazzaPanic game;
    protected ResourceManager resourceManager;
    protected Stage stage;

    // attached to stage, here for ease of access
    protected OrthographicCamera camera;
    protected Viewport viewport;

    /**
     * @param game,    the main Game class
     * @param rm       an instance of ResourceManager
     * @param viewport the viewport to be used for this screen
     */
    public ScreenBase(PiazzaPanic game, ResourceManager rm, Viewport viewport) {
        this.game = game;
        this.resourceManager = rm;
        this.viewport = viewport;
        this.stage = new Stage(viewport, game.getBatch());
        this.camera = (OrthographicCamera) viewport.getCamera();
    }

    /**
     * Creates a new table, convenience for all screens
     *
     * @return
     */
    public Table createTable() {
        Table table = new Table();
        table.setFillParent(true);
        return table;
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public Stage getStage() {
        return stage;
    }

    public Viewport getViewport() {
        return viewport;
    }
}
