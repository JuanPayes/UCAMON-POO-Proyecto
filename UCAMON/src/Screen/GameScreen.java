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
import com.badlogic.gdx.audio.Music;
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

    private List<TextureRegion> store;
    private List<TextureRegion> center;
    private List<TextureRegion> trees;
    private List<Entity> entities;
    private Music adventureTrack;

    private TextureRegion[] treeTexture;

    private Texture Grass1, BrownGrass1,BrownGrass2, BrownGrass3, HighGrass;

    private TileMap map;

    public GameScreen(Pokemon app) {
        super(app);
        this.control = new PlayerController(player);

        adventureTrack = Gdx.audio.newMusic(Gdx.files.internal("resources/Music/adventure_Track.mp3"));
        adventureTrack.setLooping(true);
        adventureTrack.play();

        playerStandingSouth = new Texture("resources/unpacked/RedStanding_South.png");

        Grass1 = new Texture("resources/Tiles/grass.png");
        BrownGrass1 = new Texture("resources/Tiles/brown_path_grass_west.png");
        BrownGrass2 = new Texture("resources/Tiles/brown_path.png");
        BrownGrass3 = new Texture("resources/Tiles/brown_path_grass_east.png");
        HighGrass = new Texture("resources/Tiles/high_Grass.png");
        batch = new SpriteBatch();

        store = new ArrayList<>();
        for (int i = 0; i < 16 ;i++){
            store.add(new TextureRegion(new Texture("resources/Tiles/Store/pokeStore_"+i+".png")));

        }

        center = new ArrayList<>();
        for (int i = 0; i < 25 ;i++){
            center.add(new TextureRegion(new Texture("resources/Tiles/Center/pokeCenter_"+i+".png")));
        }

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
        int[][] treePositions = {{0, 2}, {0, 6}, {0, 10}, {18, 2}, {18, 6}, {18, 10}};
        player = new Entity(map, 10, 1, animations);
        camara = new Camara();

        control = new PlayerController(player);

        for (int y = 0; y < map.getHeight(); y++) {
            map.setTile(10, y, BrownGrass2);
            map.setTile(11, y, BrownGrass2);
            map.setTile(9, y, BrownGrass2);
            map.setTile(8, y, new TextureRegion(BrownGrass1));
            map.setTile(12, y, new TextureRegion(BrownGrass3));
        }

        for (int y = 0; y < map.getHeight(); y++) {
            if (y >= 1 && y <= 10) {
                for (int x = 13; x < 18; x++) {
                    map.setTile(x, y, new TextureRegion(HighGrass));
                }
            }
        }

        entities = new ArrayList<>();

        for (int[] pos : treePositions) {
            addTree(map, pos[0], pos[1]);
        }

        addPokeStore(map, 2, 5);

        addPokeCenter(map, 2,11);
    }

    private void addTree(TileMap map, int x, int y) {
        int[][] treeCoords = {
                {x, y},
                {x + 1, y},
                {x, y - 1},
                {x + 1, y - 1},
                {x, y - 2},
                {x + 1, y - 2}
        };
        for (int i = 0; i < 6; i++) {
            entities.add(new Entity(map, treeCoords[i][0], treeCoords[i][1], trees.get(i)));
        }
    }

    public void addPokeStore(TileMap map, int startX, int startY) {
        int[][] storeLayout = {
                {0, 1, 2, 3},
                {4, 5, 6, 7},
                {8, 9, 10, 11},
                {12, 13, 14, 15}
        };

        for (int y = 0; y < storeLayout.length; y++) {
            for (int x = 0; x < storeLayout[y].length; x++) {
                int tileIndex = storeLayout[y][x];
                if (tileIndex >= 0) {
                    addTile(map, startX + x, startY - y, store.get(tileIndex));
                }
            }
        }
    }

    public void addPokeCenter(TileMap map, int startX, int startY) {
        int[][] centerLayout = {
                {0, 1, 2, 3, 4},
                {5, 6, 7, 8, 9},
                {10, 11, 12, 13, 14},
                {15, 16, 17, 18, 19},
                {20, 21, 22, 23, 24}
        };

        for (int y = 0; y < centerLayout.length; y++) {
            for (int x = 0; x < centerLayout[y].length; x++) {
                int tileIndex = centerLayout[y][x];
                if (tileIndex >= 0) {
                    addTile(map, startX + x, startY - y, center.get(tileIndex));
                }
            }
        }
    }

    private void addTile(TileMap map, int x, int y, TextureRegion tile) {
        Entity buildingTile = new Entity(map, x, y, tile);
        entities.add(buildingTile);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(control);
    }

    @Override
    public void render(float delta) {
        updateGameLogic(delta);
        clearScreen();
        drawGameWorld();
        drawEntities();
        drawPlayer();
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
        batch.begin();
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
        batch.end();
    }

    private void drawEntities() {
        batch.begin();
        for (Entity tree : entities) {
            float worldStartX = Gdx.graphics.getWidth() / 2 - camara.getCamaraX();
            float worldStartY = Gdx.graphics.getHeight() / 2 - camara.getCamaraY();
            batch.draw(tree.getSprite(),
                    worldStartX + tree.getWorldX() * Settings.SCALED_TILE_SIZE,
                    worldStartY + tree.getWorldY() * Settings.SCALED_TILE_SIZE,
                    Settings.SCALED_TILE_SIZE,
                    Settings.SCALED_TILE_SIZE * 1.5f);
        }
        batch.end();
    }

    private void drawPlayer() {
        batch.begin();
        float worldStartX = Gdx.graphics.getWidth() / 2 - camara.getCamaraX();
        float worldStartY = Gdx.graphics.getHeight() / 2 - camara.getCamaraY();
        batch.draw(player.getSprite(),
                worldStartX + player.getWorldX() * Settings.SCALED_TILE_SIZE,
                worldStartY + player.getWorldY() * Settings.SCALED_TILE_SIZE,
                Settings.SCALED_TILE_SIZE,
                Settings.SCALED_TILE_SIZE * 1.5f);
        batch.end();
    }

    private void updateCamera() {
        float playerWorldX = player.getWorldX() * Settings.SCALED_TILE_SIZE + Settings.SCALED_TILE_SIZE / 2;
        float playerWorldY = player.getWorldY() * Settings.SCALED_TILE_SIZE + Settings.SCALED_TILE_SIZE / 2;
        camara.update(playerWorldX, playerWorldY);
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

