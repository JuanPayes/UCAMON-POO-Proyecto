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
    private BitmapFont font;
    private Camara camara;
    private PlayerController control;
    private Entity player, npcOne;
    AnimationSet npcAnimations;
    private Texture playerStandingSouth, npcOneTexture, dialogBoxTexture;
    private boolean isCinematicActive, isDialogueActive, shouldNpcMove;
    private SpriteBatch batch;

    private GameState gamestate;


    private List<TextureRegion> pilar;

    private List<TextureRegion> store;
    private List<TextureRegion> center;
    private List<TextureRegion> trees;
    private List<Entity> entities;
    private List<TextureRegion> librery;

    private List<String> npcDialogues;
    private int currentDialogueIndex;

    private Music adventureTrack;
    private Music intro;
    private Music easterEgg;
    private Music menu;
    private boolean showSaveConfirmation;
    private float saveConfirmationTime;
    private String saveMessage, npcDialogueText;

    private TextureRegion[] treeTexture;
    private Texture Grass1, brownGrass1, brownGrass2, brownGrass3, HighGrass, road, northRoad, southRoad, brownGrass4, brownGrass5, brownGrass6, brownGrass7;
    private Texture pea1, pea2, pea3, pea4, pea5, pea6;
    private TileMap map;

    public GameScreen(Pokemon app) {
        super(app);
        this.gamestate = GameState.TITLESCREEN;
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
        pea1 = new Texture("resources/Tiles/Pea/pea_center.png");
        pea2 = new Texture("resources/Tiles/Pea/pea_east.png");
        pea3 = new Texture("resources/Tiles/Pea/pea_north.png");
        pea4 = new Texture("resources/Tiles/Pea/pea_west.png");
        pea5 = new Texture("resources/Tiles/Pea/pea_north_east.png");
        pea6 = new Texture("resources/Tiles/Pea/pea_north_west.png");

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

        librery = new ArrayList<>();
        for (int i = 0; i < 54; i++) {
            librery.add(new TextureRegion(new Texture("resources/Tiles/UcaLib/librery_" + i + ".png")));
        }

        pilar = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            pilar.add(new TextureRegion(new Texture("resources/Tiles/Pilar/pilar_"+i+".png")));
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

        //TextureAtlas npcAtlas = app.getAssetManager().get("resources/packed/npc_textures.atlas", TextureAtlas.class);

        npcAnimations = new AnimationSet(
                new Animation(0.3f / 2f, atlas.findRegions("NPC_Walking_North"), PlayMode.LOOP_PINGPONG),
                new Animation(0.3f / 2f, atlas.findRegions("NPC_Walking_South"), PlayMode.LOOP_PINGPONG),
                new Animation(0.3f / 2f, atlas.findRegions("NPC_Walking_East"), PlayMode.LOOP_PINGPONG),
                new Animation(0.3f / 2f, atlas.findRegions("NPC_Walking_West"), PlayMode.LOOP_PINGPONG),
                atlas.findRegion("NPC_Standing_North"),
                atlas.findRegion("NPC_Standing_South"),
                atlas.findRegion("NPC_Standing_East"),
                atlas.findRegion("NPC_Standing_West")
        );

        map = new TileMap(20, 36, Grass1);

        npcOneTexture = new Texture("resources/unpacked/NPC_Standing_South.png");

        font = new BitmapFont();
        font.getData().setScale(1.0f);
        font.setColor(0, 0, 0, 1);
        dialogBoxTexture = new Texture("resources/Dialog/textBox.png");
        npcDialogueText = "";
        isDialogueActive = false;

        npcDialogues = new ArrayList<>();
        npcDialogues.add("Hey! This place is absolute chaos!");
        npcDialogues.add("You want to go in? Here, I'll give you a couple of \nthings that can help! \n(the NPC has has given you 100 coins, lucky you!).");
        npcDialogues.add("That's all I have left. When the pokemon \nwent crazy, they stole all my money!");
        npcDialogues.add("Surely whenever you defeat one, \nthey'll drop some of the money they stole from me! \nThose darn pokemon...");
        npcDialogues.add("Anyway, here, to help you in your journey, \nchoose one of these pokemon. \nI hope they help in your adventure!");
        npcDialogues.add("1. Charmander. \n2. Bulbasaur.\n3. Squirtle.");
        npcDialogues.add("Remember, Pokemons that are faster than yours \nattack first, so be careful!");
        npcDialogues.add("Oh, one more thing! I've heard people say there is an extremely \nrare pokemon roaming around... beware if you find it, \nit must be very powerful!");
        npcDialogues.add("That's all I can do for you! \nPlease beware and save us all from OwlUCA!");
        currentDialogueIndex = 0;

        int[][] treePositions = {{0,2}, {18, 2},{0, 10}, {0, 14}, {0, 18}, {18, 10}, {18, 14}, {18, 18},{18, 6},{0, 6},{0,22},{0,25},{18,22},{18,25},{18,33},{0,33}
        };
        int[][] highGrassRegions = {{10, 19, 13, 18}, {22, 25, 13, 18}, {22, 25, 2, 7}
        };
        int[][] pilarPostion = {{5, 2},{14, 2}

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

        for (int[] pos : pilarPostion) {
            addPilar(map, pos[0], pos[1]);
        }

        addPokeStore(map, 2, 15);

        addPokeCenter(map, 2, 21);

        addHorizontalRoad(map, 0, map.getWidth() - 1, 28);

        addUcaLib(map, 6, 35);

        addEntrance(map, 6, 0);

        addNPC(map, 6, 4);
        startCinematic();
    }

    private void addNPC(TileMap map, int x, int y) {
        npcOne = new Entity(map, x, y, npcAnimations);
        entities.add(npcOne);
    }

    private void startCinematic() {
        isCinematicActive = true;
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

    private void addPilar(TileMap map, int x, int y ){
        int[][] pilarCoords = {
                {x, y},
                {x + 1, y},
                {x, y - 1},
                {x + 1, y - 1},
                {x, y - 2},
                {x + 1, y - 2},
        };
        for (int i = 0; i < 6; i++) {
            entities.add(new Entity(map, pilarCoords[i][0],pilarCoords[i][1], pilar.get(i)));
        }
    }

    private void addPokeStore(TileMap map, int startX, int startY) {
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

    private void addPokeCenter(TileMap map, int startX, int startY) {
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

    private void addUcaLib(TileMap map, int startX, int startY) {
        int[][] ucaLayout = {
                //{0, 1, 2, 3, 4, 5, 6, 7, 8},
                {9, 10, 11, 12, 13, 14, 15, 16, 17},
                {18, 19, 20, 21, 22, 23, 24, 25, 26},
                {27, 28, 29, 30, 31, 32, 33, 34, 35},
                {36, 37, 38, 39, 40, 41, 42, 43, 44},
                {45, 46, 47, 48, 49, 50, 51, 52, 53},
        };
        for (int y = 0; y < ucaLayout.length; y++) {
            for (int x = 0; x < ucaLayout[y].length; x++) {
                int tileIndex = ucaLayout[y][x];
                if (tileIndex >= 0) {
                    addTile(map, startX + x, startY - y, librery.get(tileIndex));
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

    private void addEntrance(TileMap map, int startX, int y) {

        int[][] layout = {
                {startX, y, 4},
                {startX + 1, y, 1},
                {startX + 2, y, 1},
                {startX + 3, y, 1},
                {startX + 4, y, 1},
                {startX + 5, y, 1},
                {startX + 6, y, 1},
                {startX + 7, y, 1},
                {startX + 8, y, 2},
                {startX, y + 1, 6},
                {startX + 1, y + 1, 3},
                {startX + 2, y + 1, 3},
                {startX + 3, y + 1, 3},
                {startX + 4, y + 1, 3},
                {startX + 5, y + 1, 3},
                {startX + 6, y + 1, 3},
                {startX + 7, y + 1, 3},
                {startX + 8, y + 1, 5}
        };


        for (int[] pos : layout) {
            int x = pos[0];
            int tileY = pos[1];
            int textureIndex = pos[2];

            TextureRegion texture;
            switch (textureIndex) {
                case 1:
                    texture = new TextureRegion(pea1);
                    break;
                case 2:
                    texture = new TextureRegion(pea2);
                    break;
                case 3:
                    texture = new TextureRegion(pea3);
                    break;
                case 4:
                    texture = new TextureRegion(pea4);
                    break;
                case 5:
                    texture = new TextureRegion(pea5);
                    break;
                case 6:
                    texture = new TextureRegion(pea6);
                    break;
                default:
                    texture = new TextureRegion(pea1);
                    break;
            }
            map.setTile(x, tileY, texture);
        }
    }
    private void addTile(TileMap map, int x, int y, TextureRegion tile) {
        Entity buildingTile = new Entity(map, x, y, tile);
        entities.add(buildingTile);
    }

    @Override
    public void show() {
        if (!isCinematicActive && !isDialogueActive) {
            Gdx.input.setInputProcessor(control);
        }
    }

    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            if (gamestate == GameState.GAME) {
                gamestate = GameState.PAUSE;
            } else if (gamestate == GameState.PAUSE) {
                gamestate = GameState.GAME;
            }
        }

        if (isDialogueActive && Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            currentDialogueIndex++;
            if (currentDialogueIndex < npcDialogues.size()) {
                npcDialogueText = npcDialogues.get(currentDialogueIndex);
            } else {
                isDialogueActive = false;
                Gdx.input.setInputProcessor(control);
                shouldNpcMove = true;
                npcOne.setState(ACTORSTATE.WALKING);
            }
        }

        switch (gamestate) {
            case TITLESCREEN:
                intro.play();
                drawTitleScreen();
                break;
            case HISOTRY:
                intro.pause();
                menu.play();
                drawStoryScreen();
                break;
            case GAME:
                menu.pause();
                adventureTrack.play();
                if (isCinematicActive) {
                    updateCinematic(delta);
                }else{
                    updateGameLogic(delta);
                }
                clearScreen();
                drawGameWorld();
                drawEntities();
                drawPlayer();
                updateCamera();
                if (isDialogueActive) {
                    drawDialogueBox();
                }
                break;
            case CARGAR:

                break;
            case PAUSE:
                adventureTrack.pause();
                drawPauseMenu();
                break;
            case BAG:
                drawBag();
                break;
            case CINEMATIC:
                updateCinematic(delta);
                clearScreen();
                drawGameWorld();
                drawEntities();
                drawPlayer();
                updateCamera();
                if (isDialogueActive) {
                    drawDialogueBox();
                }
                break;
            default:
                break;
        }
        if (showSaveConfirmation) {
            drawSaveConfirmationMessage();
            saveConfirmationTime -= delta;
            if (saveConfirmationTime <= 0) {
                showSaveConfirmation = false;
            }
        }
    }

    private void updateCinematic(float delta) {
        npcOne.update(delta);
        if (npcOne.getState() == ACTORSTATE.STANDING) {
            if (npcReachedPlayer()) {
                startDialogue();
                isCinematicActive = false;
            } else {
                moveNpcTowardsPlayer();
            }
        }
    }

    private void startDialogue() {
        isDialogueActive = true;
        currentDialogueIndex = 0;
        npcDialogueText = npcDialogues.get(currentDialogueIndex);
    }

    private boolean npcReachedPlayer() {
        return (npcOne.getX() == player.getX() && Math.abs(npcOne.getY() - player.getY()) == 1) ||
                (npcOne.getY() == player.getY() && Math.abs(npcOne.getX() - player.getX()) == 1);
    }

    private void moveNpcTowardsPlayer() {
        int playerX = player.getX();
        int playerY = player.getY();


        if (npcOne.getX() < playerX) {
            npcOne.move(DIRECTION.EAST);
        } else if (npcOne.getX() > playerX) {
            npcOne.move(DIRECTION.WEST);
        } else if (npcOne.getY() < playerY - 1) {
            npcOne.move(DIRECTION.NORTH);
        } else if (npcOne.getY() > playerY + 1) {
            npcOne.move(DIRECTION.SOUTH);
        }
    }

    private void checkForScreenTransition() {
        if (player.getWorldX() == 10 && player.getWorldY() == 10) {
            getApp().setScreen(new Floor1Screen(getApp()));
        }

    }


    private void updateGameLogic(float delta) {
        if (!isCinematicActive && !isDialogueActive) {
            control.update(delta);
            player.update(delta);
            if (shouldNpcMove) {
                moveNpcAfterDialogue(delta);
            }
        }
        updateCamera();
    }

    private void moveNpcAfterDialogue(float delta) {
        int targetX = 5;

        if (npcOne.getX() > targetX) {
            npcOne.move(DIRECTION.WEST);
            npcOne.update(delta);
        } else {
            shouldNpcMove = false;
            npcOne.setState(ACTORSTATE.STANDING);
        }
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
        batch.dispose();
        adventureTrack.dispose();
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


    public void drawPauseMenu() {
        batch.begin();
        BitmapFont font = new BitmapFont();
        font.getData().setScale(1.6f);

        String[] options = { "Bag", "Save Game", "Quit Game", "Return" };
        float y = Gdx.graphics.getHeight() / 2 + options.length * 20;
        for (String option : options) {
            GlyphLayout layout = new GlyphLayout(font, option);
            float x = (Gdx.graphics.getWidth() - layout.width) / 2;
            font.draw(batch, layout, x, y);
            y -= layout.height + 20;
        }

        batch.end();

        handlePauseMenuInput();
    }

    public void handlePauseMenuInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
            // Handle Bag
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
             handleSaveGame();
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)) {
            Gdx.app.exit();
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_4)) {
            gamestate = GameState.GAME;
        }
    }

    public void handleSaveGame() {
        saveGame();
        saveMessage = "Game saved successfully.";
        showSaveConfirmation = true;
        saveConfirmationTime = 3.0f;
    }

    public void saveGame() {

    }



    private void drawSaveConfirmationMessage() {
        batch.begin();

        // Configurar el fondo del mensaje
        TextureRegion background = new TextureRegion(new Texture(Gdx.files.internal("resources/Dialog/textBox.png")));
        float bgWidth = Gdx.graphics.getWidth() * 0.8f; // Ancho del fondo
        float bgHeight = 60; // Alto del fondo
        float bgX = (Gdx.graphics.getWidth() - bgWidth) / 2; // Posición X del fondo
        float bgY = Gdx.graphics.getHeight() / 2 - bgHeight / 2; // Posición Y del fondo
        batch.draw(background, bgX, bgY, bgWidth, bgHeight);

        // Configurar el texto
        BitmapFont font = new BitmapFont();
        font.getData().setScale(1.0f);
        font.setColor(0, 0, 0, 1);
        GlyphLayout layout = new GlyphLayout(font, "Game saved successfully!");
        float textX = (Gdx.graphics.getWidth() - layout.width) / 2; // Posición X del texto
        float textY = Gdx.graphics.getHeight() / 2 + layout.height / 2; // Posición Y del texto
        font.draw(batch, layout, textX, textY);

        batch.end();
    }

    private void drawDialogueBox() {
        if (isDialogueActive) {
            batch.begin();


            float dialogBoxWidth = Gdx.graphics.getWidth() * 0.8f;
            float dialogBoxHeight = Gdx.graphics.getHeight() * 0.2f;
            float dialogBoxX = (Gdx.graphics.getWidth() - dialogBoxWidth) / 2;
            float dialogBoxY = Gdx.graphics.getHeight() * 0.1f;
            batch.draw(dialogBoxTexture, dialogBoxX, dialogBoxY, dialogBoxWidth, dialogBoxHeight);


            GlyphLayout layout = new GlyphLayout(font, npcDialogueText);
            float textX = dialogBoxX + (dialogBoxWidth - layout.width) / 2;
            float textY = dialogBoxY + (dialogBoxHeight + layout.height) / 2;
            font.draw(batch, layout, textX, textY);

            batch.end();
        }
    }

public void drawBag(){
        /*
    batch.begin();

    // Dibujar la imagen del bolso (bag)
    Texture bagImage = new Texture(""); // Reemplaza con la ruta de tu imagen
    float bagX = (Gdx.graphics.getWidth() - bagImage.getWidth()) / 2;
    float bagY = (Gdx.graphics.getHeight() - bagImage.getHeight()) / 2;
    batch.draw(bagImage, bagX, bagY);

    // Dibujar texto para volver al menú principal
    BitmapFont font = new BitmapFont();
    font.getData().setScale(1.2f);
    GlyphLayout layout = new GlyphLayout(font, "Press ESC to return to main menu");
    float textX = (Gdx.graphics.getWidth() - layout.width) / 2;
    float textY = 50;
    font.draw(batch, layout, textX, textY);

    batch.end();

    if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
        gamestate = GameState.PAUSE;
    }

         */
}
}





