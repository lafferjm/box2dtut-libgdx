package net.lafferjm.gamedevbox2dtutorial.views;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import net.lafferjm.gamedevbox2dtutorial.Box2DTutorial;
import net.lafferjm.gamedevbox2dtutorial.LevelFactory;
import net.lafferjm.gamedevbox2dtutorial.controller.KeyboardController;
import net.lafferjm.gamedevbox2dtutorial.entity.systems.AnimationSystem;
import net.lafferjm.gamedevbox2dtutorial.entity.systems.CollisionSystem;
import net.lafferjm.gamedevbox2dtutorial.entity.systems.LevelGenerationSystem;
import net.lafferjm.gamedevbox2dtutorial.entity.systems.PhysicsDebugSystem;
import net.lafferjm.gamedevbox2dtutorial.entity.systems.PhysicsSystem;
import net.lafferjm.gamedevbox2dtutorial.entity.systems.PlayerControlSystem;
import net.lafferjm.gamedevbox2dtutorial.entity.systems.RenderingSystem;


/**
 * Created by laffe on 3/19/2018.
 */

public class MainScreen implements Screen {
    private Box2DTutorial parent;
    private OrthographicCamera cam;
    private KeyboardController controller;
    private SpriteBatch sb;
    private PooledEngine engine;
    private LevelFactory lvlFactory;

    private Sound ping;
    private Sound boing;
    private TextureAtlas atlas;


    public MainScreen(Box2DTutorial box2DTutorial) {
        parent = box2DTutorial;
        parent.assetManager.queueAddSounds();
        parent.assetManager.manager.finishLoading();

        atlas = parent.assetManager.manager.get("images/game.atlas", TextureAtlas.class);
        ping = parent.assetManager.manager.get("sounds/ping.wav", Sound.class);
        boing = parent.assetManager.manager.get("sounds/boing.wav", Sound.class);
        controller = new KeyboardController();
        engine = new PooledEngine();
        lvlFactory = new LevelFactory(engine, atlas.findRegion("player"));

        sb = new SpriteBatch();
        RenderingSystem renderingSystem = new RenderingSystem(sb);
        cam = renderingSystem.getCamera();
        sb.setProjectionMatrix(cam.combined);

        engine.addSystem(new AnimationSystem());
        engine.addSystem(new PhysicsSystem(lvlFactory.world));
        engine.addSystem(renderingSystem);
        engine.addSystem(new PhysicsDebugSystem(lvlFactory.world, renderingSystem.getCamera()));
        engine.addSystem((new CollisionSystem()));
        engine.addSystem(new PlayerControlSystem(controller));

        engine.addSystem(new LevelGenerationSystem(lvlFactory));

        lvlFactory.createPlayer(atlas.findRegion("player"), cam);
        lvlFactory.createFloor(atlas.findRegion("player"));
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(controller);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        engine.update(delta);
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
        sb.dispose();
    }

}
