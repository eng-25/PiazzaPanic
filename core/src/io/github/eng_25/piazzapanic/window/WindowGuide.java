package io.github.eng_25.piazzapanic.window;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import io.github.eng_25.piazzapanic.util.ResourceManager;

/**
 * Closable window showing a guide of 'how to play'
 */
public class WindowGuide extends WindowClosable {

    public WindowGuide(String title, ResourceManager resourceManager) {
        super(title, resourceManager);

        // add guide image to window
        TextureRegion howToPlay = resourceManager.howToPlay;
        getTitleTable().add(new Image(howToPlay))
                .size(howToPlay.getRegionWidth(), howToPlay.getRegionHeight());
    }

}
