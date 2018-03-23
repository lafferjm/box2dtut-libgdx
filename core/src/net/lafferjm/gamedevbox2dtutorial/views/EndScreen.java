package net.lafferjm.gamedevbox2dtutorial.views;

import com.badlogic.gdx.Screen;

import net.lafferjm.gamedevbox2dtutorial.Box2DTutorial;
import net.lafferjm.gamedevbox2dtutorial.DFUtils;

/**
 * Created by laffe on 3/19/2018.
 */

public class EndScreen implements Screen {
    private Box2DTutorial parent;

    public EndScreen(Box2DTutorial box2DTutorial) {
        parent = box2DTutorial;
    }

    @Override
    public void show() {
        DFUtils.log("To the MENU");
        parent.changeScreen(Box2DTutorial.MENU);
    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
