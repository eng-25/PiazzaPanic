package io.github.eng_25.piazzapanic.common.ingredient;

import java.util.List;
import java.util.TreeSet;

/**
 * Used to store recipes of dishes
 */
public enum Recipes {

    SALAD(List.of(
            getIngredient("Onion").prepare(),
            getIngredient("Lettuce").prepare(),
            getIngredient("Tomato").prepare()
    ), getDish("Salad")),
    BURGER(List.of(
            getIngredient("Bun"), // already prepared
            getIngredient("Meat").prepare(),
            getIngredient("Lettuce").prepare()
    ), getDish("Burger"));
    // Add new recipes here:
//    BURGER2(List.of(
//            getIngredient("Bun"),
//            getIngredient("Tomato").prepare()
//    ), getDish("Burger"));

    private final List<Ingredient> ingredientList;
    private final Dish dishOut;

    /**
     * Each Recipe constant requires an ingredient list and an output dish
     *
     * @param ingList List of ingredients
     * @param dish    output Dish
     */
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

    public static Ingredient getIngredient(String mapName) {
        return Ingredient.getIngredient(mapName);
    }

    public static Dish getDish(String mapName) {
        return Dish.getDish(mapName);
    }
}
