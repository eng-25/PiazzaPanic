package io.github.eng_25.piazzapanic.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.eng_25.piazzapanic.PiazzaPanic;
import io.github.eng_25.piazzapanic.common.PiazzaMap;
import io.github.eng_25.piazzapanic.common.entity.Cook;
import io.github.eng_25.piazzapanic.common.entity.Customer;
import io.github.eng_25.piazzapanic.common.ingredient.BaseIngredient;
import io.github.eng_25.piazzapanic.common.ingredient.Dish;
import io.github.eng_25.piazzapanic.common.ingredient.Ingredient;
import io.github.eng_25.piazzapanic.common.interactable.Counter;
import io.github.eng_25.piazzapanic.common.interactable.InteractionStation;
import io.github.eng_25.piazzapanic.util.ResourceManager;
import io.github.eng_25.piazzapanic.util.UIHelper;
import io.github.eng_25.piazzapanic.window.WindowGuide;
import io.github.eng_25.piazzapanic.window.WindowPause;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Set;

/**
 * The screen for the main game itself
 */
public class ScreenGame extends ScreenBase {

    private final ScreenViewport UIViewport;
    private final Table UITable;
    private final Stage UIStage;
    private final Cook[] cooks;
    private Cook currentCook;
    private final PiazzaMap map;
    private boolean interactHappened; // to prevent holding of interact key causing multiple calls
    private float gameTimer; // game timer in seconds
    private int customerCount; // number of customers that have been created
    private int customersServed;
    private int reputationPoints; // reputation points left
    private int customersWaiting; // used in case there are no free counters when a new customer is added

    private WindowPause pauseWindow;
    private WindowGuide guideWindow;

    public static final float WINDOW_SIZE = 0.6f; // window size relative to screen size
    public static final int TOTAL_CUSTOMERS = 5; // number of customers throughout mode
    public static final int COOK_COUNT = 2; // total number of cooks
    public static final int REPUTATION_AMOUNT = 3; // how many reputation points initially
    public static final int CUSTOMER_INTERVAL = 60; // interval in seconds between customers
    public static final int CUSTOMER_TIMER = 60; // time before reputation is lost, customer still never leaves due to time

    /**
     * Uses the height and width of previous screen to setup viewport initially
     *
     * @param game main game class
     * @param rm   ResourceManager instance
     */
    public ScreenGame(PiazzaPanic game, ResourceManager rm) {
        super(game, rm, new ExtendViewport(16, 9, new OrthographicCamera()));
        interactHappened = false;

        // UI setup
        UIViewport = new ScreenViewport();
        UIStage = new Stage(UIViewport, game.getBatch());
        UITable = createTable(); // create table for laying out UI elements
        UITable.top().left(); // set table's gravity to top left
        UIStage.addActor(UITable);

        // cook setup
        cooks = new Cook[COOK_COUNT];
        for (int i=0; i<COOK_COUNT; i++) {
            cooks[i] = Cook.createCook(2*i, 2*i, resourceManager.cook);
        }
        currentCook = cooks[0];

        // map
        map = new PiazzaMap(rm, camera);

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
                UIViewport.getScreenWidth()-(resourceManager.buttonUp.getRegionWidth()), 0, UITable);
        pauseButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                pauseWindow.setVisible(true);
            }
        });

        // stack UI
        Image emptyStack = new Image(resourceManager.emptyStack);
        Image stack = new Image(resourceManager.stack);
        stack.scaleBy(1.5f);
        UITable.add(emptyStack).left().row();
        UITable.add(emptyStack).left().row();
        UITable.add(emptyStack).left().row();
        UITable.add(stack).left().row();
        updateStackTextures();
        adjustStackUIPosition();

        // reputation UI
        if (reputationPoints > 0) {
            Image repPoint = new Image(resourceManager.reputation_textures[reputationPoints - 1][0]);
            UITable.add(repPoint).right().bottom();
        }
    }

    private void updateStackTextures() {
        // set up list to determine which parts of stack UI are filled and which should be empty
        ArrayList<BaseIngredient> stackDisplayList = new ArrayList<>();
        for (Object i : currentCook.getStack().toArray()) {
            stackDisplayList.add((BaseIngredient) i);
        }
        while (stackDisplayList.size() < 3) {
            stackDisplayList.add(null);
        }

        // if part of the stack was empty, use the empty texture - otherwise scale
        Array<Cell> cells = UITable.getCells();
        for (int i = 1; i < 4; i++) { // 3 stack images
            BaseIngredient ing = stackDisplayList.get(3-i);
            TextureRegion tex = ing == null ? resourceManager.emptyStack : ing.getTexture();
            Image texScaled = new Image(tex);
            texScaled.scaleBy(1.5f);
            cells.get(i).setActor(texScaled);
        }
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
        currentCook = currentCook == cooks[0] ? cooks[1] : cooks[0];
    }

    private void adjustStackUIPosition() {
        float padLeft = UIViewport.getScreenWidth() * 0.1f;
        float initialPadTop = UIViewport.getScreenHeight() * 0.4f;
        float padTop = UIViewport.getScreenHeight() * 0.1f;
        UITable.getCells().get(1).padLeft(padLeft).padTop(initialPadTop);
        for (int i = 2; i < 5; i++) {
            UITable.getCells().get(i).padLeft(padLeft).padTop(padTop);
        }
    }


    /**
     * Add actors to stages
     */
    private void addActors() {

        Arrays.stream(cooks).forEach(cook -> stage.addActor(cook));

        UIStage.addActor(guideWindow);
        UIStage.addActor(pauseWindow);
    }

    private void updateTimer(float timePassed) {
        float oldTime = gameTimer;
        gameTimer+=timePassed; // new time

        // check for events
        // customer events
        if (customersWaiting > 0) { // if a customer was left waiting previously, attempt to make a new customer regardless of timer
            for (int i = 0; i < customersWaiting; i++) { // use a loop in case this stacks up e.g. player might not serve anyone for multiple minutes
                addCustomer();
            }
        }
        // if next minute entered since last call, new customer created
        if (Math.floor(oldTime/CUSTOMER_INTERVAL) < Math.floor(gameTimer/CUSTOMER_INTERVAL)
                && customerCount < TOTAL_CUSTOMERS) {
            addCustomer();
        }
    }

    private void addCustomer() {
        customerCount++;
        Counter toAttach = map.getFreeCounter();
        if (toAttach == null) { // if no free counter
            customersWaiting++;
            return;
        }
        Customer customer = new Customer(getRandomDish(), CUSTOMER_TIMER, this);
        toAttach.attachCustomer(customer);
    }

    private Dish getRandomDish() {
        Object[] dishKeys = Dish.DISH_MAP.keySet().toArray();
        int numOfDishes = dishKeys.length;
        int randomDishIndex = new Random().nextInt(numOfDishes);
        return Dish.getDish((String) dishKeys[randomDishIndex]);
    }

    public void loseReputation() {
        reputationPoints--;
        if (reputationPoints<=0) {
            loseGame();
        }
    }

    private void updateRepUI() {
        Image updated = null;
        if (reputationPoints > 0) {
            updated = new Image(resourceManager.reputation_textures[reputationPoints - 1][0]);
        }
        UITable.getCells().get(UITable.getCells().size-1).setActor(updated).bottom().right();

    }

    public void customerServed() {
        customersServed++;
        if (customersServed >= TOTAL_CUSTOMERS) {
            winGame();
        }
    }

    private void loseGame() {
        endGame("Game Lost");
    }

    private void winGame() {
        endGame("Game Won");
    }

    private void endGame(String message) {
        // set screen to end screen, in turn calls hide() which calls dispose()
        game.setScreen(new ScreenGameEnd(game, resourceManager, message, gameTimer));
    }

    @Override
    public void show() {
        // setup InputMultiplexer to handle both UI input and other key inputs for this class simultaneously
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(this);
        inputMultiplexer.addProcessor(UIStage);
        Gdx.input.setInputProcessor(inputMultiplexer);

        gameTimer = 0;
        customerCount = 0;
        customersWaiting = 0;
        customersServed = 0;
        reputationPoints = REPUTATION_AMOUNT;

        adjustCam();
        setupWindows();
        setupUI();

        addActors();

        addCustomer();
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
        //this.dispose();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 1, 1, 0.1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        updateTimer(delta);

        // render map and progress bars
        map.renderMap(game.getBatch(), delta, this);

        // act and draw main stage
        stage.act(delta);
        stage.draw();
        adjustCam();

        // UI
        updateStackTextures();
        updateRepUI();

        // UI stage
        UIViewport.apply();
        UIStage.act(delta);
        UIStage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height); // don't centre camera, should be positioned on current cook
        UIViewport.update(width, height, true); // do centre camera for UI

        Array<Cell> UICells = UITable.getCells();
        // pause button positioning
        UICells.get(0).padLeft(UIViewport.getScreenWidth()-(resourceManager.buttonUp.getRegionWidth()));

        // stack UI positioning
        adjustStackUIPosition();

        // reputation UI positioning
        UICells.get(UICells.size-1).bottom().right();

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
                // find nearest InteractionStation to current cook
                InteractionStation toInteractWith = map.checkInteraction(currentCook);
                if (toInteractWith == null) { break; }
                if (toInteractWith.canInteract(currentCook) && !interactHappened
                        && !toInteractWith.isWorking() && !currentCook.isInteracting()) { // check if interaction is valid
                    interactHappened = true;
                    toInteractWith.interact();
                }
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
            case Input.Keys.F:
                interactHappened = false;
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
