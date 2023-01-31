package io.github.eng_25.piazzapanic.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * A helper class used to create UI elements.
 */
public class UIHelper {

    private static final ResourceManager resourceManager = new ResourceManager();


    /**
     * Used to create a TextButton and attach it to a UI table
     *
     * @param buttonText text to be shown on button
     * @param padLeft    amount of left side padding
     * @param padTop     amount of top side padding
     * @param table      The Table to add the button to
     * @param textColour colour of the text
     * @return a TextButton using the ResourceManager button texture
     */
    public static TextButton createTextButton(String buttonText, float padLeft, float padTop, Table table,
                                              Color textColour) {
        TextureRegionDrawable buttonUp = new TextureRegionDrawable(resourceManager.buttonUp);
        TextureRegionDrawable buttonDown = new TextureRegionDrawable(resourceManager.buttonDown);
        BitmapFont font = resourceManager.font;

        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle(buttonUp, buttonDown, null, font);
        TextButton button = new TextButton(buttonText, buttonStyle);
        button.getLabel().setColor(textColour);

        table.add(button).padLeft(padLeft).padTop(padTop);
        table.row();

        return button;
    }

    /**
     * Creates a text button with black text
     *
     * @param buttonText text to be shown on button
     * @param padLeft    amount of left padding
     * @param padTop     amount of top padding
     * @param table      Table to add the button to
     * @return new Textbutton with black text, using ResourceManager button texture
     */
    public static TextButton createTextButton(String buttonText, float padLeft, float padTop, Table table) {
        return createTextButton(buttonText, padLeft, padTop, table, Color.BLACK);
    }

}
