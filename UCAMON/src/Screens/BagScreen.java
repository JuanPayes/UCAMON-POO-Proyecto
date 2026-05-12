package Screens;

import GameState.GameContext;
import GameState.InventoryStates.ViewPokemonState;
import GameState.MenuStates.PauseState;
import Items.Buyable;
import Main.GameApp;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * The screen set to display de bag with all of their methods
 */
public class BagScreen extends AbstractScreen {

    private SpriteBatch batch;

    public BagScreen(GameApp app) {
        super(app);
        batch = new SpriteBatch();
    }

    public void handleSaveGame() {
        String saveMessage = "Game saved successfully.";
        Boolean showSaveConfirmation = true;
        float saveConfirmationTime = 3.0f;
    }

    public void drawBag() {
        batch.begin();
        TextureRegion bagImage = new TextureRegion(new Texture(Gdx.files.internal("resources/Bag/BagDesign.png")));

        // Obtener las dimensiones originales de la imagen
        float imageWidth = bagImage.getRegionWidth();
        float imageHeight = bagImage.getRegionHeight();

        // Dimensiones de la pantalla
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        // Calcular el factor de escala para ajustar la imagen dentro de la pantalla manteniendo la proporción
        float scale = Math.min(screenWidth / imageWidth, screenHeight / imageHeight);

        // Calcular el nuevo tamaño de la imagen escalada
        float scaledWidth = imageWidth * scale;
        float scaledHeight = imageHeight * scale;

        // Calcular la posición para centrar la imagen escalada en la pantalla
        float bgX = (screenWidth - scaledWidth) / 2; // Posición X centrad
        float bgY = (screenHeight - scaledHeight) / 2; // Posición Y centrada

        // Dibujar la imagen del bolso escalada
        batch.draw(bagImage, bgX, bgY, scaledWidth, scaledHeight);

        BitmapFont font = new BitmapFont();
        font.getData().setScale(1.2f);
        int pokeballCount = Buyable.countItems("Pokeball", GameContext.getInstance().getPlayer().getItems());
        int potionCount = Buyable.countItems("Potion", GameContext.getInstance().getPlayer().getItems());

        Texture pokeballImage = new Texture(Gdx.files.internal("resources/MenuSprites/pokeball.png"));
        float pokeballX = 249;
        float pokeballY = screenHeight - 60;
        batch.draw(pokeballImage, pokeballX, pokeballY);
        GlyphLayout pokeballLayout = new GlyphLayout(font, "x" + pokeballCount);
        font.draw(batch, pokeballLayout, pokeballX + pokeballImage.getWidth() + 10, pokeballY + pokeballImage.getHeight() / 2 + pokeballLayout.height / 2);

        Texture potionImage = new Texture(Gdx.files.internal("resources/MenuSprites/potion.png"));
        float potionX = 249;
        float potionY = pokeballY - 40; // Ajusta la posición Y según sea necesario
        batch.draw(potionImage, potionX, potionY);
        GlyphLayout potionLayout = new GlyphLayout(font, "x" + potionCount);
        font.draw(batch, potionLayout, potionX + potionImage.getWidth() + 10, potionY + potionImage.getHeight() / 2 + potionLayout.height / 2);

        // Obtener y mostrar el dinero del jugador
        String money = "$" + GameContext.getInstance().getPlayer().getMoney();
        GlyphLayout moneyLayout = new GlyphLayout(font, "Money: " + money);
        float moneyX = 450;
        float moneyY = screenHeight - 230; // Ajusta esta posición según sea necesario
        font.draw(batch, moneyLayout, moneyX, moneyY);

        GlyphLayout layout = new GlyphLayout(font, "Press ESC to return to main menu");
        float textX = (Gdx.graphics.getWidth() - layout.width) / 2;
        float textY = 50;
        font.draw(batch, layout, textX, textY);

        GlyphLayout layout1 = new GlyphLayout(font, "Press P to view your pokemons ");
        float textX1 = (Gdx.graphics.getWidth() - layout1.width) / 2;
        float textY1 = 30;
        font.draw(batch, layout1, textX1, textY1);

        batch.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            GameContext.getInstance().setState(new PauseState());
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {
            GameContext.getInstance().setState(new ViewPokemonState());
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        drawBag();
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
