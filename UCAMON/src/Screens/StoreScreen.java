package Screens;

import GameState.ExploringState;
import GameState.GameContext;
import Items.Buyable;
import Items.Pokeball;
import Items.Potion;
import Main.GameApp;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Pantalla para el menu de la tienda.
 */
public class StoreScreen extends AbstractScreen{
    private int num;
    private SpriteBatch batch;
    private int pokeballCount = 0;
    private int potionCount = 0;
    private static final int ITEM_PRICE = 10;

    public StoreScreen(GameApp app) {
        super(app);
        batch = new SpriteBatch();
    }

    public void drawStore() {
        batch.begin();
        String pokeballCount = "$10";
        String potionCount = "$10";
        BitmapFont font2 = new BitmapFont();
        font2.getData().setScale(1.6f);
        font2.setColor(0,0,0,1);
        TextureRegion bagImage = new TextureRegion(new Texture(Gdx.files.internal("resources/Bag/StoreDesign.png")));
         float customTextX = 470; // Posición X inicial del texto
         float customTextY = 80; // Posición Y inicial del texto
       String money="$" + GameContext.getInstance().getPlayer().getMoney();
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

        Texture pokeballImage = new Texture(Gdx.files.internal("resources/MenuSprites/pokeball.png"));
        float pokeballX = 249;
        float pokeballY = Gdx.graphics.getHeight() - 60;
        batch.draw(pokeballImage, pokeballX, pokeballY);
        BitmapFont font = new BitmapFont();
        font.getData().setScale(1.2f);
        GlyphLayout pokeballLayout = new GlyphLayout(font, "x" + pokeballCount);
        font.draw(batch, pokeballLayout, pokeballX + pokeballImage.getWidth() + 10, pokeballY + pokeballImage.getHeight() / 2 + pokeballLayout.height / 2);

        // Dibujar Poción
        Texture potionImage = new Texture(Gdx.files.internal("resources/MenuSprites/potion.png"));
        float potionX = 249;
        float potionY = pokeballY - 40; // Ajusta la posición Y según sea necesario
        batch.draw(potionImage, potionX, potionY);
        GlyphLayout potionLayout = new GlyphLayout(font, "x" + potionCount);
        font.draw(batch, potionLayout, potionX + potionImage.getWidth() + 10, potionY + potionImage.getHeight() / 2 + potionLayout.height / 2);

        // Mensaje de retorno
        GlyphLayout layout = new GlyphLayout(font, "Press ESC to return to main menu");
        float textX = (Gdx.graphics.getWidth() - layout.width) / 2;
        float textY = 50;
        font.draw(batch, layout, textX, textY);
        GlyphLayout customTextLayout = new GlyphLayout(font2, money);
        font.draw(batch, customTextLayout, customTextX, customTextY);

        batch.end();



        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            GameContext.getInstance().setState(ExploringState.getOverworldState());
        }
        handleInput();
    }

    private void handleInput() {
        int playerMoney = GameContext.getInstance().getPlayer().getMoney();

        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
            if (playerMoney >= 10) {
                pokeballCount++;
                GameContext.getInstance().getPlayer().setMoney(playerMoney - 10);
                Pokeball po= new Pokeball();
                GameContext.getInstance().getPlayer().getItems().add(po);
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
            if (playerMoney >= 10) {
                potionCount++;
                GameContext.getInstance().getPlayer().setMoney(playerMoney - 10);
                Potion p=new Potion();
                GameContext.getInstance().getPlayer().getItems().add(p);
            }
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        drawStore();
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
