package net.lafferjm.gamedevbox2dtutorial.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;

import net.lafferjm.gamedevbox2dtutorial.B2dModel;
import net.lafferjm.gamedevbox2dtutorial.Box2DTutorial;
import net.lafferjm.gamedevbox2dtutorial.controller.KeyboardController;

/**
 * Created by laffe on 3/19/2018.
 */

public class MainScreen implements Screen {
    private Box2DTutorial parent;
    private B2dModel model;
    private OrthographicCamera cam;
    private Box2DDebugRenderer debugRenderer;
    private KeyboardController controller;
    private final AtlasRegion playerTex;
    private SpriteBatch spriteBatch;
    private TextureAtlas atlas;

    public MainScreen(Box2DTutorial box2DTutorial) {
        parent = box2DTutorial;
        controller = new KeyboardController();
        cam = new OrthographicCamera(32, 24);
        model = new B2dModel(controller, cam, parent.assetManager);
        debugRenderer = new Box2DDebugRenderer(true, true, true, true, true, true);

        spriteBatch = new SpriteBatch();
        spriteBatch.setProjectionMatrix(cam.combined);

        atlas = parent.assetManager.manager.get("images/game.atlas");
        playerTex = atlas.findRegion("player");
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(controller);
    }

    @Override
    public void render(float delta) {
        model.logicStep(delta);
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        debugRenderer.render(model.world, cam.combined);

        spriteBatch.begin();
        spriteBatch.draw(playerTex, model.player.getPosition().x - 1, model.player.getPosition().y - 1,2 , 2);
        spriteBatch.end();
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
        spriteBatch.dispose();
    }
}
