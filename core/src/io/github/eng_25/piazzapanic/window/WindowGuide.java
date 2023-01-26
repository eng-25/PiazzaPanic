package io.github.eng_25.piazzapanic.window;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import io.github.eng_25.piazzapanic.util.ResourceManager;

/**
 * Closable window showing a guide of 'how to play'
 */
public class WindowGuide extends WindowClosable {

    public WindowGuide(String title, ResourceManager resourceManager) {
        super(title, resourceManager);

        // add guide image to window
        getTitleTable().add(new Image(resourceManager.burger))
                .size(40, 40)
                .left()
                .padLeft(10);
    }

}
