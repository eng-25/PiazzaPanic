package io.github.eng_25.piazzapanic.common.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import io.github.eng_25.piazzapanic.common.ingredient.Dish;
import io.github.eng_25.piazzapanic.common.ingredient.Ingredient;
import io.github.eng_25.piazzapanic.common.interactable.Counter;

public class Customer {

    private final Dish dishWanted;
    private TextureRegion speechTexture;
    private Vector2 position;
    private float timer;
    private final float waitTime;
    private boolean shouldTickTimer;

    private static final TextureRegion TEXTURE = Ingredient.rm.customer;

    public Customer(Dish dishWanted, int waitTime) {
        this.dishWanted = dishWanted;
        speechTexture = dishWanted.getTexture();
        timer = waitTime;
        this.waitTime = waitTime;
        position = new Vector2(14, 14);
        shouldTickTimer = false;
    }

    public void walkToCounter(Counter counter) { //TODO: change
        position = counter.getPosition();
        shouldTickTimer = true;
    }

    public void walkAway() {
        position = new Vector2(16, 0);
    }

    public void tick(float delta, SpriteBatch batch) { // returns true if timer has expired
        renderCustomer(batch);
        if (shouldTickTimer) {
            timer-=delta;

            if (timer <= 0) {
                // lose reputation
            }
        }
    }

    public void receiveDish() {
        shouldTickTimer = false;
        walkAway();
    }

    public void renderProgress() {

    }

    private void renderCustomer(SpriteBatch batch) {
        batch.begin();
        batch.draw(TEXTURE, position.x+1, position.y, 1, 2);
        batch.end();
    }

    public Dish getDish() {
        return dishWanted;
    }
}
