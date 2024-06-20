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

    private TextureRegion[] treeTexture;

    private Texture Grass1, BrownGrass1,BrownGrass2, BrownGrass3;

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
        for (int i = 0; i < 6; i++) {
            trees.add(new TextureRegion(new Texture("resources/Tiles/Tree/tree_" + i + ".png")));
        }

        TextureAtlas atlas = app.getAssetManager().get("resources/packed/textures.atlas", TextureAtlas.class);

        AnimationSet animations = new AnimationSet(
                new Animation(0.3f / 2f, atlas.findRegions("RedWalking_North"), PlayMode.LOOP_PINGPONG),
                new Animation(0.3f / 2f, atlas.findRegions("RedWalking_South"), PlayMode.LOOP_PINGPONG),
                new Animation(0.3f / 2f, atlas.findRegions("RedWalking_East"), PlayMode.LOOP_PINGPONG),
                new Animation(0.3f / 2f, atlas.findRegions("RedWalking_West"), PlayMode.LOOP_PINGPONG),
                atlas.findRegion("RedStanding_North"),
                atlas.findRegion("RedStanding_South"),
                atlas.findRegion("RedStanding_East"),
                atlas.findRegion("RedStanding_West")
        );


        map = new TileMap(20, 12, Grass1);
        player = new Entity(map, 10, 6, animations);
        camara = new Camara();

        control = new PlayerController(player);

        generateTreesAroundScreen(map);

        for (int y = 0; y < map.getHeight(); y++) {
            map.setTile(10, y, BrownGrass2);
            map.setTile(11, y, BrownGrass2);
            map.setTile(9, y, BrownGrass2);
            map.setTile(8, y, new TextureRegion(BrownGrass1));
            map.setTile(12, y, new TextureRegion(BrownGrass3));
        }
        treeEntities = new ArrayList<>();
    }

    private void addTree(TileMap map, int x, int y, TextureRegion texture) {
        treeEntities.add(new Entity(map, x, y, texture));
    }

    private void generateTreesAroundScreen(TileMap map) {
        treeEntities.clear();

        float screenWidth = Gdx.graphics.getWidth() / Settings.SCALED_TILE_SIZE;
        float screenHeight = Gdx.graphics.getHeight() / Settings.SCALED_TILE_SIZE;

        for (int y = 0; y < screenHeight; y += 3) {

            addTree(map, 0, y, trees.get(0));
            addTree(map, 0, y + 1, trees.get(2));
            addTree(map, 0, y + 2, trees.get(4));


            addTree(map, (int)screenWidth - 2, y, trees.get(1));
            addTree(map, (int)screenWidth - 2, y + 1, trees.get(3));
            addTree(map, (int)screenWidth - 2, y + 2, trees.get(5));
        }
    }



    @Override
    public void show() {
        Gdx.input.setInputProcessor(control);
    }

    @Override
    public void render(float delta) {
        batch.begin();
        updateGameLogic(delta);
        clearScreen();
        drawGameWorld();
        drawEntities();
        drawPlayer();
        batch.end();
    }

    private void updateGameLogic(float delta) {
        control.update(delta);
        player.update(delta);
        updateCamera();
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    private void drawGameWorld() {
        float worldStartX = Gdx.graphics.getWidth() / 2 - camara.getCamaraX();
        float worldStartY = Gdx.graphics.getHeight() / 2 - camara.getCamaraY();
        for (int x = 0; x < map.getWidth(); x++) {
            for (int y = 0; y < map.getHeight(); y++) {
                TilePrueba tile = map.getTile(x, y);
                Texture render = tile.getTexture();
                batch.draw(render,
                        worldStartX + x * Settings.SCALED_TILE_SIZE,
                        worldStartY + y * Settings.SCALED_TILE_SIZE,
                        Settings.SCALED_TILE_SIZE,
                        Settings.SCALED_TILE_SIZE);
            }
        }
    }

    private void drawEntities() {
        float worldStartX = Gdx.graphics.getWidth() / 2 - camara.getCamaraX();
        float worldStartY = Gdx.graphics.getHeight() / 2 - camara.getCamaraY();
        drawTrees(batch, worldStartX, worldStartY);
    }

    private void drawPlayer() {
        float worldStartX = Gdx.graphics.getWidth() / 2 - camara.getCamaraX();
        float worldStartY = Gdx.graphics.getHeight() / 2 - camara.getCamaraY();
        batch.draw(player.getSprite(),
                worldStartX + player.getWorldX() * Settings.SCALED_TILE_SIZE,
                worldStartY + player.getWorldY() * Settings.SCALED_TILE_SIZE,
                Settings.SCALED_TILE_SIZE,
                Settings.SCALED_TILE_SIZE * 1.5f);
    }

    private void updateCamera() {
        float playerWorldX = player.getWorldX() * Settings.SCALED_TILE_SIZE + Settings.SCALED_TILE_SIZE / 2;
        float playerWorldY = player.getWorldY() * Settings.SCALED_TILE_SIZE + Settings.SCALED_TILE_SIZE / 2;
        camara.update(playerWorldX, playerWorldY);
    }

    public void drawTrees(SpriteBatch batch, float worldStartX, float worldStartY) {
        for (Entity tree : treeEntities) {
            float x = worldStartX + tree.getWorldX() * Settings.SCALED_TILE_SIZE;
            float y = worldStartY + tree.getWorldY() * Settings.SCALED_TILE_SIZE;

            batch.draw(tree.getSprite(), x, y, Settings.SCALED_TILE_SIZE, Settings.SCALED_TILE_SIZE);
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

