package Screens;

import GameState.ExploringState;
import GameState.GameContext;
import GameState.InitialGameStates.InitialMenuState;
import GameState.InventoryStates.InventoryState;
import GameState.MenuStates.PauseState;
import Main.GameApp;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class PauseScreen extends AbstractScreen{
    private SpriteBatch batch;

    public PauseScreen(GameApp app) {
        super(app);
        this.batch = new SpriteBatch();
    }

    public void drawPauseMenu() {
        batch.begin();
        Texture background = new Texture(Gdx.files.internal("resources/Bag/PauseDesign.png"));
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        BitmapFont font = new BitmapFont();
        font.getData().setScale(1.6f);

        String[] options = { "1. Bag", "2. Save Game", "3. Quit Game", "4. Return to Title" };
        float y = Gdx.graphics.getHeight() / 2 + options.length * 20;
        for (String option : options) {
            GlyphLayout layout = new GlyphLayout(font, option);
            float x = (Gdx.graphics.getWidth() - layout.width) / 2;
            font.draw(batch, layout, x, y);
            y -= layout.height + 20;
        }

        batch.end();

        handlePauseMenuInput();
    }
    public void handlePauseMenuInput() {
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            GameContext.getInstance().setState(ExploringState.getOverworldState());
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
            GameContext.getInstance().setState(new InventoryState());
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
            handleSaveGame();
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)) {
            Gdx.app.exit();
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_4)) {
            GameContext.getInstance().setState(new InitialMenuState());
        }
    }

    public void handleSaveGame() {
        String saveMessage = "Game saved successfully.";
        boolean showSaveConfirmation = true;
        float saveConfirmationTime = 3.0f;
    }




        @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        drawPauseMenu();
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            GameContext.getInstance().setState(ExploringState.getOverworldState());
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

    }
}
