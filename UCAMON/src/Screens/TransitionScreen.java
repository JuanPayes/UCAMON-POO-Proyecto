package Screens;

import GameState.GameContext;
import Main.GameApp;
import PokemonFactory.Pokemon.Pokemon;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TransitionScreen extends AbstractScreen {
    public SpriteBatch batch;
    private Texture transitionTexture;
    private float alpha;
    private boolean fadingIn;
    private OverworldScreen gameScreen;
    private boolean transitionComplete = false;

    public TransitionScreen(GameApp app, OverworldScreen gameScreen) {
        super(app);
        this.batch = new SpriteBatch();
        this.transitionTexture = new Texture("resources/Transition/transition_1.png");
        this.alpha = 0.0f;
        this.fadingIn = true;
        this.gameScreen = gameScreen;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.setColor(1, 1, 1, alpha);
        batch.draw(transitionTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

        if (fadingIn) {
            alpha += delta;
            if (alpha >= 1) {
                alpha = 1;
                fadingIn = false;
                gameScreen.changeRoomImmediately(gameScreen.nextRoom, gameScreen.newX, gameScreen.newY);
                transitionComplete = true;
            }
        } else {
            alpha -= delta;
            if (alpha <= 0) {
                alpha = 0;
                GameContext.getInstance().getGame().setScreen(gameScreen);
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
        batch.dispose();
        transitionTexture.dispose();
    }
}
