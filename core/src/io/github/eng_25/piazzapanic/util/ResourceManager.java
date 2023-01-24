package io.github.eng_25.piazzapanic.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Used to centrally load and store all resource: textures, fonts, sounds, etc.
 */
public class ResourceManager {

    public TextureAtlas atlas;
    //public TextureRegion cook;
    public TextureRegion button;

    public BitmapFont font;

    private final AssetManager assetManager = new AssetManager();

    public ResourceManager() {
        // load texture atlas
        assetManager.load("assets/asset/texture.atlas", TextureAtlas.class);
        assetManager.finishLoading(); // ensures loading is finished before any textures can be accessed
        atlas = assetManager.get("assets/asset/texture.atlas", TextureAtlas.class);

        // textures
        button = atlas.findRegion("button2");

        // font
        font = new BitmapFont(Gdx.files.internal("assets/fonts/font.fnt"), atlas.findRegion("pixel"), false);

    }

    public void dispose() {
        assetManager.dispose();
        atlas.dispose();
        font.dispose();
    }

}
