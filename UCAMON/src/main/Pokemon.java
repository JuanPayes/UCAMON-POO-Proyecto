package main;

import Screen.GameScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Pokemon extends Game {

    private AssetManager assetManager;

    private GameScreen screen1;
    public void create() {
        assetManager = new AssetManager();
        assetManager.load("resources/packed/textures.atlas", TextureAtlas.class);
        assetManager.finishLoading();

        screen1 = new GameScreen(this);
        this.setScreen(screen1);
    }
    public void render() {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        super.render();
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }
}
