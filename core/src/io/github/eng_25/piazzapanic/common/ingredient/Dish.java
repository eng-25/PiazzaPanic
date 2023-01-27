package io.github.eng_25.piazzapanic.common.ingredient;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import io.github.eng_25.piazzapanic.util.ResourceManager;

import java.util.Map;

public class Dish extends BaseIngredient {

    // map of dishes to be referred to
    private static final ResourceManager rm = new ResourceManager();
    public static final Map<String, Dish> DISH_MAP = Map.of(
            "Salad", new Dish("salad", rm.salad),
            "Burger", new Dish("burger", rm.burger)
    );

    public static Dish copyOf(Dish dish) {
        return new Dish(dish.getName(), dish.getTexture());
    }

    // Dish only has a single texture
    private TextureRegion texture;

    public Dish(String name, TextureRegion tex) {
        super(name);
        texture = tex;
    }

    @Override
    public TextureRegion getTexture() {
        return texture;
    }
}
