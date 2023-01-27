package io.github.eng_25.piazzapanic.common.interactable;

import com.badlogic.gdx.math.Vector2;
import io.github.eng_25.piazzapanic.common.entity.Cook;
import io.github.eng_25.piazzapanic.common.ingredient.BaseIngredient;
import io.github.eng_25.piazzapanic.common.ingredient.Ingredient;

import java.util.List;

public class CookingStation extends InteractionStation {
    // Has a list of ingredients that it checks if the input is one of them
    // (or is a list being inputed?)

    public List<Ingredient> validInputs;
    //public Ingredient output;
    private Cook attachedCook;
    Ingredient toPrep;

    public CookingStation(Vector2 position, List<Ingredient> validInputs, int prepTime) {
        super(position, prepTime);
        this.validInputs = validInputs;
        attachedCook = null;
        toPrep = null;
    }

    @Override
    public void finishInteract() {
        attachedCook.pushStack(toPrep.prepare());
    }

    @Override
    public boolean canInteract(Cook cook) {
        BaseIngredient topOfStack = cook.peekStack();
        if (!(topOfStack instanceof Ingredient)) { return false; }
        Ingredient topIngredient = (Ingredient) topOfStack;
        for (Ingredient ing : validInputs) {
            if (ing.compareTo(topIngredient) == 0) {
                attachedCook = cook;
                toPrep = (Ingredient) cook.popStack();
                return true;
            }
        }
        return false;
    }

    // what are the methods for this?
    //
}
