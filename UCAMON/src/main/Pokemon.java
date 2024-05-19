package main;

import Screen.GameScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

public class Pokemon extends Game {

    private GameScreen screen;
    public void create() {
        screen = new GameScreen(this);

        this.setScreen(screen);
    }
    public void render() {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

        super.render();
    }
}
