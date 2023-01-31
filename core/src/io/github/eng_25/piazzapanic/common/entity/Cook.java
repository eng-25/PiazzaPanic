package io.github.eng_25.piazzapanic.common.entity;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import io.github.eng_25.piazzapanic.common.ingredient.BaseIngredient;
import io.github.eng_25.piazzapanic.util.SizedStack;

public class Cook extends Actor {

    public static final float MOVEMENT_SPEED = 3f; // movement speed

    private final SizedStack<BaseIngredient> stack;
    private final Vector2 position;
    private final Vector2 movement;
    private boolean canMove;
    private boolean isInteracting;
    TextureRegion texture;

    /**
     * Initialise a new cook not moving or interacting, with a stack of size 3
     * @param texture texture used for cook
     * @param originalPos starting position of cook
     */
    public Cook(TextureRegion texture, Vector2 originalPos) {
        stack = new SizedStack<>(3);
        this.position = originalPos;
        this.movement = new Vector2(0, 0);
        this.texture = texture;
        canMove = true;
        isInteracting = false;
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
        float oldXPos = position.x;
        float oldYPos = position.y;

        if (!canMove) {
            return;
        }
        /*
         * first line checks if the cook is within the bounds of the kitchen
         * the rest checks where the cook is with respect of the pantry boxes not
         * letting them walk through them
         * finally the statement checks if there is a movement input in that direction
         * on that axis
         *
         */
        // ---------------------------------left + right (x)
        if ((position.x < 0 && movement.x < 0) // out of bounds left
                || ((((position.x < 0.9 || (position.x < 2.9 && position.x > 1.3)
                || (position.x > 3.3 && position.x < 4.9) || (position.x > 5.3 && position.x < 6.9)
                || (position.x > 7.3))
                && (position.y > 0.8 && position.y < 1.8))) && movement.x < 0)) {
            // resetX();
            position.x = oldXPos;
        } else if ((position.x > 8.5 && movement.x > 0) // out of bounds right
                || ((((position.x < 0.8 || (position.x < 2.8 && position.x > 1.2)
                || (position.x > 3.2 && position.x < 4.8) || (position.x > 5.2 && position.x < 6.8)
                || (position.x > 7.2))
                && (position.y > 0.8 && position.y < 1.8))) && movement.x > 0)) {
            // resetX();
            position.x = oldXPos;
        } else {
            position.x += xChange; // moves on the x axis
        }
        // ------------------------------------------down + up (y)
        if ((position.y < 0 && movement.y < 0) // out of bounds down
                || ((position.y < 1.9 && position.y > 0.9)
                && ((position.x > -0.2 && position.x < 0.8) || (position.x > 1.3 && position.x < 2.8)
                || (position.x > 3.3 && position.x < 4.8) || (position.x > 5.3 && position.x < 6.8)
                || (position.x > 7.3 && position.x < 8.8))
                && movement.y < 0)) {
            // resetY();
            position.y = oldYPos;
        } else if ((position.y > 13 && movement.y > 0) // out of bounds up
                || ((position.y > 0.7 && position.y < 1.8)
                && ((position.x > -0.2 && position.x < 0.8) || (position.x > 1.3 && position.x < 2.8)
                || (position.x > 3.3 && position.x < 4.8) || (position.x > 5.3 && position.x < 6.8)
                || (position.x > 7.3 && position.x < 8.8))
                && movement.y > 0)) {
            // resetY();
            position.y = oldYPos;
        } else {
            position.y += yChange; // moves on the y axis
        }

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

    public boolean isStackEmpty() {
        return stack.isEmpty();
    }

    /**
     * Resets the Y axis movement speed to 0
     */
    public void resetY() {
        movement.y = 0;
    }

    /**
     * Resets the X axis movement speed to 0
     */
    public void resetX() {
        movement.x = 0;
    }

    /**
     * Stops all movement
     */
    public void stopMoving() {
        resetX();
        resetY();
    }

    public void setCanMove(boolean canMove) {
        this.canMove = canMove;
    }

    public void setIsInteracting(boolean isInteracting) {
        this.isInteracting = isInteracting;
    }

    public boolean isInteracting() {
        return isInteracting;
    }
}
