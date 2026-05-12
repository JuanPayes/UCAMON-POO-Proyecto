package Screens;

import Main.GameApp;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Screen;


public abstract class AbstractScreen implements Screen {

    private GameApp app;

    public AbstractScreen(GameApp app) {

        this.app = app;
    }

    public GameApp getApp() {
        return app;
    }

    public abstract void show();

    public abstract void  render(float delta);

    public abstract void resize(int width, int height);

    public abstract void  pause();

    public abstract void resume();

    public abstract void hide();

    public abstract void dispose();
}
