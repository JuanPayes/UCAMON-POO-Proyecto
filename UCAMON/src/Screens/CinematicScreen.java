package Screens;

import GameState.ExploringState;
import GameState.GameContext;
import Main.GameApp;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * 
 */
public class CinematicScreen extends AbstractScreen{

    private Music menu;
    private SpriteBatch batch;

    public CinematicScreen(GameApp app) {
        super(app);
        batch = new SpriteBatch();
        menu = Gdx.audio.newMusic(Gdx.files.internal("resources/Music/Menu.mp3"));
        menu.setLooping(true);
        menu.setVolume(0.1f);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
            menu.play();
            drawStoryScreen();
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
    public void drawStoryScreen() {
        batch.begin();
        batch.draw(new Texture("resources/Dialog/Dialog.png"), 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        BitmapFont font = new BitmapFont();
        font.getData().setScale(1.0f);
        font.setColor(0, 0, 0, 1);

        String story = "Our adventure begins on the first day of class, when our protagonist, \n" +
                "excited to enter UCA, encounters an unexpected accident. \n" +
                "The legendary Pokémon Owluca has taken control of the \n entire campus and has hypnotized all the Pokémon and professors! \n" +
                "It is our duty to stop him and restore peace to UCA. \n" +
                "Only one student will be able to do it… \n";
        String[] storyLines = story.split("\n");
        float y = (Gdx.graphics.getHeight() + (storyLines.length * 30)) / 2;
        for (String line : storyLines) {
            GlyphLayout layout = new GlyphLayout(font, line);
            float x = (Gdx.graphics.getWidth() - layout.width) / 2;
            font.draw(batch, layout, x, y);
            y -= layout.height + 10; // Ajusta el espaciado entre líneas según sea necesario
        }

        String continueMessage = "Press ENTER to continue..";
        GlyphLayout continueLayout = new GlyphLayout(font, continueMessage);
        float continueX = (Gdx.graphics.getWidth() - continueLayout.width) / 2;
        float continueY = y - continueLayout.height - 20;
        font.draw(batch, continueLayout, continueX, continueY);

        batch.end();
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            menu.stop();
            GameContext.getInstance().setState(ExploringState.getOverworldState());
        }
    }
}
