package io.github.eng_25.piazzapanic.common.ingredient;

import com.badlogic.gdx.graphics.Texture;
import io.github.eng_25.piazzapanic.util.ResourceManager;

import java.util.List;
import java.util.Map;

public class Ingredient extends BaseIngredient implements Comparable {

    public static final ResourceManager rm = new ResourceManager();
    public static final Map<String, Ingredient> INGREDIENT_MAP = Map.of(
            "Onion", new Ingredient("onion", rm.onionUnprepared.getTexture(), rm.onionPrepared.getTexture()),
            "Lettuce", new Ingredient("lettuce", rm.lettuceUnprepared.getTexture(), rm.lettucePrepared.getTexture()),
            "Tomato", new Ingredient("tomato", rm.tomatoUnprepared.getTexture(), rm.tomatoPrepared.getTexture()),
            "Meat", new Ingredient("meat", rm.meatUnprepared.getTexture(), rm.meatPrepared.getTexture()),
            "Bun", new Ingredient("bun", rm.bread.getTexture(), rm.bread.getTexture()).prepare() // always prepared
    );

    private boolean isPrepared;
    private final Texture[] textures;

    public Ingredient(String name, Texture unprepared, Texture prepared) {
        super(name);
        isPrepared = false;
        textures = new Texture[]{unprepared, prepared};
    }

    public Ingredient prepare() {
        isPrepared = true;
        return this;
    }

    @Override
    Texture getTexture() {
        return isPrepared ? textures[1] : textures[0];
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof Ingredient) {
            Ingredient ing = (Ingredient) o;
            if ((ing.isPrepared && this.isPrepared) && (ing.getName().equals(this.getName()))) {
                return 0;
            }
            return ing.getName().compareTo(this.getName());
        }
        return -1;
    }
}
