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
    public TextureRegion speech;
    public TextureRegion howToPlay;
    public TextureRegion logo;
    public TextureRegion[] platingChars;

    public TextureRegion[][] reputation_textures;

    public BitmapFont font;

    public TiledMap gameMap;

    private final AssetManager assetManager = new AssetManager();

    /**
     * Creates a new ResourceManager, loading all assets, fonts, maps and sounds
     */
    public ResourceManager() {
        // load texture atlas
        assetManager.load("asset/texture.atlas", TextureAtlas.class);
        assetManager.finishLoading(); // ensures loading is finished before any textures can be accessed
        atlas = assetManager.get("asset/texture.atlas", TextureAtlas.class);

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
        speech = atlas.findRegion("speech_bubble");
        howToPlay = atlas.findRegion("how_to_play");
        logo = atlas.findRegion("logo");

        reputation_textures = atlas.findRegion("reputation_region").split(96, 32);
        platingChars = new TextureRegion[]{
                atlas.findRegion("slash"),
                atlas.findRegion("zero"),
                atlas.findRegion("one"),
                atlas.findRegion("two"),
                atlas.findRegion("three")
        };

        // font
        font = new BitmapFont(Gdx.files.internal("fonts/font.fnt"), false);

        // map
        gameMap = new TmxMapLoader().load("maps/kitchen.tmx");

    }

    public void dispose() {
        assetManager.dispose();
        atlas.dispose();
        font.dispose();
    }

}
