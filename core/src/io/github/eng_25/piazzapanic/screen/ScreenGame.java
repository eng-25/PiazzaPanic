package io.github.eng_25.piazzapanic.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.eng_25.piazzapanic.PiazzaPanic;
import io.github.eng_25.piazzapanic.common.PiazzaMap;
import io.github.eng_25.piazzapanic.common.entity.Cook;
import io.github.eng_25.piazzapanic.util.ResourceManager;
import io.github.eng_25.piazzapanic.util.UIHelper;
import io.github.eng_25.piazzapanic.window.WindowGuide;
import io.github.eng_25.piazzapanic.window.WindowPause;

/**
 * The screen for the main game itself
 */
public class ScreenGame extends ScreenBase {

    private final ScreenViewport UIViewport;
    private final Table UITable;
    private final Stage UIStage;
    private final Cook cook1;
    private final Cook cook2;
    private Cook currentCook;
    private PiazzaMap map;

    private WindowPause pauseWindow;
    private WindowGuide guideWindow;

    public static final float WINDOW_SIZE = 0.6f;


    /**
     * Uses the height and width of previous screen to setup viewport initially
     *
     * @param game   main game class
     * @param rm     ResourceManager instance
     * @param width  the width of the window when the game was started
     * @param height the height of the window when the game was started
     */
    public ScreenGame(PiazzaPanic game, ResourceManager rm) {
        super(game, rm, new ExtendViewport(16, 9, new OrthographicCamera()));

        // UI setup
        UIViewport = new ScreenViewport();
        UIStage = new Stage(UIViewport);
        UITable = createTable(); // create table for laying out UI elements
        UITable.top().left(); // set table's gravity to top left
        UIStage.addActor(UITable);

        // cook setup
        cook1 = new Cook(resourceManager, new Vector2(0, 0));
        cook2 = new Cook(resourceManager, new Vector2(8, 8));
        currentCook = cook1;

        // map
        map = new PiazzaMap(rm, camera);

        adjustCam();

        setupWindows();
        setupUI();

        addActors();

    }

    /**
     * Sets up both windows
     */
    private void setupWindows() {
        pauseWindow = new WindowPause("pauseWindow", resourceManager, game);
        guideWindow = pauseWindow.getGuideWindow();

        guideWindow.setVisible(true); // initially show guide window
        pauseWindow.setVisible(false);

        resizeWindow(pauseWindow);
        resizeWindow(guideWindow);

        centreWindow(pauseWindow);
        centreWindow(guideWindow);

        guideWindow.setResizable(false);
        pauseWindow.setResizable(false);

        guideWindow.setModal(true);
        pauseWindow.setModal(true);

        guideWindow.setMovable(false);
        pauseWindow.setMovable(false);
    }

    /**
     * Resizes a given window based on the WINDOW_SIZE percentage constant
     *
     * @param window window to be resized
     */
    private void resizeWindow(Window window) {
        window.setSize(UIViewport.getWorldWidth() * WINDOW_SIZE, UIViewport.getWorldHeight() * WINDOW_SIZE);
    }

    /**
     * Re-centres a given window to the centre of the screen, based off the UI Viewport size
     *
     * @param window window to be centred
     */
    private void centreWindow(Window window) {
        window.setPosition((UIViewport.getWorldWidth() - window.getWidth()) / 2f,
                (UIViewport.getWorldHeight() - window.getHeight()) / 2f);
    }

    private void setupUI() {
        // pause button
        final TextButton pauseButton = UIHelper.createTextButton("Pause",
                UIViewport.getWorldWidth() - resourceManager.buttonUp.getRegionWidth(), 0, UITable);
        pauseButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                pauseWindow.setVisible(true);
            }
        });
    }

    /**
     * Ensures camera is centred on current cook
     */
    private void adjustCam() {
        camera.position.set(currentCook.getPosition(), 0);
        camera.update();
    }

    /**
     * Switches between cooks
     */
    private void switchCooks() {
        currentCook.stopMoving();
        currentCook = currentCook == cook1 ? cook2 : cook1;
    }


    /**
     * Add actors to stages
     */
    private void addActors() {

        stage.addActor(cook1);
        stage.addActor(cook2);

        UIStage.addActor(guideWindow);
        UIStage.addActor(pauseWindow);
    }

    @Override
    public void show() {
        // setup InputMultiplexer to handle both UI input and other key inputs for this class simultaneously
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(this);
        inputMultiplexer.addProcessor(UIStage);
        Gdx.input.setInputProcessor(inputMultiplexer);

    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        map.renderMap();

        // act and draw main stage
        stage.act(delta);
        stage.draw();
        adjustCam();

        // UI stage
        UIViewport.apply();
        UIStage.act(delta);
        UIStage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height); // don't centre camera, should be positioned on current cook
        UIViewport.update(width, height, true); // do centre camera for UI

        // pause button positioning
        UITable.getCells().get(0).padLeft(UIViewport.getWorldWidth() - resourceManager.buttonUp.getRegionWidth());

        resizeWindow(pauseWindow);
        resizeWindow(guideWindow);

        centreWindow(pauseWindow);
        centreWindow(guideWindow);
    }

    @Override
    public void dispose() {
        super.dispose();
        map.dispose();
        stage.dispose();
        UIStage.dispose();
    }

    // handle most inputs in keyDown and keyUp!
    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.W: // up
                currentCook.moveUp();
                break;
            case Input.Keys.S: // down
                currentCook.moveDown();
                break;
            case Input.Keys.A: // left
                currentCook.moveLeft();
                break;
            case Input.Keys.D: // right
                currentCook.moveRight();
                break;
            case Input.Keys.E: // switch
                switchCooks();
                break;
            case Input.Keys.F: // interact
                //TODO: Interact
            default:
                return false;
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.W:
            case Input.Keys.S:
                currentCook.resetY();
                break;
            case Input.Keys.A:
            case Input.Keys.D:
                currentCook.resetX();
                break;
            default:
                return false;
        }
        return true;
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
