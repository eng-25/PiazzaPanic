package io.github.eng_25.piazzapanic.common;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import io.github.eng_25.piazzapanic.common.entity.Cook;
import io.github.eng_25.piazzapanic.common.ingredient.Ingredient;
import io.github.eng_25.piazzapanic.common.interactable.*;
import io.github.eng_25.piazzapanic.util.ResourceManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PiazzaMap {

    public static final float TILE_SIZE = 32f;

    private final TiledMap map;
    private final OrthogonalTiledMapRenderer mapRenderer;
    private final OrthographicCamera camera;
    private final ArrayList<InteractionStation> interactableList;
    private final Counter[] counters; // for customer logic

    private final CookingStation pan1;
    private final CookingStation pan2;
    private final CookingStation chopping1;
    private final CookingStation chopping2;
    private final Bin bin;
    private final PantryBox bunBox;
    private final PantryBox tomatoBox;
    private final PantryBox onionBox;
    private final PantryBox lettuceBox;
    private final PantryBox meatBox;
    private final PlatingStation plating1;
    private final PlatingStation plating2;
    private final PlatingStation plating3;
    private final Counter counter1;
    private final Counter counter2;
    private final Counter counter3;

    public PiazzaMap(ResourceManager rm, OrthographicCamera camera) {
        this.map = rm.gameMap;
        this.camera = camera;
        // setup TiledMap with 1/32 as tiles are 32x32px
        mapRenderer = new OrthogonalTiledMapRenderer(map, 1 / TILE_SIZE);

        // interaction stations
        final List<Ingredient> panIngredients = List.of(
//                Ingredient.copyOf(Ingredient.INGREDIENT_MAP.get("Meat"))
                Ingredient.getIngredient("Meat")
        );
        final List<Ingredient> choppingIngredients = List.of(
//                Ingredient.copyOf(Ingredient.INGREDIENT_MAP.get("Lettuce")),
//                Ingredient.copyOf(Ingredient.INGREDIENT_MAP.get("Tomato")),
//                Ingredient.copyOf(Ingredient.INGREDIENT_MAP.get("Onion"))
                Ingredient.getIngredient("Lettuce"),
                Ingredient.getIngredient("Tomato"),
                Ingredient.getIngredient("Onion")
        );

        pan1 = new CookingStation(new Vector2(1, 13), panIngredients, 30);
        pan2 = new CookingStation(new Vector2(3, 13), panIngredients, 30);
        chopping1 = new CookingStation(new Vector2(5, 13), choppingIngredients, 15);
        chopping2 = new CookingStation(new Vector2(7, 13), choppingIngredients, 15);
        bin = new Bin(new Vector2(0, 14), 0);
        bunBox = new PantryBox(new Vector2(0, 1), Ingredient.getIngredient("Bun").prepare());
        tomatoBox = new PantryBox(new Vector2(2, 1), Ingredient.getIngredient("Tomato"));
        onionBox = new PantryBox(new Vector2(4, 1), Ingredient.getIngredient("Onion"));
        lettuceBox = new PantryBox(new Vector2(6, 1), Ingredient.getIngredient("Lettuce"));
        meatBox = new PantryBox(new Vector2(8, 1), Ingredient.getIngredient("Meat"));
        plating1 = new PlatingStation(new Vector2(4, 13));
        plating2 = new PlatingStation(new Vector2(6, 13));
        plating3 = new PlatingStation(new Vector2(8, 13));
        counter1 = new Counter(new Vector2(9, 11));
        counter2 = new Counter(new Vector2(9, 9));
        counter3 = new Counter(new Vector2(9, 7));

        interactableList = new ArrayList<>(List.of(pan1, pan2, chopping1, chopping2, bin, bunBox, tomatoBox, onionBox, lettuceBox,
                meatBox, plating1, plating2, plating3, counter1, counter2, counter3));
        counters = new Counter[]{counter1, counter2, counter3};
    }

    public void renderMap(SpriteBatch batch, float delta) {
        // render TileMap
        mapRenderer.setView(camera);
        mapRenderer.render();

        // render progress bars
        interactableList.forEach(station -> station.renderProgress(batch, TILE_SIZE));

        // counter and customer logic
        Arrays.stream(counters).forEach(counter -> {
            if (counter.hasCustomer()) {
                counter.getCustomer().tick(delta, batch);
            }
        });
    }

    public InteractionStation checkInteraction(Cook cook) {
        Vector2 cookPos = cook.getPosition();
        InteractionStation nearest = null;
        float nearestDist = 999;
        for (InteractionStation station : interactableList) {
            // find nearest position
            Vector2 pos = station.getPosition();

            float dist = pos.dst(cookPos);
            if (dist < nearestDist && dist <= 1) {
                nearestDist = dist;
                nearest = station;
            }
        }
        return nearest;
    }

    public Counter getFreeCounter() {
        for (Counter counter : counters) {
            if (!counter.hasCustomer()) {
                return counter;
            }
        }
        return null;
    }

    public void dispose() {
        mapRenderer.dispose();
    }

    public TiledMap getMap() {
        return map;
    }

    public OrthogonalTiledMapRenderer getMapRenderer() {
        return mapRenderer;
    }

    public Counter[] getCounters() {
        return counters;
    }
}
