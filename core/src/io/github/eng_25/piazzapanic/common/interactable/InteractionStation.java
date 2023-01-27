package io.github.eng_25.piazzapanic.common.interactable;

import com.badlogic.gdx.math.Vector2;
import io.github.eng_25.piazzapanic.common.entity.Cook;

import java.util.Timer;
import java.util.TimerTask;

/**
 * An abstract class that child classes will inherit from
 */
public abstract class InteractionStation {
    protected int timeToPrep;
    protected int currentTimer;
    protected final Vector2 position;
    private final Timer timer = new Timer();
    protected final TimerTask tickTimerTask;

    public InteractionStation(Vector2 position, int delay) {
        this.position = position;
        this.timeToPrep = delay;
        this.currentTimer = delay;
        tickTimerTask = new TimerTask() {
            @Override
            public void run() {
                tickTimer();
            }
        };
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

    public void interact() {
        //cook.canMove = false;
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
            currentTimer = timeToPrep;
            finishInteract();
        }
    }

    public Vector2 getPosition() {
        return position;
    }

}
