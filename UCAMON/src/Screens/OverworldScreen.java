package Screens;

import Controller.PlayerController;
import Entities.Camara;
import Entities.RenderableEntity;
import GameState.BattleStates.BattleTrainerState;
import GameState.BattleStates.WildPokemonBattleState;
import GameState.BuyingState;
import GameState.GameContext;
import GameState.MenuStates.PauseState;
import GameState.PokemonCenterState;
import Main.Config;
import Main.GameApp;
import PokemonFactory.FactoryClient;
import PokemonFactory.PokemonEnum;
import Rooms.Floor1;
import Rooms.OverWorld;
import Rooms.Room;
import Rooms.RoomPosition;
import Tiles.Tile;
import Tiles.TileMap;
import Util.AnimationSet;
import Util.DIRECTION;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import Entities.ACTORSTATE;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import static com.badlogic.gdx.math.MathUtils.random;

public class OverworldScreen extends AbstractScreen {
    private Music adventureTrack;
    private TileMap map;
    private SpriteBatch batch;
    private PlayerController control;
    private Camara camara;
    private Room currentFloor;
    private AnimationSet animations, npc1Animations, npcTwoAnimations, npcMarioAnimations;
    private boolean isCinematicActive, isDialogueActive, shouldNpcMove;
    private BitmapFont font;
    private List<String> npcDialogues,bossOneDialogues, marioDialogues;
    private Texture dialogBoxTexture;
    private String npcOneDialogueText, bossOneDialogueText, marioDialogueText;


    private RenderableEntity player, npcOne, npcTwo, npcThree ;

    private List<RenderableEntity> entities, npcs;
    private Stack<RoomPosition> roomStack;

    private static final long BATTLE_COOLDOWN_MS = 2000; // 2 seconds cooldown
    private static final long HIGH_GRASS_TIME_THRESHOLD_MS = 3000; // 3 seconds in high grass
    private long lastBattleTime = 0;
    private long highGrassEntryTime = 0;
    private boolean isInHighGrass = false;
    protected  boolean ableToBattle = true;
    public Room nextRoom;
    public int newX, newY, currentNpcOneDialogueIndex, currentBossOneDialogueIndex, currentMarioDialogueIndex,selectedPokemon;

    public OverworldScreen(GameApp app) {
        super(app);
        adventureTrack = Gdx.audio.newMusic(Gdx.files.internal("resources/Music/adventure_Track.mp3"));
        adventureTrack.setLooping(true);
        adventureTrack.setVolume(0.1f);
        batch = new SpriteBatch();

        TextureAtlas atlas = app.getAssetManager().get("resources/packed/textures.atlas", TextureAtlas.class);

        animations = new AnimationSet(
                new Animation(0.3f / 2f, atlas.findRegions("RedWalking_North"), Animation.PlayMode.LOOP_PINGPONG),
                new Animation(0.3f / 2f, atlas.findRegions("RedWalking_South"), Animation.PlayMode.LOOP_PINGPONG),
                new Animation(0.3f / 2f, atlas.findRegions("RedWalking_East"), Animation.PlayMode.LOOP_PINGPONG),
                new Animation(0.3f / 2f, atlas.findRegions("RedWalking_West"), Animation.PlayMode.LOOP_PINGPONG),
                atlas.findRegion("RedStanding_North"),
                atlas.findRegion("RedStanding_South"),
                atlas.findRegion("RedStanding_East"),
                atlas.findRegion("RedStanding_West")
        );

        npc1Animations = new AnimationSet(
                new Animation(0.3f / 2f, atlas.findRegions("NPC_Walking_North"), Animation.PlayMode.LOOP_PINGPONG),
                new Animation(0.3f / 2f, atlas.findRegions("NPC_Walking_South"), Animation.PlayMode.LOOP_PINGPONG),
                new Animation(0.3f / 2f, atlas.findRegions("NPC_Walking_East"), Animation.PlayMode.LOOP_PINGPONG),
                new Animation(0.3f / 2f, atlas.findRegions("NPC_Walking_West"), Animation.PlayMode.LOOP_PINGPONG),
                atlas.findRegion("NPC_Standing_North"),
                atlas.findRegion("NPC_Standing_South"),
                atlas.findRegion("NPC_Standing_East"),
                atlas.findRegion("NPC_Standing_West")
        );

        npcTwoAnimations = new AnimationSet(
                new Animation(0.3f / 2f, atlas.findRegions("NPC3_Walking_North"), Animation.PlayMode.LOOP_PINGPONG),
                new Animation(0.3f / 2f, atlas.findRegions("NPC3_Walking_South"), Animation.PlayMode.LOOP_PINGPONG),
                new Animation(0.3f / 2f, atlas.findRegions("NPC3_Walking_East"), Animation.PlayMode.LOOP_PINGPONG),
                new Animation(0.3f / 2f, atlas.findRegions("NPC3_Walking_West"), Animation.PlayMode.LOOP_PINGPONG),
                atlas.findRegion("NPC3_Standing_North"),
                atlas.findRegion("NPC3_Standing_South"),
                atlas.findRegion("NPC3_Standing_East"),
                atlas.findRegion("NPC3_Standing_West")
        );
        npcMarioAnimations = new AnimationSet(
                new Animation(0.3f / 2f, atlas.findRegions("NPC2_Walking_North"), Animation.PlayMode.LOOP_PINGPONG),
                new Animation(0.3f / 2f, atlas.findRegions("NPC2_Walking_South"), Animation.PlayMode.LOOP_PINGPONG),
                new Animation(0.3f / 2f, atlas.findRegions("NPC2_Walking_East"), Animation.PlayMode.LOOP_PINGPONG),
                new Animation(0.3f / 2f, atlas.findRegions("NPC2_Walking_West"), Animation.PlayMode.LOOP_PINGPONG),
                atlas.findRegion("NPC2_Standing_North"),
                atlas.findRegion("NPC2_Standing_South"),
                atlas.findRegion("NPC2_Standing_East"),
                atlas.findRegion("NPC2_Standing_West")
        );


        npcDialogues = new ArrayList<>();
        npcDialogues.add("Hey! This place is absolute chaos!");
        npcDialogues.add("You want to go in? Here, I'll give you a couple of \nthings that can help! \n(the NPC has has given you 100 coins, lucky you!).");
        npcDialogues.add("That's all I have left. When the pokemon \nwent crazy, they stole all my money!");
        npcDialogues.add("Surely whenever you defeat one, \nthey'll drop some of the money they stole from me! \nThose darn pokemon...");
        npcDialogues.add("Anyway, here, to help you in your journey, \nchoose one of these pokemon. \nI hope they help in your adventure! (Press 1, 2 or 3)");
        npcDialogues.add("1. Charmander. \n2. Bulbasaur.\n3. Squirtle.");
        npcDialogues.add("Remember, Pokemons that are faster than yours \nattack first, so be careful!");
        npcDialogues.add("Oh, one more thing! I've heard people say there is an extremely \nrare pokemon roaming around... beware if you find it, \nit must be very powerful!");
        npcDialogues.add("Remember, buy many pokeballs and always heal your pokemons \n before any battle! Otherwise they won't come out!.");
        npcDialogues.add("If you need a break, you can press 'ESC' to pause and un-pause.");
        npcDialogues.add("Oh! And remember you buy stuff pressing the keys 1 and 2 when you are\n at the store!");
        npcDialogues.add("That's all I can do for you! \nPlease beware and save us all from OwlUCA!");
        currentNpcOneDialogueIndex = 0;

        bossOneDialogues = new ArrayList<>();
        bossOneDialogues.add("Hey! You can't go through there!");
        bossOneDialogues.add("Without battling me first!");
        currentBossOneDialogueIndex = 0;

        marioDialogues = new ArrayList<>();
        marioDialogues.add("Hello");
        marioDialogues.add("World");
        currentMarioDialogueIndex = 0;

        font = new BitmapFont();
        font.getData().setScale(1.0f);
        font.setColor(0, 0, 0, 1);
        dialogBoxTexture = new Texture("resources/Dialog/textBox.png");
        npcOneDialogueText = "";
        bossOneDialogueText = "";
        marioDialogueText = "";
        isDialogueActive = false;

        entities = new ArrayList<>();
        npcs = new ArrayList<>();
        OverWorld overWorld = new OverWorld(new TileMap(20, 36, new Texture("resources/Tiles/grass.png")), entities);
        overWorld.initialize();

        this.currentFloor = overWorld;

        player = new RenderableEntity(currentFloor.getMap(), 10, 1, animations);
        camara = new Camara();
        control = new PlayerController(player);

        roomStack = new Stack<>();

        addNPC(currentFloor.getMap(), 6, 4);
        startCinematic();

    }

    protected void drawGameWorld() {
        batch.begin();
        float worldStartX = Gdx.graphics.getWidth() / 2 - camara.getCamaraX();
        float worldStartY = Gdx.graphics.getHeight() / 2 - camara.getCamaraY();
        TileMap map = currentFloor.getMap();
        for (int x = 0; x < map.getWidth(); x++) {
            for (int y = 0; y < map.getHeight(); y++) {
                Tile tile = map.getTile(x, y);
                Texture render = tile.getTexture();
                batch.draw(render,
                        worldStartX + x * Config.SCALED_TILE_SIZE,
                        worldStartY + y * Config.SCALED_TILE_SIZE,
                        Config.SCALED_TILE_SIZE,
                        Config.SCALED_TILE_SIZE);
            }
        }
        batch.end();
    }

    protected void drawEntities() {
        batch.begin();
        for (RenderableEntity entity : entities) {
            float worldStartX = Gdx.graphics.getWidth() / 2 - camara.getCamaraX();
            float worldStartY = Gdx.graphics.getHeight() / 2 - camara.getCamaraY();
            batch.draw(entity.getSprite(),
                    worldStartX + entity.getWorldX() * Config.SCALED_TILE_SIZE,
                    worldStartY + entity.getWorldY() * Config.SCALED_TILE_SIZE,
                    Config.SCALED_TILE_SIZE,
                    Config.SCALED_TILE_SIZE * 1.5f);
        }

        for (RenderableEntity npc : npcs) {
            float worldStartX = Gdx.graphics.getWidth() / 2 - camara.getCamaraX();
            float worldStartY = Gdx.graphics.getHeight() / 2 - camara.getCamaraY();
            batch.draw(npc.getSprite(),
                    worldStartX + npc.getWorldX() * Config.SCALED_TILE_SIZE,
                    worldStartY + npc.getWorldY() * Config.SCALED_TILE_SIZE,
                    Config.SCALED_TILE_SIZE,
                    Config.SCALED_TILE_SIZE * 1.5f);
        }
        batch.end();
    }

    protected void drawPlayer() {
        batch.begin();
        float worldStartX = Gdx.graphics.getWidth() / 2 - camara.getCamaraX();
        float worldStartY = Gdx.graphics.getHeight() / 2 - camara.getCamaraY();
        batch.draw(player.getSprite(),
                worldStartX + player.getWorldX() * Config.SCALED_TILE_SIZE,
                worldStartY + player.getWorldY() * Config.SCALED_TILE_SIZE,
                Config.SCALED_TILE_SIZE,
                Config.SCALED_TILE_SIZE * 1.5f);
        batch.end();
    }

    public void checkForPCenterInteraction() {
        float playerX = player.getWorldX();
        float playerY = player.getWorldY();

        int PCenterEntranceX = 4;
        int PCenterEntranceY = 16;

        if (playerX == PCenterEntranceX && playerY == PCenterEntranceY) {
            drawPCenterPrompt();
            if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                GameContext.getInstance().setState(new PokemonCenterState());
            }
        }

    }

    private void drawPCenterPrompt() {
        batch.begin();
        BitmapFont font = new BitmapFont();
        font.getData().setScale(1.7f);
        GlyphLayout layout = new GlyphLayout(font, "Press ENTER to enter Pokemon center");

        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        float textX = (screenWidth - layout.width) / 2;
        float textY = screenHeight * 0.1f;

        // Dibujar el texto
        font.draw(batch, layout, textX, textY);
        batch.end();
    }

    private void checkForStoreInteraction() {
        float playerX = player.getWorldX();
        float playerY = player.getWorldY();


        int storeEntranceX = 4;
        int storeEntranceY = 11;

        if (playerX == storeEntranceX && playerY == storeEntranceY) {
            drawStorePrompt();
            if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                GameContext.getInstance().setState(new BuyingState());
            }
        }
    }

    private void drawStorePrompt() {
        batch.begin();
        BitmapFont font = new BitmapFont();
        font.getData().setScale(1.7f);
        GlyphLayout layout = new GlyphLayout(font, "Press ENTER to enter the store");
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        float textX = (screenWidth - layout.width) / 2;
        float textY = screenHeight * 0.1f;
        font.draw(batch, layout, textX, textY);
        batch.end();
    }

    protected void updateCamera() {
        float playerWorldX = player.getWorldX() * Config.SCALED_TILE_SIZE + Config.SCALED_TILE_SIZE / 2;
        float playerWorldY = player.getWorldY() * Config.SCALED_TILE_SIZE + Config.SCALED_TILE_SIZE / 2;
        camara.update(playerWorldX, playerWorldY);
    }

    protected void updateGameLogic(float delta) {
        if (!isCinematicActive && !isDialogueActive) {
            control.update(delta);
        }
        player.update(delta);
        updateCamera();
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(control);
    }

    @Override
    public void render(float delta) {
        adventureTrack.play();
        updateGameLogic(delta);
        clearScreen();
        drawGameWorld();
        drawEntities();
        drawPlayer();
        checkForPCenterInteraction();
        checkForStoreInteraction();
        checkHighGrass();
        int playerX = player.getX();
        if (currentFloor.getID().equals("OverWorld") && player.getX() == 10 && player.getY() == 30 && Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            changeRoom(new Floor1(entities), 6, 0);
        } else if (currentFloor.getID().equals("Floor1") && player.getX() == 6 && player.getY() == 0 && Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            returnToPreviousRoom();
        }
        if (currentFloor.getID().equals("Floor1")){
            addNPC(currentFloor.getMap(), 5, 10);
            npcs.clear();
            npcs.add(npcTwo);
            addNPC(currentFloor.getMap(), 6, 17);
            if(player.getY() == 10 && GameContext.getInstance().getGamelevel() == 2 && ableToBattle){
                adventureTrack.stop();
                GameContext.getInstance().setState(new BattleTrainerState(1));
            }
            if (player.getY() == 16 && GameContext.getInstance().getGamelevel() >= 2 && ableToBattle){
                adventureTrack.stop();
                GameContext.getInstance().setState(new BattleTrainerState(2));
            }
        }
        updateCameraPosition();
        if (isCinematicActive) {
            updateCinematic(npcOne,delta);
        }
        if (isDialogueActive) {
            drawDialogueBox();
            handleDialogueInputNpcOne();
        }
        if (shouldNpcMove) {
            moveNpcAfterDialogue(delta);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            adventureTrack.stop();
            GameContext.getInstance().setState(new PauseState());
        }
    }

    protected void changeRoom(Room room, int newX, int newY) {

        this.nextRoom = room;
        this.newX = newX;
        this.newY = newY;
        GameApp app = GameContext.getInstance().getGame();
        app.setScreen(new TransitionScreen(app, this));
    }

    public void changeRoomImmediately(Room room, int newX, int newY) {
        roomStack.push(new RoomPosition(currentFloor, player.getX(), player.getY(), new ArrayList<>(entities)));

        entities.clear();

        TileMap oldMap = currentFloor.getMap();
        for (int x = 0; x < oldMap.getWidth(); x++) {
            for (int y = 0; y < oldMap.getHeight(); y++) {
                oldMap.getTile(x, y).setEntity(null);
            }
        }

        this.currentFloor = room;
        currentFloor.initialize();

        player.setX(newX);
        player.setY(newY);
        player.setMap(room.getMap());

        room.getMap().getTile(player.getX(), player.getY()).setEntity(player);
        updateCameraPosition();
    }

    private void returnToPreviousRoom() {
        if (!roomStack.isEmpty()) {
            RoomPosition previousRoom = roomStack.pop();

            this.nextRoom = previousRoom.getRoom();
            this.newX = previousRoom.getX();
            this.newY = previousRoom.getY();

            GameApp app = GameContext.getInstance().getGame();
            app.setScreen(new TransitionScreen(app, this));

            entities.clear();
            npcs.clear();

            TileMap oldMap = currentFloor.getMap();
            for (int x = 0; x < oldMap.getWidth(); x++) {
                for (int y = 0; y < oldMap.getHeight(); y++) {
                    oldMap.getTile(x, y).setEntity(null);
                }
            }

            this.currentFloor = previousRoom.getRoom();
            this.currentFloor.initialize();

            entities.addAll(previousRoom.getEntities());

            player.setX(previousRoom.getX());
            player.setY(previousRoom.getY());
            player.setMap(currentFloor.getMap());

            currentFloor.getMap().getTile(player.getX(), player.getY()).setEntity(player);
        }
    }

    private void updateCameraPosition() {
        float playerWorldX = player.getWorldX() * Config.SCALED_TILE_SIZE + Config.SCALED_TILE_SIZE / 2;
        float playerWorldY = player.getWorldY() * Config.SCALED_TILE_SIZE + Config.SCALED_TILE_SIZE / 2;
        camara.updatePostion(playerWorldX, playerWorldY);
    }

    private void addNPC(TileMap map, int x, int y) {
        npcOne = new RenderableEntity(map, x, y, npc1Animations);
        npcTwo = new RenderableEntity(map, x, y, npcTwoAnimations);
        npcThree =  new RenderableEntity(map, x, y, npcMarioAnimations);
        if(currentFloor.getID().equals("OverWorld")){
            npcs.add(npcOne);
        }else if(currentFloor.getID().equals("Floor1")){
            npcs.add(npcThree);
        }
    }

    private void startCinematic() {
        isCinematicActive = true;
    }

    private void updateCinematic(RenderableEntity currentNpc, float delta) {
        currentNpc.update(delta);
        if (currentNpc.getState() == ACTORSTATE.STANDING) {
            if (npcReachedPlayer(npcOne)) {
                startDialogueNpcOne();
                isCinematicActive = false;
            } else {
                moveNpcTowardsPlayer(npcOne);
            }
        }
    }

    private void startDialogueNpcOne() {
        if (!isDialogueActive) {
            isDialogueActive = true;
            currentNpcOneDialogueIndex = 0;
            npcOneDialogueText = npcDialogues.get(currentNpcOneDialogueIndex);
        }
    }

    private void startDialogueBossOne() {
        if (!isDialogueActive) {  // Asegurar que no hay otro diálogo activo
            isDialogueActive = true;
            currentBossOneDialogueIndex = 0;
            bossOneDialogueText = bossOneDialogues.get(currentBossOneDialogueIndex);
        }
    }

    private void startDialogueMario() {
        isDialogueActive = true;
        currentMarioDialogueIndex = 0;
        marioDialogueText = marioDialogues.get(currentMarioDialogueIndex);
    }

    private void handleDialogueInputBossOne(){
        if (isDialogueActive){
            if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){
                continueDialogueBossOne();
            }
        }
    }


    private void handleDialogueInputNpcOne() {
        if (isDialogueActive) {
            if (currentNpcOneDialogueIndex == 5) {
                if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
                    selectedPokemon = 1;
                    GameContext.getInstance().setInitialChoice(1);
                    GameContext.getInstance().getPlayer().getPokemons().add(FactoryClient.getPokemon(PokemonEnum.CHARMANDER,25 ,1,25,0, 45));
                    continueDialogueNpcOne();
                } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
                    selectedPokemon = 2;
                    GameContext.getInstance().setInitialChoice(2);
                    GameContext.getInstance().getPlayer().getPokemons().add(FactoryClient.getPokemon(PokemonEnum.BULBASAUR, 30,1,30, 0, 30));
                    continueDialogueNpcOne();
                } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)) {
                    selectedPokemon = 3;
                    GameContext.getInstance().setInitialChoice(3);
                    GameContext.getInstance().getPlayer().getPokemons().add(FactoryClient.getPokemon(PokemonEnum.SQUIRTALE, 40,1,40, 0, 25));
                    continueDialogueNpcOne();
                }
            } else if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                GameContext.getInstance().setGamelevel(1);
                continueDialogueNpcOne();
            }
        }
    }

    private void continueDialogueNpcOne() {
        currentNpcOneDialogueIndex++;
        if (currentNpcOneDialogueIndex < npcDialogues.size()) {
            npcOneDialogueText = npcDialogues.get(currentNpcOneDialogueIndex);
        } else {
            endDialogue();
        }
    }

    private void continueDialogueBossOne() {
        currentBossOneDialogueIndex++;
        if (currentBossOneDialogueIndex < bossOneDialogues.size()) {
            npcOneDialogueText = bossOneDialogues.get(currentBossOneDialogueIndex);
        } else {
            endDialogue();
        }
    }

    private void continueDialogueMario() {
        currentMarioDialogueIndex++;
        if (currentMarioDialogueIndex < marioDialogues.size()) {
            npcOneDialogueText = marioDialogues.get(currentMarioDialogueIndex);
        } else {
            endDialogue();
        }
    }



    private void endDialogue() {
        isDialogueActive = false;
        shouldNpcMove = true;
    }

    private boolean npcReachedPlayer(RenderableEntity npc) {
        return (npc.getX() == player.getX() && Math.abs(npc.getY() - player.getY()) == 1) ||
                (npc.getY() == player.getY() && Math.abs(npc.getX() - player.getX()) == 1);
    }

    private void moveNpcTowardsPlayer(RenderableEntity npc) {
        int playerX = player.getX();
        int playerY = player.getY();

        if (npc.getX() < playerX) {
            npc.move(DIRECTION.EAST);
        } else if (npc.getX() > playerX) {
            npc.move(DIRECTION.WEST);
        } else if (npcOne.getY() < playerY - 1) {
            npc.move(DIRECTION.NORTH);
        } else if (npcOne.getY() > playerY + 1) {
            npc.move(DIRECTION.SOUTH);
        }
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

    private void drawDialogueBox() {
        if (isDialogueActive) {
            batch.begin();

            float dialogBoxWidth = Gdx.graphics.getWidth() * 0.8f;
            float dialogBoxHeight = Gdx.graphics.getHeight() * 0.2f;
            float dialogBoxX = (Gdx.graphics.getWidth() - dialogBoxWidth) / 2;
            float dialogBoxY = Gdx.graphics.getHeight() * 0.1f;
            batch.draw(dialogBoxTexture, dialogBoxX, dialogBoxY, dialogBoxWidth, dialogBoxHeight);

            GlyphLayout layout = new GlyphLayout(font, npcOneDialogueText);
            float textX = dialogBoxX + (dialogBoxWidth - layout.width) / 2;
            float textY = dialogBoxY + (dialogBoxHeight + layout.height) / 2;
            font.draw(batch, layout, textX, textY);

            batch.end();
        }
    }

    public void checkHighGrass() {
        int[][] highGrassRegions = {
                {10, 19, 13, 18},
                {22, 25, 13, 18},
                {22, 25, 2, 7}
        };

        int playerX = player.getX();
        int playerY = player.getY();

        long currentTime = System.currentTimeMillis();

        // Check if cooldown period is active
        if (currentTime - lastBattleTime < BATTLE_COOLDOWN_MS) {
            return; // Exit if cooldown is not over
        }

        boolean isPlayerInHighGrass = false;
        for (int[] region : highGrassRegions) {
            int x1 = region[2];
            int x2 = region[3];
            int y1 = region[0];
            int y2 = region[1];

            if (playerX >= x1 && playerX <= x2 && playerY >= y1 && playerY <= y2 ) {
                isPlayerInHighGrass = true;
                break;
            }
        }

        if (isPlayerInHighGrass) {
            if (!isInHighGrass) {
                isInHighGrass = true;
                highGrassEntryTime = currentTime; // Record the entry time to high grass
            } else if (currentTime - highGrassEntryTime >= HIGH_GRASS_TIME_THRESHOLD_MS) {
                adventureTrack.stop();
                GameContext.getInstance().setState(new WildPokemonBattleState());
                lastBattleTime = currentTime; // Update the last battle time
                isInHighGrass = false; // Reset the high grass state
            }
        } else {
            isInHighGrass = false; // Reset if player exits high grass
        }
    }



    public int getSelectedPokemon() {
        return selectedPokemon;
    }

    public void setSelectedPokemon(int selectedPokemon) {
        this.selectedPokemon = selectedPokemon;
    }

    public RenderableEntity getPlayer() {
        return player;
    }

    public void setPlayer(RenderableEntity player) {
        this.player = player;
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {}
}
