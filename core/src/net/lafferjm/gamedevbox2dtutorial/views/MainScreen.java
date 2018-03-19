package net.lafferjm.gamedevbox2dtutorial.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;

import net.lafferjm.gamedevbox2dtutorial.B2dModel;
import net.lafferjm.gamedevbox2dtutorial.Box2DTutorial;

/**
 * Created by laffe on 3/19/2018.
 */

public class MainScreen implements Screen {
    private Box2DTutorial parent;
    private B2dModel model;
    private OrthographicCamera cam;
    private Box2DDebugRenderer debugRenderer;

    public MainScreen(Box2DTutorial box2DTutorial) {
        parent = box2DTutorial;
        model = new B2dModel();
        cam = new OrthographicCamera(32, 24);
        debugRenderer = new Box2DDebugRenderer(true, true, true, true, true, true);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        model.logicStep(delta);
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        debugRenderer.render(model.world, cam.combined);
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
