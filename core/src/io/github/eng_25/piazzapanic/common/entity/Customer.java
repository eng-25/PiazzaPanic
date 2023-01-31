package io.github.eng_25.piazzapanic.common.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import io.github.eng_25.piazzapanic.common.ingredient.Dish;
import io.github.eng_25.piazzapanic.common.interactable.Counter;
import io.github.eng_25.piazzapanic.screen.ScreenGame;
import io.github.eng_25.piazzapanic.util.ResourceManager;

import java.util.Random;

public class Customer {

    private final Dish dishWanted;
    private Vector2 position;
    private float timer; // time spent waiting
    private final float waitTime; // time willing to wait
    private boolean shouldTickTimer;
    private final ScreenGame game;

    private final ResourceManager rm = new ResourceManager();
    private final TextureRegion dishTexture;
    private final TextureRegion speechTexture = rm.speech;
    private final TextureRegion progressBg = rm.barBg;
    private final TextureRegion progressBar = rm.barFg;
    private final TextureRegion texture = rm.customer;

    /**
     * Create a new Customer at a set start position, with a random dish wanted.
     *
     * @param waitTime   time customer will wait before reputation is lost
     * @param game       ScreenGame instance
     */
    public Customer(int waitTime, ScreenGame game) {
        this.dishWanted = getRandomDish();
        dishTexture = dishWanted.getTexture();
        timer = waitTime;
        this.waitTime = waitTime;
        position = new Vector2(14, 14);
        shouldTickTimer = false;
        this.game = game;
    }

    /**
     * Makes customer move to counter
     *
     * @param counter counter to move to
     */
    public void walkToCounter(Counter counter) {
        position = counter.getPosition();
        shouldTickTimer = true;
    }

    /**
     * Makes customer leave counter
     */
    public void walkAway() {
        position = new Vector2(16, 0);
    }

    /**
     * Called every tick
     *
     * @param delta    time passed since last call
     * @param batch    SpriteBatch to draw with
     * @param tileSize size of game tile unit, to ensure drawing is accurate
     */
    public void tick(float delta, SpriteBatch batch, float tileSize) { // returns true if timer has expired
        renderCustomer(batch, tileSize);
        if (shouldTickTimer) {
            timer -= delta;

            if (timer <= 0) {
                // lose reputation
                game.loseReputation();
                shouldTickTimer = false;
            }
        }
    }

    /**
     * Called when correct dish is passed into Counter this Customer is attached to
     */
    public void receiveDish() {
        shouldTickTimer = false;
        walkAway();
        game.customerServed();
    }

    /**
     * Used to render customer, dish speech and progress bar
     *
     * @param batch    batch to render with
     * @param tileSize size of game tile unit, to ensure drawing is accurate
     */
    private void renderCustomer(SpriteBatch batch, float tileSize) {
        if (!batch.isDrawing()) {
            batch.begin();
        }
        // customer render
        batch.draw(texture, position.x + 1, position.y, 1, 2);

        // speech render
        batch.draw(speechTexture, position.x + 1.8f, position.y + 1.2f, 1.5f, 2);
        // dish inside speech render
        batch.draw(dishTexture, position.x + 2.15f, position.y + 1.8f, 1, 1);

        // progress render
        if (shouldTickTimer) {
            float barX = progressBg.getRegionWidth() / tileSize;
            float barY = progressBg.getRegionHeight() / tileSize;
            float barXPos = position.x + 1;
            float barYPos = position.y + 1.9f;
            float progress = 1 - (waitTime - timer) / waitTime;
            batch.draw(progressBg, barXPos, barYPos, barX, barY);
            batch.draw(progressBar, barXPos, barYPos, barX * progress, barY);
        }
    }

    private Dish getRandomDish() {
        Object[] dishKeys = Dish.DISH_MAP.keySet().toArray();
        int numOfDishes = dishKeys.length;
        int randomDishIndex = new Random().nextInt(numOfDishes);
        return Dish.getDish((String) dishKeys[randomDishIndex]);
    }

    public Dish getDish() {
        return dishWanted;
    }

    public Vector2 getPosition() {
        return position;
    }

    public float getTimer() {
        return timer;
    }
}
