package net.lafferjm.gamedevbox2dtutorial.loader;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader.SkinParameter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * Created by laffe on 3/19/2018.
 */

public class B2dAssetManager {

    public final AssetManager manager = new AssetManager();

    public final String playerImage = "images/player.png";
    public final String enemyImage = "images/enemy.png";

    public final String boingSound = "sounds/boing.wav";
    public final String pingSound = "sounds/ping.wav";

    public final String playingSong = "music/Rolemusic_-_pl4y1ng.mp3";

    public final String skin = "skin/glassy-ui.json";

    public final String gameImages = "images/game.atlas";
    public final String loadingImages = "images/loading.atlas";

    public void queueAddImages() {
        manager.load(gameImages, TextureAtlas.class);
    }

    public void queueAddLoadingImages() {
        manager.load(loadingImages, TextureAtlas.class);
    }

    public void queueAddSounds() {
        manager.load(boingSound, Sound.class);
        manager.load(pingSound, Sound.class);
    }

    public void queueAddMusic() {
        manager.load(playingSong, Music.class);
    }

    public void queueAddSkin() {
        SkinParameter params = new SkinParameter("skin/glassy-ui.atlas");
        manager.load(skin, Skin.class, params);
    }

    public void queueAddFonts() {

    }

    public void queueAddParticleEffects() {

    }
}
