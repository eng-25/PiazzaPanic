package io.github.eng_25.piazzapanic.common;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import io.github.eng_25.piazzapanic.util.ResourceManager;

public class PiazzaMap {

    private final TiledMap map;
    private final OrthogonalTiledMapRenderer mapRenderer;
    private final OrthographicCamera camera;

    public PiazzaMap(ResourceManager rm, OrthographicCamera camera) {
        this.map = rm.gameMap;
        this.camera = camera;
        // setup TiledMap with 1/32 as tiles are 32x32px
        mapRenderer = new OrthogonalTiledMapRenderer(map, 1 / 32f);
    }

    public void renderMap() {
        mapRenderer.setView(camera);
        mapRenderer.render();
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
