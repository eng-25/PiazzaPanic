package io.github.eng_25.piazzapanic.common.interactable;

import com.badlogic.gdx.math.Vector2;
import io.github.eng_25.piazzapanic.common.entity.Cook;
import io.github.eng_25.piazzapanic.common.ingredient.BaseIngredient;
import io.github.eng_25.piazzapanic.common.ingredient.Ingredient;

import java.util.List;

public class CookingStation extends InteractionStation {

    public List<Ingredient> validInputs;
    private Cook attachedCook;
    private Ingredient toPrep;

    public CookingStation(Vector2 position, List<Ingredient> validInputs, int prepTime) {
        super(position, prepTime);
        this.validInputs = validInputs;
        attachedCook = null;
        toPrep = null;
    }

    @Override
    public void finishInteract() {
        attachedCook.setCanMove(true);
        attachedCook.pushStack(toPrep.prepare());
    }

    @Override
    public boolean canInteract(Cook cook) {
        if (isWorking()) {
            return false;
        } // added as check here since this method will pop from stack
        BaseIngredient topOfStack = cook.peekStack();
        if (!(topOfStack instanceof Ingredient)) {
            return false;
        }
        Ingredient topIngredient = (Ingredient) topOfStack;
        for (Ingredient ing : validInputs) {
            if (ing.compareTo(topIngredient) == 0 && !(topIngredient.isPrepared())) {
                attachedCook = cook;
                toPrep = Ingredient.copyOf((Ingredient) cook.popStack());
                attachedCook.setCanMove(false);
                return true;
            }
        }
        return false;
    }

}
