package io.github.eng_25.piazzapanic.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

/**
 * Used to centrally load and store all resource: textures, fonts, sounds, etc.
 */
public class ResourceManager {

    public TextureAtlas atlas;
    public TextureRegion cook;
    public TextureRegion buttonUp;
    public TextureRegion buttonDown;
    public TextureRegion burger;
    public TextureRegion salad;
    public TextureRegion closeButton;
    public TextureRegion lettucePrepared;
    public TextureRegion lettuceUnprepared;
    public TextureRegion onionPrepared;
    public TextureRegion onionUnprepared;
    public TextureRegion meatPrepared;
    public TextureRegion meatUnprepared;
    public TextureRegion tomatoPrepared;
    public TextureRegion tomatoUnprepared;
    public TextureRegion bun;
    public TextureRegion windowTex;
    public TextureRegion emptyStack;
    public TextureRegion stack;
    public TextureRegion customer;
    public TextureRegion barBg;
    public TextureRegion barFg;

    public BitmapFont font;

    public TiledMap gameMap;

    private final AssetManager assetManager = new AssetManager();

    public ResourceManager() {
        // load texture atlas
        assetManager.load("assets/asset/texture.atlas", TextureAtlas.class);
        assetManager.finishLoading(); // ensures loading is finished before any textures can be accessed
        atlas = assetManager.get("assets/asset/texture.atlas", TextureAtlas.class);

        // textures
        buttonUp = atlas.findRegion("button_up");
        buttonDown = atlas.findRegion("button_down");
        closeButton = atlas.findRegion("close_button");
        windowTex = atlas.findRegion("window");

        burger = atlas.findRegion("burger");
        salad = atlas.findRegion("salad");
        lettucePrepared = atlas.findRegion("lettuce_prepared");
        lettuceUnprepared = atlas.findRegion("lettuce_unprepared");
        onionPrepared = atlas.findRegion("onion_prepared");
        onionUnprepared = atlas.findRegion("onion_unprepared");
        meatPrepared = atlas.findRegion("meat_prepared");
        meatUnprepared = atlas.findRegion("meat_unprepared");
        tomatoPrepared = atlas.findRegion("tomato_prepared");
        tomatoUnprepared = atlas.findRegion("tomato_unprepared");
        bun = atlas.findRegion("bun_prepared");
        cook = atlas.findRegion("cook");
        emptyStack = atlas.findRegion("empty_stack");
        stack = atlas.findRegion("stack");
        customer = atlas.findRegion("customer");
        barBg = atlas.findRegion("bar_bg");
        barFg = atlas.findRegion("bar_fg");

        // font
        font = new BitmapFont(Gdx.files.internal("assets/fonts/font.fnt"), false);

        // map
        gameMap = new TmxMapLoader().load("assets/maps/kitchen.tmx");

    }

    public void dispose() {
        assetManager.dispose();
        atlas.dispose();
        font.dispose();
    }

}
