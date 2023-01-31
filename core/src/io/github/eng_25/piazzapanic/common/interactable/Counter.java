package io.github.eng_25.piazzapanic.common.interactable;

import com.badlogic.gdx.math.Vector2;
import io.github.eng_25.piazzapanic.common.entity.Cook;
import io.github.eng_25.piazzapanic.common.entity.Customer;
import io.github.eng_25.piazzapanic.common.ingredient.Dish;

/**
 * Used as a passthrough from Cook to Customer.
 * Takes dish from interacting cook and handles attached Customer logic.
 */
public class Counter extends InteractionStation {

    private Dish dishWanted;
    private Dish dishGiven;
    private Customer customer;


    /**
     * Creates a counter with a position and no attached customer.
     *
     * @param position The position of the counter.
     */
    public Counter(Vector2 position) {
        super(position, 0);
        dishWanted = null;
        customer = null;
        dishGiven = null;
    }


    /**
     * If dishGiven was equal to the dishWanted, the customer is removed from the Counter and
     * their receiveDish() method is called.
     */
    @Override
    public void finishInteract() {
        if (dishGiven.getName().equals(dishWanted.getName())) {
            customer.receiveDish();
            removeCustomer();
        }
    }

    /**
     * This checks if the top of the cook's stack is a dish, if so pop it and store it as dishGiven.
     *
     * @param cook The cook interacting with the counter.
     * @return true if the top of the cook's stack is an instance of Dish, false otherwise.
     */
    @Override
    public boolean canInteract(Cook cook) {
        if (cook.peekStack() instanceof Dish) {
            dishGiven = (Dish) cook.popStack();
            return true;
        }
        return false;
    }

    /**
     * Used to attach a Customer to this Counter instance, also storing the dish they want and telling them to walk
     * to this counter.
     *
     * @param toAttach customer to attach
     */
    public void attachCustomer(Customer toAttach) {
        customer = toAttach;
        dishWanted = customer.getDish();
        customer.walkToCounter(this);
    }

    /**
     * Used to un-attach a customer
     */
    public void removeCustomer() {
        customer = null;
    }

    /**
     * Checks if counter has a customer attached.
     *
     * @return true if a customer is attached, false otherwise.
     */
    public boolean hasCustomer() {
        return customer != null;
    }

    public Customer getCustomer() {
        return customer;
    }
}
