package Screen;

import Rooms.Floor1;
import Rooms.OverWorld;
import Rooms.Room;
import Util.AnimationSet;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
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

public class GameScreen extends AbstractScreen {
    private Camara camara;
    private PlayerController control;
    private Entity player;
    private Room currentFloor;
    private Texture playerStandingSouth;
    private SpriteBatch batch;

    private GameState gamestate;

    private List<Entity> entities;

    private Music adventureTrack;
    private Music intro;
    private Music easterEgg;
    private Music menu;
    private boolean showSaveConfirmation;
    private float saveConfirmationTime;
    private String saveMessage;

    public GameScreen(Pokemon app) {
        super(app);
        this.gamestate = GameState.TITLESCREEN;

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

        batch = new SpriteBatch();

        TextureAtlas atlas = app.getAssetManager().get("resources/packed/textures.atlas", TextureAtlas.class);

        AnimationSet animations = new AnimationSet(
                new Animation(0.3f / 2f, atlas.findRegions("RedWalking_North"), Animation.PlayMode.LOOP_PINGPONG),
                new Animation(0.3f / 2f, atlas.findRegions("RedWalking_South"), Animation.PlayMode.LOOP_PINGPONG),
                new Animation(0.3f / 2f, atlas.findRegions("RedWalking_East"), Animation.PlayMode.LOOP_PINGPONG),
                new Animation(0.3f / 2f, atlas.findRegions("RedWalking_West"), Animation.PlayMode.LOOP_PINGPONG),
                atlas.findRegion("RedStanding_North"),
                atlas.findRegion("RedStanding_South"),
                atlas.findRegion("RedStanding_East"),
                atlas.findRegion("RedStanding_West")
        );

        // Inicializar la lista de entidades
        entities = new ArrayList<>();

        // Crear e inicializar OverWorld
        OverWorld overWorld = new OverWorld(new TileMap(20, 36, new Texture("resources/Tiles/grass.png")), entities);
        overWorld.initialize();

        // Establecer el cuarto actual como OverWorld
        this.currentFloor = overWorld;

        // Crear el jugador y establecer su controlador
        player = new Entity(currentFloor.getMap(), 10, 1, animations);
        camara = new Camara();
        control = new PlayerController(player);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(control);
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

        if (gamestate == GameState.GAME && player.getX() == 10 && player.getY() == 30 && Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            changeRoom();
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
                updateGameLogic(delta);
                clearScreen();
                drawGameWorld();
                drawEntities();
                drawPlayer();
                break;
            case CARGAR:
                // Implementar lógica de carga aquí
                break;
            case PAUSE:
                adventureTrack.pause();
                drawPauseMenu();
                break;
            case BAG:
                drawBag();
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

    protected void changeRoom() {
        // Limpiar entidades del cuarto actual
        entities.clear();

        // Limpiar entidades del mapa actual
        TileMap oldMap = currentFloor.getMap();
        for (int x = 0; x < oldMap.getWidth(); x++) {
            for (int y = 0; y < oldMap.getHeight(); y++) {
                oldMap.getTile(x, y).setEntity(null);
            }
        }

        // Crear e inicializar NewRoom
        Floor1 floor1 = new Floor1(entities);
        floor1.initialize();

        // Establecer el cuarto actual como NewRoom
        this.currentFloor = floor1;

        // Reubicar al jugador en la nueva habitación
        player.setX(7); // Nueva posición X del jugador
        player.setY(0); // Nueva posición Y del jugador
        player.setMap(floor1.getMap()); // Establecer el nuevo mapa del jugador

        // Añadir el jugador al nuevo mapa
        floor1.getMap().getTile(player.getX(), player.getY()).setEntity(player);
    }

    protected void updateGameLogic(float delta) {
        control.update(delta);
        player.update(delta);
        updateCamera();
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    public void drawTitleScreen() {
        batch.begin();
        batch.draw(new Texture("resources/TitleScreen/background.png"), 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        BitmapFont font = new BitmapFont();
        font.getData().setScale(1.6f);
        String comenzar = "Press ENTER to start a new game";
        GlyphLayout layout = new GlyphLayout(font, comenzar);
        float x = (Gdx.graphics.getWidth() - layout.width) / 2;
        float y = (Gdx.graphics.getHeight() + layout.height - 200) / 2;
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
        if (Gdx.input.isKeyJustPressed(Input.Keys.L)) {
            gamestate = GameState.CARGAR;
        }
    }

    protected void drawGameWorld() {
        batch.begin();
        float worldStartX = Gdx.graphics.getWidth() / 2 - camara.getCamaraX();
        float worldStartY = Gdx.graphics.getHeight() / 2 - camara.getCamaraY();
        TileMap map = currentFloor.getMap(); // Usar el mapa del cuarto actual
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

    protected void drawEntities() {
        batch.begin();
        for (Entity entity : entities) {
            float worldStartX = Gdx.graphics.getWidth() / 2 - camara.getCamaraX();
            float worldStartY = Gdx.graphics.getHeight() / 2 - camara.getCamaraY();
            batch.draw(entity.getSprite(),
                    worldStartX + entity.getWorldX() * Settings.SCALED_TILE_SIZE,
                    worldStartY + entity.getWorldY() * Settings.SCALED_TILE_SIZE,
                    Settings.SCALED_TILE_SIZE,
                    Settings.SCALED_TILE_SIZE * 1.5f);
        }
        batch.end();
    }

    protected void drawPlayer() {
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

    protected void updateCamera() {
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
                "The legendary Pokémon Owluca has taken control of the \n entire campus and has hypnotized all the Pokémon and professors! \n" +
                "It is our duty to stop him and restore peace to UCA. \n" +
                "Only one student will be able to do it… \n";
        String[] storyLines = story.split("\n");
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

        String[] options = { "Bag", "Save Game", "Quit Game", "Return to Title" };
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
            gamestate = GameState.BAG;
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
            handleSaveGame();
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)) {
            Gdx.app.exit();
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_4)) {
            gamestate = GameState.TITLESCREEN;
        }
    }

    public void handleSaveGame() {
        saveGame();
        saveMessage = "Game saved successfully.";
        showSaveConfirmation = true;
        saveConfirmationTime = 3.0f;
    }

    public void saveGame() {
        // Implementar lógica de guardado aquí
    }

    private void drawSaveConfirmationMessage() {
        batch.begin();

        TextureRegion background = new TextureRegion(new Texture(Gdx.files.internal("resources/Dialog/Message.png")));
        float bgWidth = Gdx.graphics.getWidth() * 0.8f;
        float bgHeight = 60;
        float bgX = (Gdx.graphics.getWidth() - bgWidth) / 2;
        float bgY = Gdx.graphics.getHeight() / 2 - bgHeight / 2;
        batch.draw(background, bgX, bgY, bgWidth, bgHeight);

        BitmapFont font = new BitmapFont();
        font.getData().setScale(1.0f);
        font.setColor(0, 0, 0, 1);
        GlyphLayout layout = new GlyphLayout(font, "Game saved successfully!");
        float textX = (Gdx.graphics.getWidth() - layout.width) / 2;
        float textY = Gdx.graphics.getHeight() / 2 + layout.height / 2;
        font.draw(batch, layout, textX, textY);

        batch.end();
    }

    public void drawBag() {
        batch.begin();

        // Dibujar la imagen del bolso (bag)
        Texture bagImage = new Texture("resources/bag.png"); // Reemplaza con la ruta de tu imagen
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
    }
}
