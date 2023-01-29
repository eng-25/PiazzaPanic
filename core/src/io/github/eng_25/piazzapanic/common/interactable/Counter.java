package io.github.eng_25.piazzapanic.common.interactable;

import com.badlogic.gdx.math.Vector2;
import io.github.eng_25.piazzapanic.common.entity.Cook;
import io.github.eng_25.piazzapanic.common.entity.Customer;
import io.github.eng_25.piazzapanic.common.ingredient.Dish;

public class Counter extends InteractionStation {

    private Dish dishWanted;
    private Customer customer;
    public Counter(Vector2 position) {
        super(position, 0);
        dishWanted = null;
        customer = null;
    }

    @Override
    public void finishInteract() {

    }

    @Override
    public boolean canInteract(Cook cook) {
        return false;
    }

    public void attachCustomer(Customer toAttach) {
        customer = toAttach;
        dishWanted = customer.getDish();
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
