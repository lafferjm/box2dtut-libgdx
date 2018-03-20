package net.lafferjm.gamedevbox2dtutorial.views;

/**
 * Created by laffe on 3/19/2018.
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import net.lafferjm.gamedevbox2dtutorial.Box2DTutorial;

public class LoadingScreen implements Screen {

    private Box2DTutorial parent;
    private TextureAtlas atlas;
    private AtlasRegion title;
    private AtlasRegion dash;
    private SpriteBatch spriteBatch;
    private Animation flameAnimation;

    public final int IMAGE = 0;
    public final int FONT = 1;
    public final int PARTY = 2;
    public final int SOUND = 3;
    public final int MUSIC = 4;
    public final int FINISHED = 5;

    private int currentLoadingStage = 0;

    public float countDown = 5f;
    private float stateTime;

    public LoadingScreen(Box2DTutorial box2DTutorial) {
        parent = box2DTutorial;
        spriteBatch = new SpriteBatch();
        spriteBatch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
    }

    @Override
    public void show() {
        parent.assetManager.queueAddLoadingImages();
        parent.assetManager.manager.finishLoading();

        atlas = parent.assetManager.manager.get("images/loading.atlas");
        title = atlas.findRegion("staying-alight-logo");
        dash = atlas.findRegion("loading-dash");

        flameAnimation = new Animation(0.07f, atlas.findRegions("flames"), PlayMode.LOOP);

        parent.assetManager.queueAddImages();
        System.out.println("Loading images....");

        stateTime = 0f;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stateTime += delta;
        TextureRegion currentFrame = (TextureRegion) flameAnimation.getKeyFrame(stateTime, true);

        spriteBatch.begin();
        drawingLoadingBar(currentLoadingStage * 2, currentFrame);
        spriteBatch.draw(title, 135, 250);
        spriteBatch.end();

        if (parent.assetManager.manager.update()) {
            currentLoadingStage += 1;
            switch(currentLoadingStage) {
                case FONT:
                    System.out.println("Loading fonts....");
                    parent.assetManager.queueAddFonts();
                    break;
                case PARTY:
                    System.out.println("Loading Particle Effects....");
                    parent.assetManager.queueAddParticleEffects();
                    break;
                case SOUND:
                    System.out.println("Loading Sounds....");
                    parent.assetManager.queueAddSounds();
                    break;
                case MUSIC:
                    System.out.println("Loading Music....");
                    parent.assetManager.queueAddMusic();
                    break;
                case FINISHED:
                    System.out.println("Finished");
                    break;
            }

            if (currentLoadingStage > 5) {
                countDown -= delta;
                currentLoadingStage = FINISHED;

                if (countDown < 0) {
                    parent.changeScreen(Box2DTutorial.MENU);
                }
            }
        }
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

    private void drawingLoadingBar(int stage, TextureRegion currentFrame) {
        for(int i = 0; i < stage; i++) {
            spriteBatch.draw(currentFrame, 50 + (i * 50), 150, 50, 50);
            spriteBatch.draw(dash, 35 + (i * 50), 140, 80, 80);
        }
    }
}
