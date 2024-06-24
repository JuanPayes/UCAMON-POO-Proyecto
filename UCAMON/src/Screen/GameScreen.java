//xd
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
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.Input;

public class GameScreen  extends AbstractScreen {
    private Camara camara;
    private PlayerController control;
    private Entity player;
    private Texture playerStandingSouth;
    private SpriteBatch batch;
    private GameState gamestate;

    private List<TextureRegion> store;
    private List<TextureRegion> center;
    private List<TextureRegion> trees;
    private List<Entity> entities;
    private Music adventureTrack;
    private Music intro;
    private Music easterEgg;
    private Music menu;

    private TextureRegion[] treeTexture;

    private Texture Grass1, brownGrass1, brownGrass2, brownGrass3, HighGrass, road, northRoad, southRoad, brownGrass4, brownGrass5, brownGrass6, brownGrass7;

    private TileMap map;

    public GameScreen(Pokemon app) {
        super(app);
        this.gamestate = GameState.TITLESCREEN; // Inicializa el estado del juego a la pantalla de título.
        this.control = new PlayerController(player);

    adventureTrack = Gdx.audio.newMusic(Gdx.files.internal("resources/Music/adventure_Track.mp3"));
    adventureTrack.setLooping(true);
    adventureTrack.setVolume(0.1f);


    intro = Gdx.audio.newMusic(Gdx.files.internal("resources/Music/Intro.mp3"));
    intro.setLooping(true);
    intro.setVolume(0.1f);


    easterEgg = Gdx.audio.newMusic(Gdx.files.internal("resources/Music/EasterEgg.mp3"));
    easterEgg.setLooping(true);
    easterEgg.setVolume(0.1f);

    menu = Gdx.audio.newMusic(Gdx.files.internal("resources/Music/Menu.mp3"));
    menu.setLooping(true);
    menu.setVolume(0.1f);





        playerStandingSouth = new Texture("resources/unpacked/RedStanding_South.png");

        Grass1 = new Texture("resources/Tiles/grass.png");
        brownGrass1 = new Texture("resources/Tiles/Path/brownPath_west.png"); //west
        brownGrass2 = new Texture("resources/Tiles/Path/brownPath_center.png"); //center
        brownGrass3 = new Texture("resources/Tiles/Path/brownPath_east.png"); //east
        brownGrass4 = new Texture("resources/Tiles/Path/brownPath_north.png"); //north
        brownGrass5 = new Texture("resources/Tiles/Path/brownPath_south.png"); //south
        HighGrass = new Texture("resources/Tiles/high_Grass.png");
        road = new Texture("resources/Tiles/Road/roadPath.png");
        northRoad = new Texture("resources/Tiles/Road/roadPath_North.png");
        southRoad = new Texture("resources/Tiles/Road/roadPath_South.png");
        batch = new SpriteBatch();

        store = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            store.add(new TextureRegion(new Texture("resources/Tiles/Store/pokeStore_" + i + ".png")));
        }

        center = new ArrayList<>();
        for (int i = 0; i < 25; i++) {
            center.add(new TextureRegion(new Texture("resources/Tiles/Center/pokeCenter_" + i + ".png")));
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

        map = new TileMap(20, 36, Grass1);
        int[][] treePositions = {
                {0, 10}, {0, 14}, {0, 18}, {18, 10}, {18, 14}, {18, 18}, {18, 6},
                {0, 6}, {0, 22}, {0, 26}, {18, 22}, {18, 26}, {18, 33}, {0, 33}
        };
        int[][] highGrassRegions = {
                {10, 19, 13, 18}, {24, 27, 13, 18}, {24, 27, 2, 7}
        };

        player = new Entity(map, 10, 1, animations);
        camara = new Camara();

        control = new PlayerController(player);

        for (int y = 0; y < map.getHeight(); y++) {
            map.setTile(10, y, brownGrass2);
            map.setTile(11, y, brownGrass2);
            map.setTile(9, y, brownGrass2);
            map.setTile(8, y, new TextureRegion(brownGrass1));
            map.setTile(12, y, new TextureRegion(brownGrass3));
        }

        for (int[] region : highGrassRegions) {
            int startY = region[0];
            int endY = region[1];
            int startX = region[2];
            int endX = region[3];
            for (int y = startY; y <= endY; y++) {
                for (int x = startX; x < endX; x++) {
                    map.setTile(x, y, new TextureRegion(HighGrass));
                }
            }
        }

        entities = new ArrayList<>();

        for (int[] pos : treePositions) {
            addTree(map, pos[0], pos[1]);
        }

        addPokeStore(map, 2, 15);
        addPokeCenter(map, 2, 21);
        addHorizontalRoad(map, 0, map.getWidth() - 1, 29);
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

    private void addHorizontalRoad(TileMap map, int startX, int endX, int y) {
        for (int x = startX; x <= endX; x++) {
            map.setTile(x, y, new TextureRegion(road));        // Camino central
            map.setTile(x, y + 1, new TextureRegion(northRoad));  // Parte superior del camino
            map.setTile(x, y - 1, new TextureRegion(southRoad));  // Parte inferior del camino
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
        if(gamestate==GameState.TITLESCREEN){
            intro.play();
            drawTitleScreen();
        } else if (gamestate==GameState.HISOTRY) {
            intro.pause();
            menu.play();
            drawStoryScreen();
        } else if(gamestate==GameState.GAME){
            menu.pause();
            adventureTrack.play();
            updateGameLogic(delta);
            clearScreen();
            drawGameWorld();
            drawEntities();
            drawPlayer();
        }

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


    public void drawTitleScreen(){
        batch.begin();
        batch.draw(new Texture("resources/TitleScreen/background.png"), 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        BitmapFont font = new BitmapFont();
        font.getData().setScale(1.6f);
        String comenzar = "Press ENTER to start a new game" ;
        GlyphLayout layout = new GlyphLayout(font, comenzar);
        float x = (Gdx.graphics.getWidth() - layout.width) / 2;
        float y = (Gdx.graphics.getHeight() + layout.height-200) / 2;
        font.draw(batch, layout, x, y);
        String cargar = "Press L to load";
        layout.setText(font, cargar);
        x = (Gdx.graphics.getWidth() - layout.width) / 2;
        y -= layout.height + 20;
        font.draw(batch, layout, x, y);

        batch.end();


        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            gamestate = GameState.HISOTRY;
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.L)){
            gamestate= GameState.CARGAR;
        }

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

    public void drawStoryScreen() {
        batch.begin();
        batch.draw(new Texture("resources/Dialog/Dialog.png"), 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        BitmapFont font = new BitmapFont();
        font.getData().setScale(1.0f);
        font.setColor(0, 0, 0, 1);

        String story = "Our adventure begins on the first day of class, when our protagonist, \n" +
                "excited to enter UCA, encounters an unexpected accident. \n" +
                "The legendary Pokémon Owluca  has taken control of the \n entire campus and has hypnotized  all the Pokémon and professors! \n" +
                "It is our duty to stop him and restore peace to UCA. \n" +
                "Only one student will be able to do it… \n";
        String [] storyLines=story.split("\n");
        float y = (Gdx.graphics.getHeight() + (storyLines.length * 30)) / 2;
        for (String line : storyLines) {
            GlyphLayout layout = new GlyphLayout(font, line);
            float x = (Gdx.graphics.getWidth() - layout.width) / 2;
            font.draw(batch, layout, x, y);
            y -= layout.height + 10; // Ajusta el espaciado entre líneas según sea necesario
        }

        String continueMessage = "Press ENTER to continue..";
        GlyphLayout continueLayout = new GlyphLayout(font, continueMessage);
        float continueX = (Gdx.graphics.getWidth() - continueLayout.width) / 2;
        float continueY = y - continueLayout.height - 20;
        font.draw(batch, continueLayout, continueX, continueY);

        batch.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            gamestate = GameState.GAME;
        }
    }






}


