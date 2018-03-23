package net.lafferjm.gamedevbox2dtutorial.entity.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.OrthographicCamera;

/**
 * Created by laffe on 3/20/2018.
 */

public class PlayerComponent implements Component {
    public OrthographicCamera cam = null;
    public boolean onPlatform = false;
    public boolean onSpring = false;
    public boolean isDead = false;
}
