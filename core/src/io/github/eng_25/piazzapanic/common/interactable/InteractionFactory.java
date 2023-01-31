package io.github.eng_25.piazzapanic.common.interactable;

import com.badlogic.gdx.math.Vector2;
import io.github.eng_25.piazzapanic.common.ingredient.Ingredient;

import java.util.ArrayList;
import java.util.List;

/**
 * A factory class used to create multiple InteractionStations of the same type at once.
 * Can create bins, pans, chopping stations, counters and plating stations.
 */
public class InteractionFactory {

    public static final int PREP_TIME_PAN = 30; // pan preparation time in seconds
    public static final int PREP_TIME_CHOP = 15; // chopping preparation time in seconds

    /**
     * Creates and returns a list of given station type
     *
     * @param count     number of stations to be made
     * @param positions Array of Vector2 positions for each station, must be of length equal to count parameter
     * @param type      the lowercase type name of station to be created e.g. "bin", "pan"
     * @return a List of size count filled with InteractionStations of given type
     */
    public static List<InteractionStation> createStations(int count, Vector2[] positions, String type) {
        if (positions.length != count) {
            return null;
        }
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
                for (int i = 0; i < count; i++) {
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
