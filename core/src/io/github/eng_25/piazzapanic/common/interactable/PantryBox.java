package io.github.eng_25.piazzapanic.common.interactable;

import com.badlogic.gdx.math.Vector2;
import io.github.eng_25.piazzapanic.common.entity.Cook;
import io.github.eng_25.piazzapanic.common.ingredient.Ingredient;

/**
 * Based on Ingredient station the cook is at, it will return the relevant ingredient
 * @return Ingredient
 */
public class PantryBox extends InteractionStation {

    private final Ingredient output;

    public PantryBox(Vector2 position, Ingredient output) {
        super(position, 0);
        this.output = output;
    }

    @Override
    public void finishInteract() {

    }

    @Override
    public boolean canInteract(Cook cook) {
        return false;
    }
    // how do I test for this?
}
