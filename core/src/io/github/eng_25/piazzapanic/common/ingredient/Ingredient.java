package io.github.eng_25.piazzapanic.common.ingredient;

import com.badlogic.gdx.graphics.Texture;
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

    public static Ingredient copyOf(Ingredient ing) {
        return new Ingredient(ing.getName(), ing.textures[0], ing.textures[1]);
    }

    private boolean isPrepared;
    private final TextureRegion[] textures; // {unprepared, prepared}

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

    @Override
    public int compareTo(Ingredient ing) {
        if ((ing.isPrepared && this.isPrepared) && (ing.getName().equals(this.getName()))) {
            return 0;
        }
        return ing.getName().compareTo(this.getName());
    }

}
