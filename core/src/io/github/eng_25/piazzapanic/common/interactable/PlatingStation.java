package io.github.eng_25.piazzapanic.common.interactable;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import io.github.eng_25.piazzapanic.common.entity.Cook;
import io.github.eng_25.piazzapanic.common.ingredient.Dish;
import io.github.eng_25.piazzapanic.common.ingredient.Ingredient;
import io.github.eng_25.piazzapanic.common.ingredient.Recipes;
import io.github.eng_25.piazzapanic.util.ResourceManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Used to store multiple Ingredients and build Dishes based off the recipes in Recipes enum.
 */
public class PlatingStation extends InteractionStation {

    private final List<Ingredient> heldIngredients;
    private Cook attachedCook;
    private Dish output;

    ResourceManager rm = new ResourceManager();
    TextureRegion[] chars = rm.platingChars;

    /**
     * Creates an instance of a plating station with a position and 0 timer.
     * Initially, no cook is attached, no dish is to be output and no ingredients are held.
     *
     * @param position The position of the plating station.
     */
    public PlatingStation(Vector2 position) {
        super(position, 0);
        attachedCook = null;
        output = null;
        heldIngredients = new ArrayList<>();
    }

    /**
     * Checks if the held ingredients list contains a valid recipe.
     * If so, sets output to that Dish. If not, sets output to null.
     */
    private void checkForDish() {
        output = Recipes.checkValidRecipe(heldIngredients);
    }


    /**
     * Used to clear the heldIngredients list
     */
    private void clearStation() {
        heldIngredients.clear();
    }

    /**
     * Adds resulting Dish to attached cook's stack if this station contained a valid recipe after interaction.
     * If not, and the station now holds 3 or more ingredients, it is cleared.
     */
    @Override
    public void finishInteract() {
        checkForDish();
        // As current recipe will only be >= 3
        // if it's a recipe or no longer possible to be a recipe, the list is cleared.
        if (output == null && heldIngredients.size() >= 3) {
            clearStation();
        } else {
            if (!attachedCook.isStackFull() && output != null) { // double check space in stack for Dish output
                attachedCook.pushStack(output);
                clearStation();
            }
        }
    }

    /**
     * Adds item at the top of the stack if it is a prepared ingredient to the plating station.
     *
     * @param cook The cook interacting with plating station.
     * @return true if interaction is valid, false otherwise.
     */
    @Override
    public boolean canInteract(Cook cook) {
        if (isWorking()) {
            return false;
        }
        if (cook.peekStack() instanceof Ingredient) {
            if (((Ingredient) cook.peekStack()).isPrepared()) {
                attachedCook = cook;
                heldIngredients.add((Ingredient) attachedCook.popStack());
                return true;
            }
        }
        return false;
    }

    @Override
    public void renderProgress(SpriteBatch batch, float tileSize) {
        super.renderProgress(batch, tileSize);

        TextureRegion firstChar = chars[heldIngredients.size()+1];
        batch.draw(firstChar, position.x, position.y+1.6f, 0.5f, 0.5f);
        batch.draw(chars[0], position.x+0.3f, position.y+1.6f, 0.5f, 0.5f);
        batch.draw(chars[4], position.x+0.6f, position.y+1.6f, 0.5f, 0.5f);
    }
}
