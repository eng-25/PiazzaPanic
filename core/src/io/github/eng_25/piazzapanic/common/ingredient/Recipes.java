package io.github.eng_25.piazzapanic.common.ingredient;

import java.util.List;
import java.util.TreeSet;

/**
 * Used to store recipes of dishes
 */
public enum Recipes {

    SALAD(List.of(
            Ingredient.getIngredient("Onion").prepare(),
            Ingredient.getIngredient("Lettuce").prepare(),
            Ingredient.getIngredient("Tomato").prepare()
    ), Dish.getDish("Salad")),
    BURGER(List.of(
            Ingredient.getIngredient("Bun"), // already prepared
            Ingredient.getIngredient("Meat").prepare(),
            Ingredient.getIngredient("Lettuce").prepare()
    ), Dish.getDish("Burger"));

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
    public static Dish checkValidRecipe(List<Ingredient> listOfIngredients) {
        for (Recipes r : Recipes.values()) {
            if (new TreeSet<>(listOfIngredients).equals(new TreeSet<>(r.getIngredientList()))) {
                return r.getDish();
            }
        }
        return null;
    }
}
