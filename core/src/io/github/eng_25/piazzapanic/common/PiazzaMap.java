package io.github.eng_25.piazzapanic.common;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import io.github.eng_25.piazzapanic.common.entity.Cook;
import io.github.eng_25.piazzapanic.common.ingredient.Ingredient;
import io.github.eng_25.piazzapanic.common.interactable.CookingStation;
import io.github.eng_25.piazzapanic.common.interactable.InteractionStation;
import io.github.eng_25.piazzapanic.util.ResourceManager;

import java.util.ArrayList;
import java.util.List;

public class PiazzaMap {

    private final TiledMap map;
    private final OrthogonalTiledMapRenderer mapRenderer;
    private final OrthographicCamera camera;
    private final ArrayList<InteractionStation> interactableList;
    private CookingStation pan1;

    public PiazzaMap(ResourceManager rm, OrthographicCamera camera) {
        this.map = rm.gameMap;
        this.camera = camera;
        // setup TiledMap with 1/32 as tiles are 32x32px
        mapRenderer = new OrthogonalTiledMapRenderer(map, 1 / 32f);

        // interaction stations
        final List<Ingredient> panIngredients = List.of(
                Ingredient.copyOf(Ingredient.INGREDIENT_MAP.get("Meat"))
        );
        final List<Ingredient> choppingIngredients = List.of(
                Ingredient.copyOf(Ingredient.INGREDIENT_MAP.get("Lettuce")),
                Ingredient.copyOf(Ingredient.INGREDIENT_MAP.get("Tomato")),
                Ingredient.copyOf(Ingredient.INGREDIENT_MAP.get("Onion"))
        );

        //TODO: _Sam: add all stations here, add all to list below
        pan1 = new CookingStation(new Vector2(1, 14), panIngredients, 3);

        interactableList = new ArrayList<>();
        interactableList.add(pan1);
    }

    public void renderMap() {
        mapRenderer.setView(camera);
        mapRenderer.render();
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

    public void dispose() {
        mapRenderer.dispose();
    }

    public TiledMap getMap() {
        return map;
    }

    public OrthogonalTiledMapRenderer getMapRenderer() {
        return mapRenderer;
    }
}
