package io.github.eng_25.piazzapanic.common.interactable;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import io.github.eng_25.piazzapanic.common.entity.Cook;
import io.github.eng_25.piazzapanic.util.ResourceManager;

/**
 * An abstract class that all interaction stations will inherit from
 */
public abstract class InteractionStation {
    protected int timeToPrep;
    protected float currentTimer;
    protected final Vector2 position;
    private boolean isWorking;
    private final TextureRegion progressBarBg;
    private final TextureRegion progressBar;

    /**
     * Creates an interaction station with a position and a timer used to delay before finishInteract is called.
     * Initially sets isWorking to false and sets up progress bar textures using a ResourceManager instance.
     *
     * @param position The position of the interaction station.
     * @param delay    How many seconds the interaction station will take to complete its interaction.
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

    /**
     * Abstract method to define logic at the end of a station's interaction
     */
    abstract public void finishInteract(); // define in each child class


    /**
     * Called at the start of interaction, allows the timer to tick
     */
    public void startInteract() {
        isWorking = true;
    }

    /**
     * Used to check if an interaction should go ahead or not, based on logic defined independently in each child class.
     *
     * @param cook the cook interacting
     * @return true if the interaction is valid, false otherwise
     */
    abstract public boolean canInteract(Cook cook);


    /**
     * Called every game tick, will call finishInteract() and stop working once the timer has finished.
     *
     * @param delta
     */
    public void tickTimer(float delta) {
        if (isWorking) {
            currentTimer -= delta;
            if (currentTimer <= 0) {
                isWorking = false;
                currentTimer = timeToPrep;
                finishInteract();
            }
        }
    }

    /**
     * Used to render progress bar of work
     *
     * @param batch    SpriteBatch to render to.
     * @param tileSize game unit/tile size in pixels.
     */
    public void renderProgress(SpriteBatch batch, float tileSize) {
        if (isWorking && timeToPrep > 0) { // only render progress bar on stations which have a prep time
            if (!batch.isDrawing()) {
                batch.begin();
            }
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

    public Vector2 getPosition() {
        return position;
    }

    public boolean isWorking() {
        return isWorking;
    }
}
