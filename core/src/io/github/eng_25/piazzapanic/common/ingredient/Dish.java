package io.github.eng_25.piazzapanic.common.ingredient;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.Map;

public class Dish extends BaseIngredient {

    // static map of dishes to be referred to
    public static final Map<String, Dish> DISH_MAP = Map.of(
            "Salad", new Dish("salad", Ingredient.rm.salad),
            "Burger", new Dish("burger", Ingredient.rm.burger)
    );

    /**
     * Returns a copy of a Dish
     * @param dish Dish to copy
     * @return new instance of Dish
     */
    public static Dish copyOf(Dish dish) {
        return new Dish(dish.getName(), dish.getTexture());
    }

    /**
     * Convenience to get a copy of a Dish with its map name
     * @param mapName map name of Dish to copy
     * @return new instance of Dish
     */
    public static Dish getDish(String mapName) { // statically returns copy of given dish name
        return copyOf(DISH_MAP.get(mapName));
    }

    // Dish only has a single texture
    private final TextureRegion texture;

    /**
     * Create a new Dish with given name and texture
     * @param name name of dish
     * @param tex dish TextureRegion
     */
    public Dish(String name, TextureRegion tex) {
        super(name);
        texture = tex;
    }

    @Override
    public TextureRegion getTexture() {
        return texture;
    }
}
