package io.github.eng_25.piazzapanic.common.entity;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import io.github.eng_25.piazzapanic.common.ingredient.BaseIngredient;
import io.github.eng_25.piazzapanic.common.ingredient.Ingredient;
import io.github.eng_25.piazzapanic.util.ResourceManager;
import io.github.eng_25.piazzapanic.util.SizedStack;

public class Cook extends Actor {

    public static final float MOVEMENT_SPEED = 3f; // movement speed

    private final SizedStack<BaseIngredient> stack;
    private final Vector2 position;
    private final Vector2 movement;
    TextureRegion texture;

    public Cook(ResourceManager rm, Vector2 originalPos) {
        stack = new SizedStack<>(3);
        this.position = originalPos;
        this.movement = new Vector2(0, 0);
        texture = rm.cook;
    }

    public void moveLeft() {
        movement.x = -1;
    }

    public void moveRight() {
        movement.x = 1;
    }

    public void moveUp() {
        movement.y = 1;
    }

    public void moveDown() {
        movement.y = -1;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, position.x, position.y, 1, 2);
    }

    @Override
    public void act(float delta) {
        float xChange = movement.x * MOVEMENT_SPEED * delta;
        float yChange = movement.y * MOVEMENT_SPEED * delta;

        position.x += xChange;
        position.y += yChange;

    }

    public Vector2 getPosition() {
        return position;
    }

    public TextureRegion getTexture() {
        return texture;
    }

    public SizedStack<BaseIngredient> getStack() {
        return stack;
    }

    public BaseIngredient peekStack() {
        return stack.peek();
    }

    public BaseIngredient popStack() {
        return stack.pop();
    }

    public boolean pushStack(BaseIngredient ing) {
        return stack.push(ing) == ing; // returns true if push was successful
    }

    public boolean isStackFull() {
        return stack.isFull();
    }

    public void resetY() {
        movement.y = 0;
    }

    public void resetX() {
        movement.x = 0;
    }

    public void stopMoving() {
        resetX();
        resetY();
    }

}
