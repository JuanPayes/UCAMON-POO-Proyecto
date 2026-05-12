package Screens;

import GameState.ExploringState;
import GameState.GameContext;
import Main.GameApp;
import PokemonFactory.Pokemon.Pokemon;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class PokemonCenterScreen extends AbstractScreen {

    private SpriteBatch batch;

    public PokemonCenterScreen(GameApp app) {
        super(app);
        batch = new SpriteBatch();
    }

    public void drawPCenter() {
        batch.begin();
        TextureRegion PCenterImage = new TextureRegion(new Texture(Gdx.files.internal("resources/Bag/TextBoxDesign.png")));

        float imageWidth = PCenterImage.getRegionWidth();
        float imageHeight = PCenterImage.getRegionHeight();

        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        float scale = Math.min(screenWidth / imageWidth, screenHeight / imageHeight);

        float scaledWidth = imageWidth * scale;
        float scaledHeight = imageHeight * scale;

        float bgX = (screenWidth - scaledWidth) / 2; // Posición X centrada
        float bgY = (screenHeight - scaledHeight) / 2; // Posición Y centrada

        batch.draw(PCenterImage, bgX, bgY, scaledWidth, scaledHeight);
        BitmapFont font = new BitmapFont();
        font.getData().setScale(1.2f);
        BitmapFont font2 = new BitmapFont();
        font2.getData().setScale(1.2f);
        font2.setColor(0, 0, 0, 1);

        // Texto en la parte inferior
        GlyphLayout layoutBottom = new GlyphLayout(font, "Press ESC to return to the game");
        float textBottomX = (Gdx.graphics.getWidth() - layoutBottom.width) / 2;
        float textBottomY = 50;
        font.draw(batch, layoutBottom, textBottomX, textBottomY);

        // Texto en el centro
        GlyphLayout layoutCenter = new GlyphLayout(font2, "All Pokémon have been healed!");
        float textCenterX = bgX + (scaledWidth - layoutCenter.width) / 2;
        float textCenterY = bgY + (scaledHeight + layoutCenter.height) / 2;
        font.draw(batch, layoutCenter, textCenterX, textCenterY);

        batch.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            GameContext.getInstance().setState(ExploringState.getOverworldState());
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            healAllPokemon();
        }
    }

    private void healAllPokemon() {
        ExploringState.getOverworldState().getOverworldScreen().ableToBattle = true;
        for(Pokemon p : GameContext.getInstance().getPlayer().getPokemons()){
            p.setHp(p.getHpMax());
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        drawPCenter();
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
