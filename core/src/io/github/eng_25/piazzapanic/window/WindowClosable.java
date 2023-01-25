package io.github.eng_25.piazzapanic.window;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import io.github.eng_25.piazzapanic.util.ResourceManager;

public abstract class WindowClosable extends Window {

    private final ImageButton.ImageButtonStyle closeButtonStyle;

    public WindowClosable(String title, ResourceManager resourceManager) {
        super(title, new WindowStyle(
                resourceManager.font, Color.BLACK, new TextureRegionDrawable(resourceManager.windowTex)
        ));

        closeButtonStyle = new ImageButton.ImageButtonStyle();
        closeButtonStyle.imageUp = new TextureRegionDrawable(resourceManager.closeButton);
        addCloseButton();

        getTitleLabel().setVisible(false); // removes visibility of window's title text
        setClip(false); // no auto clipping
        setTransform(true); // allows window to be transformed
    }

    private void addCloseButton() {
        final Button closeButton = new ImageButton(closeButtonStyle);
        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setVisible(false);
            }
        });

        getTitleTable().add(closeButton) // add close button to window's table so that it is rendered
                .size(40, 40)
                .padRight(10)
                .padTop(40);
    }
}
