package io.github.eng_25.piazzapanic.common.interactable;

import com.badlogic.gdx.math.Vector2;
import io.github.eng_25.piazzapanic.common.entity.Cook;

/**
 * This class contains the logic for each bin instance
 * which involves removing the top item from the stack.
 */
public class Bin extends InteractionStation {
    private Cook attachedCook;

    /**
     * Creates a bin with a position and a preparation time
     * @param position of the bin instance
     * @param prepTime time that it takes to push an item from the cook's tack to the bin
     */
    public Bin(Vector2 position, int prepTime) {
        super(position, prepTime);
        attachedCook = null;
    }

    /**
     * Removes top item from the stack after confirming interaction is allowed
     */
    @Override
    public void finishInteract() {
        attachedCook.popStack();
    }

    /**
     * A method that checks if there is an item in the cook's stack to be removed
     * @param cook The cook interacting with the bin
     * @return whether interaction is valid
     */
    @Override
    public boolean canInteract(Cook cook) {
        if (!(cook.isStackEmpty())) {
            attachedCook = cook;
            return true;
        } return false;
    }
}
