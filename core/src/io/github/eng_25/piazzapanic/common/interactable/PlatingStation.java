package io.github.eng_25.piazzapanic.common.interactable;

import com.badlogic.gdx.math.Vector2;
import io.github.eng_25.piazzapanic.common.entity.Cook;
import io.github.eng_25.piazzapanic.common.ingredient.Dish;
import io.github.eng_25.piazzapanic.common.ingredient.Ingredient;
import io.github.eng_25.piazzapanic.common.ingredient.Recipes;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Will convert the combination of ingredients given into a dish if it matches a designated recipe.
 */
public class PlatingStation extends InteractionStation {

    private List<Ingredient> currentRecipe;

    private Cook attachedCook;
    private Dish output;

    /**
     * Creates an instance of a plating station with a position.
     * @param position The position of the plating station.
     */
    public PlatingStation(Vector2 position) {
        super(position, 0);
        attachedCook = null;
        output = null;
        currentRecipe = new ArrayList<>();
    }

    /**
     * Checks if dish is a valid recipe.
     */
    private void checkForDish() {
        output = Recipes.checkValidRecipe(currentRecipe);
    }

    /**
     * Adds item to the stack if it is a valid recipe.
     */
    @Override
    public void finishInteract() {
        checkForDish();
        // As current recipe will only be >= 3
        // if it's a recipe or no longer possible to be a recipe, the list is cleared.
        if (output == null && currentRecipe.size() >= 3) {
            currentRecipe.clear();
        } else {
            if (!attachedCook.isStackFull() && output != null) { // double check space in stack for Dish output
                attachedCook.pushStack(output);
            }
        }
    }

    /**
     * Adds item at the top of the stack if it is a prepared ingredient to the plating station.
     * @param cook The cook interacting with plating station.
     * @return Whether interaction is valid.
     */
    @Override
    public boolean canInteract(Cook cook) {
        if (isWorking()) { return false; }
        if (cook.peekStack() instanceof Ingredient) {
            if (((Ingredient) cook.peekStack()).isPrepared()) {
                attachedCook = cook;
                currentRecipe.add((Ingredient) attachedCook.popStack());
                return true;
            }
        }
        return false;
    }
}
