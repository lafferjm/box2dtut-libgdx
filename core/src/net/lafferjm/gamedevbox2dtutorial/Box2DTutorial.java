package net.lafferjm.gamedevbox2dtutorial;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.audio.Music;

import net.lafferjm.gamedevbox2dtutorial.loader.B2dAssetManager;
import net.lafferjm.gamedevbox2dtutorial.views.EndScreen;
import net.lafferjm.gamedevbox2dtutorial.views.LoadingScreen;
import net.lafferjm.gamedevbox2dtutorial.views.MainScreen;
import net.lafferjm.gamedevbox2dtutorial.views.MenuScreen;
import net.lafferjm.gamedevbox2dtutorial.views.PreferencesScreen;

public class Box2DTutorial extends Game {
    private LoadingScreen loadingScreen;
    private PreferencesScreen preferencesScreen;
    private MenuScreen menuScreen;
    private MainScreen mainScreen;
    private EndScreen endScreen;
    private AppPreferences preferences;
    private Music playingSong;
    public B2dAssetManager assetManager = new B2dAssetManager();

    public final static int MENU = 0;
    public final static int PREFERENCES = 1;
    public final static int APPLICATION = 2;
    public final static int ENDGAME = 3;

    @Override
    public void create() {
        preferences = new AppPreferences();
        loadingScreen = new LoadingScreen(this);
        setScreen(loadingScreen);

        assetManager.queueAddMusic();
        assetManager.manager.finishLoading();

        playingSong = assetManager.manager.get("music/Rolemusic_-_pl4y1ng.mp3");
        playingSong.play();
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        super.dispose();
        playingSong.dispose();
        assetManager.manager.dispose();
    }

    public void changeScreen(int screen) {
        switch(screen) {
            case MENU:
                if(menuScreen == null) {
                    menuScreen = new MenuScreen(this);
                }
                this.setScreen(menuScreen);
                break;
            case PREFERENCES:
                if (preferencesScreen == null) {
                    preferencesScreen = new PreferencesScreen(this);
                }
                this.setScreen(preferencesScreen);
                break;
            case APPLICATION:
                if (mainScreen == null) {
                    mainScreen = new MainScreen(this);
                }
                this.setScreen(mainScreen);
                break;
            case ENDGAME:
                if (endScreen == null) {
                    endScreen = new EndScreen(this);
                }
                this.setScreen(endScreen);
                break;
        }
    }

    public AppPreferences getPreferences() {
        return this.preferences;
    }
}