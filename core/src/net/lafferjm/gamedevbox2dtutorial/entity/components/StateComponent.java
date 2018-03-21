package net.lafferjm.gamedevbox2dtutorial.entity.components;

import com.badlogic.ashley.core.Component;

/**
 * Created by laffe on 3/20/2018.
 */

public class StateComponent implements Component {
    public static final int STATE_NORMAL = 0;
    public static final int STATE_JUMPING = 1;
    public static final int STATE_FALLING = 2;
    public static final int STATE_MOVING = 3;
    public static final int STATE_HIT = 4;

    private int state = 0;
    public float time = 0.0f;
    public boolean isLooping = false;

    public void set(int newState) {
        state = newState;
        time = 0.0f;
    }

    public int get() {
        return state;
    }
}
