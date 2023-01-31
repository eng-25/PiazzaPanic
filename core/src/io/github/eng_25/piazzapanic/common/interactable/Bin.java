package io.github.eng_25.piazzapanic.common.interactable;

import com.badlogic.gdx.math.Vector2;
import io.github.eng_25.piazzapanic.common.entity.Cook;

/**
 * This class contains the logic for each bin instance
 * which removes the top item from the interacting cook's stack.
 */
public class Bin extends InteractionStation {
    private Cook attachedCook;

    /**
     * Creates a bin with a position and a preparation time of 0
     *
     * @param position of the bin instance
     */
    public Bin(Vector2 position) {
        super(position, 0);
        attachedCook = null;
    }

    /**
     * Pops attached cook's stack
     */
    @Override
    public void finishInteract() {
        attachedCook.popStack();
    }

    /**
     * Checks the passed cook's stack is not empty, attaching it if so.
     *
     * @param cook The cook interacting with the bin.
     * @return true if the stack is not empty, false otherwise.
     */
    @Override
    public boolean canInteract(Cook cook) {
        if (!(cook.isStackEmpty())) {
            attachedCook = cook;
            return true;
        }
        return false;
    }
}
