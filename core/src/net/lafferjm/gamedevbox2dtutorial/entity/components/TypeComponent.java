package net.lafferjm.gamedevbox2dtutorial.entity.components;

import com.badlogic.ashley.core.Component;

/**
 * Created by laffe on 3/20/2018.
 */

public class TypeComponent implements Component {
    public static final int PLAYER = 0;
    public static final int ENEMY = 1;
    public static final int SCENERY = 3;
    public static final int OTHER = 4;
    public static final int SPRING = 5;

    public int type = OTHER;
}
