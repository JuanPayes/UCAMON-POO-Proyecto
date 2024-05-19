package Screen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import main.Pokemon;

public class GameScreen  extends AbstractScreen {

    private Texture player;
    private SpriteBatch batch;

    public GameScreen(Pokemon app) {
        super(app);

        player = new Texture("Res/SpriteTest.png");
        batch = new SpriteBatch();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
    batch.begin();
    batch.draw(player, 300, 200, 16, 16);
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

