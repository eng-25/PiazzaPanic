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
    protected float currentTimer;
    protected final Vector2 position;
//    protected final Timer timer = new Timer();
//    protected TimerTask tickTimerTask;
    private boolean isWorking;
    private final TextureRegion progressBarBg;
    private final TextureRegion progressBar;

    /**
     * Creates an interaction station with a position and delay.
     * @param position The position of the interaction station.
     * @param delay How long the interaction station will take to complete its action.
     */
    public InteractionStation(Vector2 position, int delay) {
        this.position = position;
        this.timeToPrep = delay;
        this.currentTimer = delay;
        isWorking = false;

        ResourceManager rm = new ResourceManager();
        progressBarBg = rm.barBg;
        progressBar = rm.barFg;
    }

    abstract public void finishInteract(); // define in each child class


    public void interact() {
        isWorking = true;
    }

    abstract public boolean canInteract(Cook cook);


    public void tickTimer(float delta) {
        if (isWorking) {
            System.out.println(currentTimer);
            currentTimer -= delta;
            if (currentTimer <= 0) {
                isWorking = false;
                currentTimer = timeToPrep;
                finishInteract();
            }
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
            if (!batch.isDrawing()) { batch.begin(); }
            float barX = progressBar.getRegionWidth() / tileSize;
            float barY = progressBar.getRegionHeight() / tileSize;
            float xPos = position.x + ((1 - barX) / 2f);
            float yPos = position.y + 2f;
            // bg
            batch.draw(progressBarBg, xPos, yPos, barX, barY);
            // bar
            float progress = 1 - (float) currentTimer / timeToPrep;
            batch.draw(progressBar, xPos, yPos, barX * progress, barY);
        }
    }

//    public TimerTask getTickTimerTask() {
//        return tickTimerTask;
//    }
//
//    public void cancelTimer() {
//        timer.cancel();
//    }
}
