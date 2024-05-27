package Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import controller.PlayerController;
import entity.Camara;
import entity.Entity;
import entity.TERRAIN;
import entity.TileMap;
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


        playerStandingSouth = new Texture("resources/SpriteTest.png");
        Grass1 = new Texture("resources/Tiles/grass.png");
        BrownGrass1 = new Texture("resources/Tiles/brown_path_grass_west.png");
        BrownGrass2 = new Texture("resources/Tiles/brown_path.png");
        BrownGrass3 = new Texture("resources/Tiles/brown_path_grass_east.png");
        batch = new SpriteBatch();
        map = new TileMap(20,12);
        player = new Entity(map,0,0);
        camara = new Camara();

        control = new PlayerController(player);

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(control);
    }

    @Override
    public void render(float delta) {
        camara.update(player.getX()+0.5f ,player.getY()+0.5f);

        batch.begin();

        float worldStartX = Gdx.graphics.getWidth()/2 - camara.getCamaraX()*Settings.SCALED_TILE_SIZE;
        float worldStartY = Gdx.graphics.getHeight()/2 - camara.getCamaraY()*Settings.SCALED_TILE_SIZE;

        for(int x = 0; x < map.getWidth(); x++){
            for(int y = 0; y <map.getHeight();y++) {
                Texture render;

                if (map.getTile(x, y).getTerrain() == TERRAIN.GRASS_1) {
                    render = Grass1;
                }else {
                    render = Grass1;
                }
                batch.draw(render,worldStartX+x*Settings.SCALED_TILE_SIZE,worldStartY+y*Settings.SCALED_TILE_SIZE,Settings.SCALED_TILE_SIZE,Settings.SCALED_TILE_SIZE);
            }
        }

        batch.draw(playerStandingSouth, worldStartX+player.getX()* Settings.SCALED_TILE_SIZE, worldStartY+player.getY()*Settings.SCALED_TILE_SIZE, Settings.SCALED_TILE_SIZE
                , Settings.SCALED_TILE_SIZE*1.5f);//Ver dimensiones de sprite...
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

