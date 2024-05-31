package main;

import Screen.GameScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

public class Pokemon extends Game {

    private GameScreen screen1;
    public void create() {
        screen1 = new GameScreen(this);

        this.setScreen(screen1);
    }
    public void render() {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        super.render();
    }
}
