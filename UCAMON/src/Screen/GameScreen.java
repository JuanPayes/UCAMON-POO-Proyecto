package Screen;

import Util.AnimationSet;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import controller.PlayerController;
import entity.*;
import main.Pokemon;
import main.Settings;

public class GameScreen  extends AbstractScreen {
    private Camara camara;
    private PlayerController control;
    private Entity player;
    private Texture playerStandingSouth;
    private SpriteBatch batch;

    private Texture Grass1;
    private Texture BrownGrass1;
    private Texture BrownGrass2;
    private Texture BrownGrass3;
    private TileMap map;

    public GameScreen(Pokemon app) {
        super(app);
        this.control = new PlayerController(player);

        playerStandingSouth = new Texture("resources/unpacked/RedStanding_South.png");

        Grass1 = new Texture("resources/Tiles/grass.png");
        BrownGrass1 = new Texture("resources/Tiles/brown_path_grass_west.png");
        BrownGrass2 = new Texture("resources/Tiles/brown_path.png");
        BrownGrass3 = new Texture("resources/Tiles/brown_path_grass_east.png");
        batch = new SpriteBatch();

        TextureAtlas atlas = app.getAssetManager().get("resources/packed/textures.atlas", TextureAtlas.class);

        AnimationSet animations = new AnimationSet(
                new Animation(0.3f/2f,atlas.findRegions("RedWalking_North"), PlayMode.LOOP_PINGPONG),
                new Animation(0.3f/2f,atlas.findRegions("RedWalking_South"), PlayMode.LOOP_PINGPONG),
                new Animation(0.3f/2f,atlas.findRegions("RedWalking_East"), PlayMode.LOOP_PINGPONG),
                new Animation(0.3f/2f,atlas.findRegions("RedWalking_West"), PlayMode.LOOP_PINGPONG),
                atlas.findRegion("RedStanding_North"),
                atlas.findRegion("RedStanding_South"),
                atlas.findRegion("RedStanding_East"),
                atlas.findRegion("RedStanding_West")
        );


        map = new TileMap(20,12, Grass1, BrownGrass2);
        player = new Entity(map,0,0, animations);
        camara = new Camara();

        control = new PlayerController(player);

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(control);
    }

    @Override
    public void render(float delta) {
        control.update(delta);

        player.update(delta);
        camara.update(player.getWorldX()+0.5f ,player.getWorldY()+0.5f);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        float worldStartX = Gdx.graphics.getWidth()/2 - camara.getCamaraX()*Settings.SCALED_TILE_SIZE;
        float worldStartY = Gdx.graphics.getHeight()/2 - camara.getCamaraY()*Settings.SCALED_TILE_SIZE;

        for(int x = 0; x < map.getWidth(); x++){
            for(int y = 0; y <map.getHeight();y++) {
                TilePrueba tile = map.getTile(x, y);
                Texture render = tile.getTexture();
                batch.draw(render,worldStartX+x*Settings.SCALED_TILE_SIZE,worldStartY+y*Settings.SCALED_TILE_SIZE,Settings.SCALED_TILE_SIZE,Settings.SCALED_TILE_SIZE);
            }
        }

        batch.draw(player.getSprite(),
                worldStartX+player.getWorldX()*Settings.SCALED_TILE_SIZE,
                worldStartY+player.getWorldY()*Settings.SCALED_TILE_SIZE,
                Settings.SCALED_TILE_SIZE
                ,Settings.SCALED_TILE_SIZE*1.5f);
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

