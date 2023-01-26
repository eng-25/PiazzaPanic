package io.github.eng_25.piazzapanic.common.ingredient;

import java.util.Collections;
import java.util.List;

/**
 * Used to store recipes of dishes
 */
public enum Recipes {

    SALAD(List.of(
            Ingredient.INGREDIENT_MAP.get("Onion").prepare(),
            Ingredient.INGREDIENT_MAP.get("Lettuce").prepare(),
            Ingredient.INGREDIENT_MAP.get("Tomato").prepare()
    ), Dish.DISH_MAP.get("Salad")),
    BURGER(List.of(
            Ingredient.INGREDIENT_MAP.get("Bun"),
            Ingredient.INGREDIENT_MAP.get("Meat").prepare(),
            Ingredient.INGREDIENT_MAP.get("Lettuce").prepare()
    ), Dish.DISH_MAP.get("Burger"));

    private final List<Ingredient> ingredientList;
    private final Dish dishOut;

    Recipes(List<Ingredient> ingList, Dish dish) {
        ingredientList = ingList;
        dishOut = dish;
    }

    public Dish getDish() {
        return dishOut;
    }

    public List<Ingredient> getIngredientList() {
        return ingredientList;
    }

    /**
     * Checks a list of ingredients against all recipes in the Enum.
     *
     * @param listOfIngredients the list to be checked
     * @return Dish if list passed is valid recipe, null if not
     */
    public Dish checkValidRecipe(List<Ingredient> listOfIngredients) {
        Collections.sort(listOfIngredients);

        for (Recipes r : Recipes.values()) {
            if (listOfIngredients.equals(r.getIngredientList())) {
                return r.getDish();
            }
        }
        return null;
    }
}
