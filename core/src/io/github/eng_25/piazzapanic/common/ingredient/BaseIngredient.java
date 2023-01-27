package io.github.eng_25.piazzapanic.common.ingredient;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Base class for Ingredients and Dishes, both have a name and must implement a getTexture() method
 */
public abstract class BaseIngredient {

    private final String name;

    public BaseIngredient(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract TextureRegion getTexture();
}
