package io.github.eng_25.piazzapanic.common.interactable;

import com.badlogic.gdx.math.Vector2;
import io.github.eng_25.piazzapanic.common.entity.Cook;
import io.github.eng_25.piazzapanic.common.ingredient.BaseIngredient;
import io.github.eng_25.piazzapanic.common.ingredient.Ingredient;

import java.util.List;

/**
 * Used to prepare Ingredients over a given preparation time
 */
public class CookingStation extends InteractionStation {

    public List<Ingredient> validInputs;
    private Cook attachedCook;
    private Ingredient toPrep;

    /**
     * Creates a cooking station with a position, a list of valid inputs and a preparation time.
     * Initially has no attached cook and holds no ingredient to prepare.
     *
     * @param position    The position of the cooking station.
     * @param validInputs A list of Ingredients that the cooking station will prepare.
     * @param prepTime    The time taken for the preparation to be completed.
     */
    public CookingStation(Vector2 position, List<Ingredient> validInputs, int prepTime) {
        super(position, prepTime);
        this.validInputs = validInputs;
        attachedCook = null;
        toPrep = null;
    }

    /**
     * Allows the attached cook to move again, and pushes the now prepared Ingredient onto their stack
     */
    @Override
    public void finishInteract() {
        attachedCook.setCanMove(true);
        attachedCook.pushStack(toPrep.prepare());
    }

    /**
     * Checks if the top of the cook's stack is valid for interaction.
     * This is true if it is an instance of Ingredient, non-prepared and an ingredient specified by the list validInputs.
     * If valid, attached cook is set to cook, and toPrep is set to the unprepared Ingredient popped from the cook's stack
     *
     * @param cook The cook interacting with the cooking station.
     * @return true if the interaction is valid, false otherwise.
     */
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
