package Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import controller.PlayerController;
import entity.Player;
import main.Pokemon;
import main.Settings;

public class GameScreen  extends AbstractScreen {
    private PlayerController control;
    private Player player;
    private Texture playerStandingSouth;
    private SpriteBatch batch;

    public GameScreen(Pokemon app) {
        super(app);

        playerStandingSouth = new Texture("resources/SpriteTest.png");
        batch = new SpriteBatch();

        player = new Player(0,0);

        control = new PlayerController(player);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(control);
    }

    @Override
    public void render(float delta) {
    batch.begin();
    batch.draw(playerStandingSouth, player.getX()* Settings.SCALED_TILE_SIZE, player.getY()*Settings.SCALED_TILE_SIZE, Settings.SCALED_TILE_SIZE
            , Settings.SCALED_TILE_SIZE*1.5f);
    batch.end();
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

