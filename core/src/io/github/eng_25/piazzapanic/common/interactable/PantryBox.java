package io.github.eng_25.piazzapanic.common.interactable;

import com.badlogic.gdx.math.Vector2;
import io.github.eng_25.piazzapanic.common.entity.Cook;
import io.github.eng_25.piazzapanic.common.ingredient.Ingredient;

/**
 * Based on Ingredient station the cook is at, it will return the relevant ingredient.
 */
public class PantryBox extends InteractionStation {

    private final Ingredient toOutput;
    private Cook attachedCook;

    /**
     * Creates an instance of a pantry box with a position and specified ingredient to output.
     * @param position The position of the pantry box.
     * @param toOutput The ingredient to be outputted.
     */
    public PantryBox(Vector2 position, Ingredient toOutput) {
        super(position, 0);
        this.toOutput = toOutput;
        attachedCook = null;
    }

    /**
     * Adds the relevant ingredient to the interacting cook's stack.
     */
    @Override
    public void finishInteract() {
        attachedCook.pushStack(toOutput);
    }

    /**
     * If the stack isn't full (an item can be pushed), the cook can interact with the pantry box.
     * @param cook The cook interacting with the pantry box.
     * @return Whether interaction is valid.
     */
    @Override
    public boolean canInteract(Cook cook) {
        if (!(cook.isStackFull())) {
            attachedCook = cook;
            return true;
        } return false;
    }
}
