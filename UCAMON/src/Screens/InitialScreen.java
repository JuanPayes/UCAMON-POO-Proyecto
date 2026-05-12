package Screens;

import GameState.ExploringState;
import GameState.GameContext;
import GameState.InitialGameStates.CinematicState;
import Main.GameApp;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class InitialScreen extends AbstractScreen {
    private Music intro;
    private SpriteBatch batch = new SpriteBatch();
    private Texture background;

    public InitialScreen(GameApp app) {
        super(app);
        intro = Gdx.audio.newMusic(Gdx.files.internal("resources/Music/Intro.mp3"));
        intro.setLooping(true);
        intro.setVolume(0.1f);
        background = new Texture(Gdx.files.internal("resources/TitleScreen/background.png"));
    }

    public void drawTitleScreen() {
        batch.begin();
        batch.draw(background, -200, -70, 1000, 540);
        BitmapFont font = new BitmapFont();
        font.getData().setScale(1.6f);

        String comenzar = "Press ENTER to start a new game";
        GlyphLayout layout = new GlyphLayout(font, comenzar);
        float x = (Gdx.graphics.getWidth() - layout.width) / 2;
        float y = (Gdx.graphics.getHeight() + layout.height - 200) / 2;
        font.draw(batch, layout, x, y);

        String cargar = "Press L to load";
        layout.setText(font, cargar);
        x = (Gdx.graphics.getWidth() - layout.width) / 2;
        y -= layout.height + 20;
        font.draw(batch, layout, x, y);

        batch.end();
    }

    @Override
    public void show() {
        intro.play();
    }

    @Override
    public void render(float delta) {
        drawTitleScreen();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            intro.stop();
            GameContext.getInstance().setState(new CinematicState());
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.L)) {
            GameContext.getInstance().setState(new ExploringState());
        }

    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {
        intro.stop();
    }

    @Override
    public void dispose() {
        batch.dispose();
        intro.dispose();
        background.dispose();
    }
}
