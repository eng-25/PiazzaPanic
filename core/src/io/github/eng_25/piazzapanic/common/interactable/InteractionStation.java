package io.github.eng_25.piazzapanic.common.interactable;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import io.github.eng_25.piazzapanic.common.entity.Cook;
import io.github.eng_25.piazzapanic.util.ResourceManager;

import java.util.Timer;
import java.util.TimerTask;

/**
 * An abstract class that child classes will inherit from
 */
public abstract class InteractionStation {
    protected int timeToPrep;
    protected int currentTimer;
    protected final Vector2 position;
    protected final Timer timer = new Timer();
    protected TimerTask tickTimerTask;
    private boolean isWorking;
    private final TextureRegion progressBarBg;
    private final TextureRegion progressBar;

    public InteractionStation(Vector2 position, int delay) {
        this.position = position;
        this.timeToPrep = delay;
        this.currentTimer = delay;
        isWorking = false;

        ResourceManager rm = new ResourceManager();
        progressBarBg = rm.barBg;
        progressBar = rm.barFg;
    }

    /* TODO: _Sam: define in each class - logic to happen after timer has ticked
    bin - pop from stack
    counter - pop from stack, but make sure Dish is stored as field somewhere
    PantryBox - push 'Ingredient.copyOf(output)' to stack
    PlatingStation - pop from stack, add it to list, then check the list with the Recipes method
        if the Recipes method returns a Dish, push that onto the same cook's stack
        if returns null, do nothing
     */
    abstract public void finishInteract(); // define in each child class

    // Probably say Faran write this
    public void interact() {
        //cook.canMove = false;
        isWorking = true;
        tickTimerTask = new TimerTask() {
            @Override
            public void run() {
                tickTimer();
            }
        };
        timer.schedule(tickTimerTask, 0, 1000);
    }

    /* TODO: _Sam: define in each class - returns true if the interaction should go ahead, add any logic that needs to
            happen BEFORE interaction (e.g. removing from stack)
    e.g. in CookingStation, returns true if the TOP of the cook's stack had an item in the "validItems" list
    bin - if stack not empty, true
    counter - if TOP of cook's stack (use peek to check) is instanceof Dish, return true (make sure to pop stack)
    PantryBox - check stack !isFull(), return true
    PlatingStation - if instanceof Ingredient and .isPrepared(), return true (attach cook maybe, try first using currentCook?)
    */

    abstract public boolean canInteract(Cook cook);



    private void tickTimer() {
        currentTimer -= 1;
        if (currentTimer <= 0) {
            tickTimerTask.cancel();
            isWorking = false;
            currentTimer = timeToPrep;
            finishInteract();
        }
    }

    public Vector2 getPosition() {
        return position;
    }

    public boolean isWorking() {
        return isWorking;
    }

    public void renderProgress(SpriteBatch batch, float tileSize) {
        if (isWorking && timeToPrep > 0) { // only render progress bar on stations which have a prep time
            batch.begin();
            float barX = progressBar.getRegionWidth()/tileSize;
            float barY = progressBar.getRegionHeight()/tileSize;
            float xPos = position.x+((1-barX)/2f);
            float yPos = position.y+2f;
            // bg
            batch.draw(progressBarBg, xPos, yPos, barX, barY);
            // bar
            float progress = 1-(float)currentTimer/timeToPrep;
            batch.draw(progressBar, xPos, yPos, barX*progress, barY);
            batch.end();
        }
    }
}
