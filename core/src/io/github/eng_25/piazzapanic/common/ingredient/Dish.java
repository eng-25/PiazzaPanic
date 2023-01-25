package io.github.eng_25.piazzapanic.common.ingredient;

import com.badlogic.gdx.graphics.Texture;
import io.github.eng_25.piazzapanic.util.ResourceManager;

import java.util.Map;

public class Dish extends BaseIngredient {

    // map of dishes to be referred to
    private static final ResourceManager rm = new ResourceManager();
    public static final Map<String, Dish> DISH_MAP = Map.of(
            "Salad", new Dish("salad", rm.salad.getTexture()),
            "Burger", new Dish("burger", rm.burger.getTexture())
    );

    // Dish only has a single texture
    private Texture texture;

    public Dish(String name, Texture tex) {
        super(name);
        texture = tex;
    }

    @Override
    Texture getTexture() {
        return texture;
    }
}
