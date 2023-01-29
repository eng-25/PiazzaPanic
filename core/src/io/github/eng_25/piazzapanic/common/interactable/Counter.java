package io.github.eng_25.piazzapanic.common.interactable;

import com.badlogic.gdx.math.Vector2;
import io.github.eng_25.piazzapanic.common.entity.Cook;
import io.github.eng_25.piazzapanic.common.ingredient.Dish;

/**
 * This class pops the top of the cook's stack if it is a dish that the customer requested.
 * ... (customer functionality)
 */
public class Counter extends InteractionStation {

    private Dish toCustomer;

    private Cook attachedCook;

    /**
     * Creates a counter with a position.
     * @param position The position of the counter.
     */
    public Counter(Vector2 position) {
        super(position, 0);
        toCustomer = null;
        attachedCook = null;
    }

    /**
     * ... (customer functionality unknown)
     */
    @Override
    public void finishInteract() {
        // something to do with checking that toCustomer is the same as what the customer requests
        // (not sure how this would be implemented)
    }

    /**
     * This checks if the top of the cook's stack is a dish, if so pop it and save it.
     * @param cook The cook interacting with the counter.
     * @return Whether interaction is valid.
     */
    @Override
    public boolean canInteract(Cook cook) {
        if (cook.peekStack() instanceof Dish) {
            toCustomer = (Dish) cook.popStack();
            attachedCook = cook;
            return true;
        } return false;
    }
}
