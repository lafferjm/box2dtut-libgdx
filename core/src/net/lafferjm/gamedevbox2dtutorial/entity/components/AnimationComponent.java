package net.lafferjm.gamedevbox2dtutorial.entity.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.utils.IntMap;

/**
 * Created by laffe on 3/20/2018.
 */

public class AnimationComponent implements Component {
    public IntMap<Animation> animations = new IntMap<Animation>();
}
