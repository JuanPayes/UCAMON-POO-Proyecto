package Screen;

import com.badlogic.gdx.Screen;
import main.Pokemon;

public abstract class AbstractScreen implements Screen {

    private Pokemon app;

    public AbstractScreen(Pokemon app) {
    this.app = app;
    }

    public Pokemon getApp() {
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
