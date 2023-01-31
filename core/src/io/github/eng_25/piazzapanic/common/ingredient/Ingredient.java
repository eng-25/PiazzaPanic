package io.github.eng_25.piazzapanic.common.ingredient;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import io.github.eng_25.piazzapanic.util.ResourceManager;

import java.util.Map;

public class Ingredient extends BaseIngredient implements Comparable<Ingredient> {

    // map of ingredients to be referred to
    public static final ResourceManager rm = new ResourceManager();
    public static final Map<String, Ingredient> INGREDIENT_MAP = Map.of(
            "Onion", new Ingredient("onion", rm.onionUnprepared, rm.onionPrepared),
            "Lettuce", new Ingredient("lettuce", rm.lettuceUnprepared, rm.lettucePrepared),
            "Tomato", new Ingredient("tomato", rm.tomatoUnprepared, rm.tomatoPrepared),
            "Meat", new Ingredient("meat", rm.meatUnprepared, rm.meatPrepared),
            "Bun", new Ingredient("bun", rm.bun, rm.bun).prepare() // always prepared
    );

    /**
     * Returns a copy of Ingredient given
     *
     * @param ing Ingredient to copy
     * @return new instance of Ingredient
     */
    public static Ingredient copyOf(Ingredient ing) {
        return new Ingredient(ing.getName(), ing.textures[0], ing.textures[1]);
    }

    /**
     * Convenience to get a copy of a Ingredient with its map name
     *
     * @param mapName map name of Ingredient to copy
     * @return new instance of Ingredient
     */
    public static Ingredient getIngredient(String mapName) { // statically returns a copy of given ingredient
        return copyOf(INGREDIENT_MAP.get(mapName));
    }

    private boolean isPrepared;
    private final TextureRegion[] textures; // {unprepared, prepared}

    /**
     * Creates a new Ingredient, mapping textures to a TextureRegion array in order of [unprepared, prepared]
     *
     * @param name       name of Ingredient
     * @param unprepared unprepared texture
     * @param prepared   prepared texture
     */
    public Ingredient(String name, TextureRegion unprepared, TextureRegion prepared) {
        super(name);
        isPrepared = false;
        textures = new TextureRegion[]{unprepared, prepared};
    }

    /**
     * sets isPrepared to true
     * returns itself, so ingredients can be created and prepared in the same line
     * e.g. Ingredient ing = new Ingredient().prepare()
     *
     * @return itself, now prepared
     */
    public Ingredient prepare() {
        isPrepared = true;
        return this;
    }

    public boolean isPrepared() {
        return isPrepared;
    }

    /**
     * Gets current ingredient texture
     *
     * @return prepared texture if isPrepared true, unprepared texture otherwise
     */
    @Override
    public TextureRegion getTexture() {
        return isPrepared ? textures[1] : textures[0];
    }

    /**
     * Used to compare 2 Ingredients, based on isPrepared and name.
     *
     * @param ing the object to be compared.
     * @return true if isPrepared boolean and name String are both equal, false otherwise.
     */
    @Override
    public int compareTo(Ingredient ing) {
        if ((ing.isPrepared && this.isPrepared) && (ing.getName().equals(this.getName()))) {
            return 0;
        }
        return ing.getName().compareTo(this.getName());
    }

}
