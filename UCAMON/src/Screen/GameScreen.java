package Screen;

import Util.AnimationSet;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import controller.PlayerController;
import entity.*;
import main.Pokemon;
import main.Settings;

import java.util.ArrayList;
import java.util.List;

public class GameScreen  extends AbstractScreen {
    private Camara camara;
    private PlayerController control;
    private Entity player;
    private Texture playerStandingSouth;
    private SpriteBatch batch;

    private List<TextureRegion> trees;
    private List<Entity> treeEntities;

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

        trees = new ArrayList<>();
        for(int i = 0; i < 6; i++){
            trees.add(new TextureRegion(new Texture("resources/Tiles/Tree/tree_"+ i +".png")));
        }

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


        map = new TileMap(20,12, Grass1);
        player = new Entity(map,10,6, animations);
        camara = new Camara();

        control = new PlayerController(player);

        for (int y = 0; y < map.getHeight(); y++) {
            map.setTile(10, y, BrownGrass2);
            map.setTile(11,y, BrownGrass2);
            map.setTile(9,y, BrownGrass2);
            map.setTile(8,y, new TextureRegion(BrownGrass1));
            map.setTile(12,y, new TextureRegion(BrownGrass3));
        }

        treeEntities = new ArrayList<>();

        for(int y = 0; y < map.getHeight()-2; y++){
            addTree(7, y);
            addTree(13, y);
        }
        }
        /*
        for(int y = 0; y < map.getHeight(); y++){
            TextureRegion treeTexture = trees.get(y % trees.size());
            map.setTile(7, y, treeTexture);
            treeEntities.add(new Entity(map,7, y, treeTexture));

            treeTexture = trees.get((y+1) % trees.size());
            map.setTile(13, y, treeTexture);
            treeEntities.add(new Entity(map, 13,y,treeTexture));
        }
        */
        private void addTree(int x, int y) {
            for (int i = 0; i < 3; i++) {
                if (y + i < map.getHeight()) {
                    map.setTile(x + i, y, trees.get(i * 2));
                    treeEntities.add(new Entity(map, x, y + i, trees.get(i * 2)));

                    map.setTile(x + i, y, trees.get(i * 2 + 1));
                    treeEntities.add(new Entity(map, x + i, y + i, trees.get(i * 2 + 1)));
                }
            }
        }


    @Override
    public void show() {
        Gdx.input.setInputProcessor(control);
    }

    @Override
    public void render(float delta) {
        control.update(delta);

        player.update(delta);

        float playerWorldX = player.getWorldX() * Settings.SCALED_TILE_SIZE + Settings.SCALED_TILE_SIZE / 2;
        float playerWorldY = player.getWorldY() * Settings.SCALED_TILE_SIZE + Settings.SCALED_TILE_SIZE / 2;
        camara.update(playerWorldX, playerWorldY);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        float worldStartX = Gdx.graphics.getWidth() / 2 - camara.getCamaraX();
        float worldStartY = Gdx.graphics.getHeight() / 2 - camara.getCamaraY();

        batch.begin();

        for(int x = 0; x < map.getWidth(); x++){
            for(int y = 0; y <map.getHeight();y++) {
                TilePrueba tile = map.getTile(x, y);
                Texture render = tile.getTexture(); //Un cambio debido a que se hizo un cambio las clases de Tile
                batch.draw(render,
                        worldStartX+x*Settings.SCALED_TILE_SIZE,
                        worldStartY+y*Settings.SCALED_TILE_SIZE,
                        Settings.SCALED_TILE_SIZE,
                        Settings.SCALED_TILE_SIZE);
            }
        }

        for (Entity tree : treeEntities) {
            batch.draw(tree.getSprite(),
                    worldStartX + tree.getWorldX() * Settings.SCALED_TILE_SIZE,
                    worldStartY + tree.getWorldY() * Settings.SCALED_TILE_SIZE,
                    Settings.SCALED_TILE_SIZE,
                    Settings.SCALED_TILE_SIZE * 1.5f);
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

