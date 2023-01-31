package io.github.eng_25.piazzapanic.common.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import io.github.eng_25.piazzapanic.common.ingredient.Dish;
import io.github.eng_25.piazzapanic.common.interactable.Counter;
import io.github.eng_25.piazzapanic.screen.ScreenGame;
import io.github.eng_25.piazzapanic.util.ResourceManager;

public class Customer {

    private final Dish dishWanted;
    private Vector2 position;
    private float timer;
    private final float waitTime;
    private boolean shouldTickTimer;
    private final ScreenGame game;

    private final ResourceManager rm = new ResourceManager();
    private final TextureRegion dishTexture;
    private final TextureRegion speechTexture = rm.speech;
    private final TextureRegion progressBg = rm.barBg;
    private final TextureRegion progressBar = rm.barFg;
    private final TextureRegion texture = rm.customer;

    public Customer(Dish dishWanted, int waitTime, ScreenGame game) {
        this.dishWanted = dishWanted;
        dishTexture = dishWanted.getTexture();
        timer = waitTime;
        this.waitTime = waitTime;
        position = new Vector2(14, 14);
        shouldTickTimer = false;
        this.game = game;
    }

    public void walkToCounter(Counter counter) { //TODO: change
        position = counter.getPosition();
        shouldTickTimer = true;
    }

    public void walkAway() {
        position = new Vector2(16, 0);
    }

    public void tick(float delta, SpriteBatch batch, ScreenGame game, float tileSize) { // returns true if timer has expired
        renderCustomer(batch, tileSize);
        if (shouldTickTimer) {
            timer-=delta;

            if (timer <= 0) {
                // lose reputation
                game.loseReputation();
                shouldTickTimer = false;
            }
        }
    }

    public void receiveDish() {
        shouldTickTimer = false;
        walkAway();
        game.customerServed();
    }

    private void renderCustomer(SpriteBatch batch, float tileSize) {
        if (!batch.isDrawing()) { batch.begin(); }
        // customer render
        batch.draw(texture, position.x+1, position.y, 1, 2);

        // speech render
        batch.draw(speechTexture, position.x+1.8f, position.y+1.2f, 1.5f, 2);
        // dish inside speech render
        batch.draw(dishTexture, position.x+2.15f, position.y+1.8f, 1, 1);

        // progress render
        if (shouldTickTimer) {
            float barX = progressBg.getRegionWidth() / tileSize;
            float barY = progressBg.getRegionHeight() / tileSize;
            float barXPos = position.x+1;
            float barYPos = position.y+1.9f;
            float progress = 1-(waitTime-timer)/waitTime;
            batch.draw(progressBg, barXPos, barYPos, barX, barY);
            batch.draw(progressBar, barXPos, barYPos, barX*progress, barY);
        }
    }

    public Dish getDish() {
        return dishWanted;
    }
}
