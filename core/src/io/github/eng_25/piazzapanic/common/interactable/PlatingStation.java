package io.github.eng_25.piazzapanic.common.interactable;

import com.badlogic.gdx.math.Vector2;
import io.github.eng_25.piazzapanic.common.entity.Cook;
import io.github.eng_25.piazzapanic.common.ingredient.Ingredient;
import io.github.eng_25.piazzapanic.common.ingredient.Recipes;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class PlatingStation extends InteractionStation {

    private List<Ingredient> currentRecipe;

    private Cook attachedCook;

    public PlatingStation(Vector2 position, int prepTime) {
        super(position, prepTime);
        attachedCook = null;
        currentRecipe = new ArrayList<>();
    }
    @Override
    public void finishInteract() {
        if (currentRecipe == Recipes.SALAD.getIngredientList()) {
            attachedCook.pushStack(Recipes.SALAD.getDish());
        } if (currentRecipe == Recipes.BURGER.getIngredientList()) {
            attachedCook.pushStack(Recipes.BURGER.getDish());
        }
    }

    @Override
    public boolean canInteract(Cook cook) {
        for (int i = 0; i < 3; i++) {
            System.out.println(cook.peekStack());
            if (cook.peekStack() instanceof Ingredient && ((Ingredient) cook.peekStack()).isPrepared()) {
                currentRecipe.add((Ingredient) cook.popStack());
            }
        } if (!(cook.isStackEmpty())) {
            return false;
        } attachedCook = cook;
        return true;
    }
}
