package io.github.eng_25.piazzapanic.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.eng_25.piazzapanic.PiazzaPanic;
import io.github.eng_25.piazzapanic.util.ResourceManager;
import io.github.eng_25.piazzapanic.window.WindowClosable;

/**
 * The screen for the main game itself
 */
public class ScreenGame extends ScreenBase {

    private OrthogonalTiledMapRenderer mapRenderer;
    private final ScreenViewport UIViewport;
    private final Table UITable;
    private final Stage UIStage;


    /**
     * Uses the height and width of previous screen to setup viewport initially
     * @param game main game class
     * @param rm ResourceManager instance
     * @param width the width of the window when the game was started
     * @param height the height of the window when the game was started
     */
    public ScreenGame(PiazzaPanic game, ResourceManager rm, int width, int height) {
        super(game, rm, new ExtendViewport(16, 9, new OrthographicCamera()));
        // UI setup
        UIViewport = new ScreenViewport();
        UIStage = new Stage(UIViewport);
        UITable = createTable(); // create table for laying out UI elements
        UITable.top().left(); // set table's gravity to top left
        UIStage.addActor(UITable);
    }

    @Override
    public void show() {
        // setup InputMultiplexer to handle both UI input and other key inputs for this class simultaneously
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(UIStage);
        inputMultiplexer.addProcessor(this);
        Gdx.input.setInputProcessor(inputMultiplexer);

        // setup TiledMap with 1/32 as tiles are 32x32px
        mapRenderer = new OrthogonalTiledMapRenderer(resourceManager.gameMap, 1/32f);
        camera.update();
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // render map
        mapRenderer.setView(camera);
        mapRenderer.render();

        // act and draw main stage
        stage.act(delta);
        stage.draw();

        // UI stage
        UIViewport.apply();
        UIStage.act(delta);
        UIStage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height); // don't centre camera, should be positioned on current cook
        UIViewport.update(width, height, true); // do centre camera for UI
    }

    @Override
    public void dispose() {
        super.dispose();
        mapRenderer.dispose();
        stage.dispose();
        UIStage.dispose();
    }

    // handle most inputs in keyDown and keyUp!
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
