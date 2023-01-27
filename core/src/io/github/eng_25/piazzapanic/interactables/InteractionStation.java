package io.github.eng_25.piazzapanic.interactables;

import com.badlogic.gdx.math.Vector2;
import io.github.eng_25.piazzapanic.common.ingredient.Ingredient;

/**
 * An abstract class that child classes will inherit from
 */
public class InteractionStation {
    private int prepTimer;
    private Vector2 position;



    private void Interact() {
        // Define interaction that all stations will use
    }

    /**
     * To check if the ingredient on the top of the cook's stack
     * is usable on the interaction station
     * @param Ingredient
     */

    /*private bool checkInput(Ingredient Ingredient) {

    }*/
    /**
     * Whenever this method is called, timer is reduced by 1
     */
    private void tickTimer () {
        prepTimer -= 1;
    }

}
