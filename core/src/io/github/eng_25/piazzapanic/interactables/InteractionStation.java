package io.github.eng_25.piazzapanic.interactables;

import com.badlogic.gdx.math.Vector2;

/**
 * An abstract class that child classes will inherit from
 */
public class InteractionStation {
    private int prepTimer;
    private Vector2 position;

    /**
     * To check if the ingredient on the top of the cook's stack
     * is usable on the interaction station
     * @param Ingredient
     */
    /*private bool checkInput(Ingredient Ingredient) { // I assume Ingredient isn't implemented as a type yet (if we are trying to use it)

    }*/
    /**
     * Whenever this method is called, timer is reduced by 1
     */
    private void tickTimer () {
        prepTimer -= 1;
    }

}
