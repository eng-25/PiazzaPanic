package io.github.eng_25.piazzapanic.common.ingredient;

import com.badlogic.gdx.graphics.Texture;

public abstract class BaseIngredient {

    private String name;

    public BaseIngredient(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    abstract Texture getTexture();
}
