package io.github.eng_25.piazzapanic.common.interactable;

import com.badlogic.gdx.math.Vector2;
import io.github.eng_25.piazzapanic.common.entity.Cook;

public class Bin extends InteractionStation {

    private Cook attachedcook;

    public Bin(Vector2 position, int prepTime) {
        super(position, prepTime);
        attachedcook = null;
    }

    @Override
    public void finishInteract() {
        attachedcook.popStack();
    }

    @Override
    public boolean canInteract(Cook cook) {
        if (!(cook.isStackEmpty())) {
            attachedcook = cook;
            return true;
        } return false;
    }
}
