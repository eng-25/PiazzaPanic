package io.github.eng_25.piazzapanic.common.interactable;

import com.badlogic.gdx.math.Vector2;
import io.github.eng_25.piazzapanic.common.entity.Cook;
import io.github.eng_25.piazzapanic.common.ingredient.Dish;
import io.github.eng_25.piazzapanic.common.ingredient.Ingredient;
import io.github.eng_25.piazzapanic.common.ingredient.Recipes;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class PlatingStation extends InteractionStation {

    private List<Ingredient> currentRecipe;

    private Cook attachedCook;
    private Dish output;

    public PlatingStation(Vector2 position) {
        super(position, 0);
        attachedCook = null;
        output = null;
        currentRecipe = new ArrayList<>();
    }

    private void checkForDish() {
        output = Recipes.checkValidRecipe(currentRecipe);
    }
    @Override
    public void finishInteract() {
        checkForDish();
        if (output == null && currentRecipe.size() >= 3) {
            currentRecipe.clear();
        } else {
            if (!attachedCook.isStackFull() && output != null) { // double check space in stack for Dish output
                attachedCook.pushStack(output);
            }
        }
    }

    @Override
    public boolean canInteract(Cook cook) {
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
