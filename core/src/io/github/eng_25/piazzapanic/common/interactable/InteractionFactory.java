package io.github.eng_25.piazzapanic.common.interactable;

import com.badlogic.gdx.math.Vector2;
import io.github.eng_25.piazzapanic.common.ingredient.Ingredient;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

public class InteractionFactory {

    public static final int PREP_TIME_PAN = 30;
    public static final int PREP_TIME_CHOP = 15;

    public static List<InteractionStation> createStations(int count, Vector2[] positions, String type) {
        if (positions.length != count && !type.equalsIgnoreCase("pantry")) { return null; }
        List<InteractionStation> stationList = new ArrayList<>();
        switch (type) {
            case "bin":
                // position
                for (int i = 0; i < count; i++) {
                    stationList.add(new Bin(positions[i]));
                }
                break;
            case "pan":
                // position, List<Ingredient> containing meat
                final List<Ingredient> panIngredients = List.of(
                        Ingredient.getIngredient("Meat")
                );
                for (int i = 0; i < count; i++) {
                    stationList.add(new CookingStation(positions[i], panIngredients, PREP_TIME_PAN));
                }
                break;
            case "chopping":
                // position, List<Ingredient> containing veggies
                final List<Ingredient> choppingIngredients = List.of(
                        Ingredient.getIngredient("Lettuce"),
                        Ingredient.getIngredient("Tomato"),
                        Ingredient.getIngredient("Onion")
                );
                for (int i=0; i<count; i++) {
                    stationList.add(new CookingStation(positions[i], choppingIngredients, PREP_TIME_CHOP));
                }
                break;
            case "counter":
                // position
                for (int i = 0; i < count; i++) {
                    stationList.add(new Counter(positions[i]));
                }
                break;
            case "plating":
                // position
                for (int i = 0; i < count; i++) {
                    stationList.add(new PlatingStation(positions[i]));
                }
                break;
            default:
                return null;
        }
        return stationList;
    }
}
