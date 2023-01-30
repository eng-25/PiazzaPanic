package io.github.eng_25.piazzapanic.common.interactable;

import com.badlogic.gdx.math.Vector2;
import io.github.eng_25.piazzapanic.common.entity.Cook;
import io.github.eng_25.piazzapanic.common.entity.Customer;
import io.github.eng_25.piazzapanic.common.ingredient.Dish;

/**
 * This class pops the top of the cook's stack if it is a dish that the customer requested.
 * ... (customer functionality)
 */
public class Counter extends InteractionStation {

    private Dish dishWanted;
    private Dish toCustomer;
    private Customer customer;
  
    
     /**
     * Creates a counter with a position.
     * @param position The position of the counter.
     */
    public Counter(Vector2 position) {
        super(position, 0);
        dishWanted = null;
        customer = null;
        toCustomer = null;
    }



    /**
     * ... (customer functionality unknown)
     */
    @Override
    public void finishInteract() {
        if (toCustomer == dishWanted) {
            customer.receiveDish();
            removeCustomer();
        }
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
            return true;
        } return false;
    }

    public void attachCustomer(Customer toAttach) {
        customer = toAttach;
        dishWanted = customer.getDish();
        System.out.println(dishWanted.getName());
        customer.walkToCounter(this);
    }

    public void removeCustomer() {
        customer = null;
    }

    public boolean hasCustomer() {
        return customer != null;
    }

    public Customer getCustomer() {
        return customer;
    }
}
