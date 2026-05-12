package Screens;

import GameState.GameContext;
import GameState.InventoryStates.InventoryState;
import Main.GameApp;
import PokemonFactory.Pokemon.Pokemon;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.List;

public class ViewPokemonScreen extends AbstractScreen {

    private SpriteBatch batch;
    private Integer firstSelection = null;
    private Integer secondSelection = null;
    private BitmapFont font;

    public ViewPokemonScreen(GameApp app) {
        super(app);
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.getData().setScale(1.0f);
    }

    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) selectPokemon(0);
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) selectPokemon(1);
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)) selectPokemon(2);
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_4)) selectPokemon(3);
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_5)) selectPokemon(4);
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_6)) selectPokemon(5);
    }

    private void selectPokemon(int index) {
        if (firstSelection == null) {
            firstSelection = index;
        } else if (secondSelection == null) {
            secondSelection = index;
            switchPokemons(firstSelection, secondSelection);
            firstSelection = null;
            secondSelection = null;
        }
    }

    private void switchPokemons(int index1, int index2) {
        List<Pokemon> pokemons = GameContext.getInstance().getPlayer().getPokemons();
        if (index1 >= 0 && index1 < pokemons.size() && index2 >= 0 && index2 < pokemons.size()) {
            Pokemon temp = pokemons.get(index1);
            pokemons.set(index1, pokemons.get(index2));
            pokemons.set(index2, temp);
        }
    }

    private void drawPokemonWindow() {
        batch.begin();
        TextureRegion windowImage = new TextureRegion(new Texture(Gdx.files.internal("resources/Bag/BagDesign.png")));

        // Obtener las dimensiones originales de la imagen
        float imageWidth = windowImage.getRegionWidth();
        float imageHeight = windowImage.getRegionHeight();

        // Dimensiones de la pantalla
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        // Calcular el factor de escala para ajustar la imagen dentro de la pantalla manteniendo la proporción
        float scale = Math.min(screenWidth / imageWidth, screenHeight / imageHeight);

        // Calcular el nuevo tamaño de la imagen escalada
        float scaledWidth = imageWidth * scale;
        float scaledHeight = imageHeight * scale;

        // Calcular la posición para centrar la imagen escalada en la pantalla
        float bgX = (screenWidth - scaledWidth) / 2;
        float bgY = (screenHeight - scaledHeight) / 2;

        // Dibujar la imagen de la ventana escalada
        batch.draw(windowImage, bgX, bgY, scaledWidth, scaledHeight);


        BitmapFont font = new BitmapFont();
        font.getData().setScale(1.2f);



        // Suponiendo que `selectedPokemons` contiene los Pokémon seleccionados
        float startX = bgX + 249;
        float startY = bgY + scaledHeight - 40;

        for (int i = 0; i < GameContext.getInstance().getPlayer().getPokemons().size() && i < 6; i++) {
            Pokemon pokemon = GameContext.getInstance().getPlayer().getPokemons().get(i);
            String info = String.format("Name: %s, HP: %d, Speed: %d, Level: %d",
                    pokemon.getName(), pokemon.getHp(), pokemon.getSpeed(), pokemon.getLevel());
            GlyphLayout layout = new GlyphLayout(font, info);
            if (firstSelection != null && firstSelection == i) {
                font.getData().setScale(1.2f);
            } else if (secondSelection != null && secondSelection == i) {
                font.getData().setScale(1.2f);
            } else {
                font.getData().setScale(1.0f);
            }
            font.draw(batch, layout, startX, startY - i * (layout.height + 20));
        }

        GlyphLayout instructionLayout = new GlyphLayout(font, "");
        float instructionX = (Gdx.graphics.getWidth() - instructionLayout.width) / 2;
        float instructionY = 80;
        font.draw(batch, instructionLayout, instructionX, instructionY);

        GlyphLayout returnLayout = new GlyphLayout(font, "Press ESC to return");
        float returnX = (Gdx.graphics.getWidth() - returnLayout.width) / 2;
        float returnY = 50;
        font.draw(batch, returnLayout, returnX, returnY);

        batch.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            GameContext.getInstance().setState(new InventoryState());
        }
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clear the screen
        handleInput();
        drawPokemonWindow();
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
        font.dispose();
    }
}
