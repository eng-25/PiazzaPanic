package io.github.eng_25.piazzapanic.common.interactable;

import com.badlogic.gdx.math.Vector2;
import io.github.eng_25.piazzapanic.common.entity.Cook;

public class Counter extends InteractionStation {
    public Counter(Vector2 position) {
        super(position, 0);
    }

    @Override
    public void finishInteract() {

    }

    @Override
    public boolean canInteract(Cook cook) {
        return false;
    }
}
