package net.lafferjm.gamedevbox2dtutorial.views;

/**
 * Created by laffe on 3/19/2018.
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import net.lafferjm.gamedevbox2dtutorial.Box2DTutorial;

public class LoadingScreen implements Screen {

    private Stage stage;
    private Box2DTutorial parent;
    private TextureAtlas atlas;
    private AtlasRegion title;
    private AtlasRegion dash;
    private AtlasRegion background;
    private AtlasRegion copyright;
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
    private Image titleImage;
    private Image copyrightImage;
    private Table table;
    private Table loadingTable;

    public LoadingScreen(Box2DTutorial box2DTutorial) {
        parent = box2DTutorial;
        stage = new Stage(new ScreenViewport());

        loadAssets();

        parent.assetManager.queueAddImages();
        System.out.println("Loading images...");
    }

    @Override
    public void show() {
        titleImage = new Image(title);
        copyrightImage = new Image(copyright);

        table = new Table();
        table.setFillParent(true);
        table.setDebug(false);

        table.setBackground(new TiledDrawable(background));

        loadingTable = new Table();
        loadingTable.add(new LoadingBarPart(dash, flameAnimation));
        loadingTable.add(new LoadingBarPart(dash, flameAnimation));
        loadingTable.add(new LoadingBarPart(dash, flameAnimation));
        loadingTable.add(new LoadingBarPart(dash, flameAnimation));
        loadingTable.add(new LoadingBarPart(dash, flameAnimation));
        loadingTable.add(new LoadingBarPart(dash, flameAnimation));
        loadingTable.add(new LoadingBarPart(dash, flameAnimation));
        loadingTable.add(new LoadingBarPart(dash, flameAnimation));
        loadingTable.add(new LoadingBarPart(dash, flameAnimation));
        loadingTable.add(new LoadingBarPart(dash, flameAnimation));

        table.add(titleImage).align(Align.center).pad(10, 0, 0, 0).colspan(10);
        table.row();
        table.add(loadingTable).width(400);
        table.row();
        table.add(copyrightImage).align(Align.center).pad(200, 0, 0, 0).colspan(10);

        stage.addActor(table);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (parent.assetManager.manager.update()) {
            currentLoadingStage += 1;
            if (currentLoadingStage <= FINISHED) {
                loadingTable.getCells().get((currentLoadingStage - 1)*2).getActor().setVisible(true);
                loadingTable.getCells().get((currentLoadingStage - 1) * 2 + 1).getActor().setVisible(true);
            }

            switch(currentLoadingStage) {
                case FONT:
                    System.out.println("Loading fonts...");
                    parent.assetManager.queueAddFonts();
                    break;
                case PARTY:
                    System.out.println("Loading Particle Effects...");
                    parent.assetManager.queueAddParticleEffects();
                    break;
                case SOUND:
                    System.out.println("Loading sounds....");
                    parent.assetManager.queueAddSounds();
                    break;
                case MUSIC:
                    System.out.println("Loading music...");
                    parent.assetManager.queueAddMusic();
                    break;
                case FINISHED:
                    System.out.println("Finished");
                    break;
            }

            if (currentLoadingStage > FINISHED) {
                countDown -= delta;
                currentLoadingStage = FINISHED;
                if (countDown < 0) {
                    parent.changeScreen(Box2DTutorial.MENU);
                }
            }
        }

        stage.act();
        stage.draw();
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

    private void loadAssets() {
        parent.assetManager.queueAddLoadingImages();
        parent.assetManager.manager.finishLoading();

        atlas = parent.assetManager.manager.get("images/loading.atlas");
        title = atlas.findRegion("staying-alight-logo");
        dash = atlas.findRegion("loading-dash");
        flameAnimation = new Animation(0.07f, atlas.findRegions("flames"), PlayMode.LOOP);

        background = atlas.findRegion("flamebackground");
        copyright = atlas.findRegion("copyright");
    }

    class LoadingBarPart extends Actor {
        private final AtlasRegion image;
        private Animation flameAnimation;
        private TextureRegion currentFrame;
        private float stateTime = 0f;

        public LoadingBarPart(AtlasRegion atlasRegion, Animation animation) {
            super();

            image = atlasRegion;
            flameAnimation = animation;
            this.setWidth(30);
            this.setHeight(25);
            this.setVisible(false);
        }

        @Override
        public void draw(Batch batch, float parentAlpha) {
            super.draw(batch, parentAlpha);
            batch.draw(image, getX(), getY(), 30, 30);
            batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
            batch.draw(currentFrame, getX() - 5, getY(), 40, 40);
            batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        }

        @Override
        public void act(float delta) {
            super.act(delta);
            stateTime += delta;
            currentFrame = (TextureRegion)flameAnimation.getKeyFrame(stateTime, true);
        }
    }
}
