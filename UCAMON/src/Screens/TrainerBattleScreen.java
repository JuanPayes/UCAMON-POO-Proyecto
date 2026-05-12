package Screens;

import Entities.Entity;
import Entities.Player;
import GameState.ExploringState;
import GameState.GameContext;
import Items.Buyable;
import Main.GameApp;
import PokemonFactory.Pokemon.Pokemon;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.backends.lwjgl.audio.Mp3;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Random;

public class TrainerBattleScreen extends AbstractScreen {
    private Music firstBossTheme;
    private SpriteBatch batch;
    private Player player;
    private Entity opponentTrainer;
    private Pokemon opponentPokemon;
    private Pokemon playerPokemon;
    private Texture dialogueBoxTexture, pokemonInformationTexture;
    private BitmapFont font;
    private String dialogueText;
    private enum GameState { INITIAL, PLAYER_OPTIONS, ATTACK_OPTIONS, BAG_OPTIONS, RUNNING, SWITCHING, OPPONENT_TURN,WAITING_FOR_PLAYER_CONFIRMATION,WAITING_FOR_OPPONENT_CONFIRMATION,CAPTURED_POKEMON,WIN,LOSS,USE_POKEBALL,USE_POTION,SWITCHOPPONENTPOKEMON }
    private GameState currentState;
    private boolean opponentSwitched = false;
    private boolean playerSwitched = false;

    public TrainerBattleScreen(GameApp app, Entity opponentTrainer) {
        super(app);
        firstBossTheme = Gdx.audio.newMusic(Gdx.files.internal("resources/Music/firstBossTheme.mp3"));
        firstBossTheme.setLooping(true);
        firstBossTheme.setVolume(0.1f);
        batch = new SpriteBatch();
        this.opponentTrainer = opponentTrainer;
        this.player = GameContext.getInstance().getPlayer();
        this.playerPokemon = player.getPokemons().get(0);
        this.opponentPokemon = opponentTrainer.getPokemons().get(0);
        this.dialogueBoxTexture = new Texture(Gdx.files.internal("resources/Dialog/textBox.png"));
        this.pokemonInformationTexture = new Texture(Gdx.files.internal("resources/Battle/MenuBTL.png"));
        this.font = new BitmapFont();
        this.dialogueText = "";
        this.currentState = GameState.INITIAL;
    }

    public void setDialogue(String dialogue) {
        this.dialogueText = dialogue;
    }

    public void drawPokemons() {
        Texture playerPokemonTexture = playerPokemon.getSprite();
        Texture enemyPokemonTexture = opponentPokemon.getSpriteOP();
        batch.begin();
        batch.draw(enemyPokemonTexture, 380.0f, 140.0f, 200, 200);
        batch.draw(playerPokemonTexture, 20.0f, 140.0f, 200, 200);
        batch.end();
    }

    public void drawDialogueBox() {
        float boxWidth = Gdx.graphics.getWidth() - 100;
        float boxHeight = 100;
        float boxX = (Gdx.graphics.getWidth() - boxWidth) / 2;
        float boxY = 50;

        batch.begin();
        batch.draw(dialogueBoxTexture, boxX, boxY, boxWidth, boxHeight);
        font.setColor(Color.BLACK);
        font.draw(batch, dialogueText, boxX + 20, boxY + boxHeight - 20);
        batch.end();
    }

    public void drawInfoEnemyBox() {
        float boxWidth = Gdx.graphics.getWidth() - 400;

        batch.begin();
        batch.draw(pokemonInformationTexture, 340, 330, boxWidth, 40);
        batch.end();
    }

    public void drawInfoPlayerBox() {
        float boxWidth = Gdx.graphics.getWidth() - 400;

        batch.begin();
        batch.draw(pokemonInformationTexture, 50, 330, boxWidth, 40);
        batch.end();
        writePokemonHealth(playerPokemon, 150, 347);
        writePokemonLevel(playerPokemon, 229, 362);
        writePokemonName(playerPokemon,80,360);
        writePokemonHealth(opponentPokemon, 443, 347);
        writePokemonLevel(opponentPokemon, 523, 362);
        writePokemonName(opponentPokemon,370,360);
    }

    public void writePokemonHealth(Pokemon pokemon, float x, float y) {
        batch.begin();
        font.setColor(Color.BLACK);
        String info = pokemon.getHp() + "/" + pokemon.getHpMax();
        font.draw(batch, info, x, y);
        batch.end();
    }

    public void writePokemonName(Pokemon pokemon, float x, float y) {
        batch.begin();
        font.setColor(Color.BLACK);
        String info = pokemon.getName();
        font.draw(batch, info, x, y);
        batch.end();
    }

    public void writePokemonLevel(Pokemon pokemon, float x, float y) {
        batch.begin();
        font.setColor(Color.BLACK);
        String info = pokemon.getLevel() + "";
        font.draw(batch, info, x, y);
        batch.end();
    }
    public void updateStats() {
        writePokemonHealth(GameContext.getInstance().getPlayer().getPokemons().get(0), 150, 347);
        writePokemonLevel(GameContext.getInstance().getPlayer().getPokemons().get(0), 229, 362);

        writePokemonHealth(opponentPokemon, 443, 347);
        writePokemonLevel(opponentPokemon, 523, 362);
    }
    public void handleDialogue() {
        if (playerPokemon.isFainted()) {
            currentState = GameState.LOSS;
        }
        switch (currentState) {
            case INITIAL:
                setDialogue("The Pokemon Trainer challenged you with " + opponentPokemon.getName() + "!");
                if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                    if(opponentPokemon.getSpeed() > playerPokemon.getSpeed()){
                        setDialogue("Its " + opponentPokemon.getName() + " turn!");
                        currentState = GameState.WAITING_FOR_PLAYER_CONFIRMATION;
                    }else{
                        currentState = GameState.PLAYER_OPTIONS;
                    }
                }
                break;

            case PLAYER_OPTIONS:
                setDialogue("1. Attack\n2. Bag\n3. Run\n4. Switch Pokemon");
                if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
                    currentState = GameState.ATTACK_OPTIONS;
                } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
                    currentState = GameState.BAG_OPTIONS;
                    setDialogue("Using Bag...");
                } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)) {
                    setDialogue("You ran away safely!");
                    currentState = GameState.RUNNING;
                } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_4)) {
                    currentState = GameState.SWITCHING;
                }
                break;

            case ATTACK_OPTIONS:
                StringBuilder options = new StringBuilder();
                for (int i = 0; i < playerPokemon.getAttacks().size(); i++) {
                    options.append((i + 1)).append(": ").append(playerPokemon.getAttacks().get(i).getName()).append("\n");
                }
                options.append("B: Back");
                setDialogue(options.toString());
                if (Gdx.input.isKeyJustPressed(Input.Keys.B)) {
                    currentState = GameState.PLAYER_OPTIONS;
                } else {
                    for (int i = 0; i < player.getPokemons().get(0).getAttacks().size(); i++) {
                        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1 + i)) {

                            if(playerPokemon.getAttacks().get(i).use(opponentPokemon, playerPokemon)){
                                setDialogue(playerPokemon.getName()+ " used " + player.getPokemons().get(0).getAttacks().get(i).getName() + "!");
                            }else{
                                setDialogue(playerPokemon.getName()+ " missed "  + "!");
                            }
                            updateStats();
                            if(opponentPokemon.isFainted()){
                                currentState = GameState.SWITCHOPPONENTPOKEMON;
                            }else{
                                currentState = GameState.WAITING_FOR_PLAYER_CONFIRMATION;
                            }
                        }
                    }
                }
                break;

            case WAITING_FOR_PLAYER_CONFIRMATION:
                if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                    currentState = GameState.OPPONENT_TURN;
                }
                break;
            case WAITING_FOR_OPPONENT_CONFIRMATION:
                if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                    currentState = GameState.PLAYER_OPTIONS;
                }
                break;

            case OPPONENT_TURN:
                Random random = new Random();
                int attack = random.nextInt(opponentPokemon.getAttacks().size());

                if(opponentPokemon.getAttacks().get(attack).use(playerPokemon, opponentPokemon)){
                    setDialogue(opponentPokemon.getName() + " has used " + opponentPokemon.getAttacks().get(attack).getName());
                }else{
                    setDialogue(opponentPokemon.getName() + " missed!");
                }
                updateStats();

                if(playerPokemon.isFainted()){
                    if(!playerSwitched){
                        playerPokemon = player.getPokemons().get(1); // Switch to the second Pokémon
                        setDialogue("You switched to " + playerPokemon.getName() + "!");
                        drawPokemons();
                        updateStats();
                        currentState = GameState.WAITING_FOR_OPPONENT_CONFIRMATION;
                    }else{
                        currentState = GameState.LOSS;
                    }

                }else{
                    currentState = GameState.WAITING_FOR_OPPONENT_CONFIRMATION;
                }

                break;


            case BAG_OPTIONS:
                setDialogue("1. Pokeball\n2. Potion\nB: Return");
                if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
                    currentState = GameState.USE_POKEBALL;
                } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
                    currentState = GameState.USE_POTION;
                } else if (Gdx.input.isKeyJustPressed(Input.Keys.B)) {
                    currentState = GameState.PLAYER_OPTIONS;
                }
                break;

            case RUNNING:
                firstBossTheme.stop();
                GameContext.getInstance().setState(ExploringState.getOverworldState());
                break;
            case USE_POKEBALL:
                if (Buyable.hasItem("Pokeball")) {
                    try {
                        if(Buyable.getItem("Pokeball").use(opponentPokemon, playerPokemon)){
                             currentState = GameState.CAPTURED_POKEMON;
                        }else{
                            setDialogue("Pokeball missed");
                            if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) currentState = GameState.OPPONENT_TURN;
                        }

                    } catch (Exception e) {
                        System.out.println("Something went wrong");
                    }
                }
                if(Buyable.hasItem("Pokeball")){
                    setDialogue("You don't have Pokeballs...(Press Enter)");
                    if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) currentState = GameState.PLAYER_OPTIONS;
                }
                break;
            case USE_POTION:
                if (Buyable.hasItem("Potion")) {
                    try {
                        if(Buyable.getItem("Potion").use(opponentPokemon, playerPokemon)){
                            setDialogue(playerPokemon + " has used a potion.");
                            currentState = GameState.WAITING_FOR_PLAYER_CONFIRMATION;
                        }

                    } catch (Exception e) {
                        System.out.println("Something went wrong");
                    }
                }
                if(!Buyable.hasItem("Potion")){
                    setDialogue("You don't have Pokeballs...(Press Enter)");
                    if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) currentState = GameState.PLAYER_OPTIONS;
                }
                break;


            case CAPTURED_POKEMON:
                setDialogue("You have caught " + opponentPokemon.getName());
                if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                    firstBossTheme.stop();
                    GameContext.getInstance().setState(ExploringState.getOverworldState());
                    break;
                }
                break;
            case WIN:
                GameContext.getInstance().setGamelevel(GameContext.getInstance().getGamelevel() + 1);
                setDialogue("You won!!");
                drawDialogueBox();
                if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                    firstBossTheme.stop();
                    GameContext.getInstance().setState(ExploringState.getOverworldState());
                }
                break;
            case SWITCHING:
                if (player.getPokemons().size() > 1) {
                    playerPokemon = player.getPokemons().get(1); // Switch to the second Pokémon
                    setDialogue("You switched to " + playerPokemon.getName() + "!");
                    drawPokemons();
                    updateStats();
                    currentState = GameState.WAITING_FOR_OPPONENT_CONFIRMATION;
                } else {

                    setDialogue("You don't have any other Pokémon to switch to! (Press Enter)");
                    currentState = GameState.WAITING_FOR_PLAYER_CONFIRMATION;
                }
                break;
            case SWITCHOPPONENTPOKEMON:
                if(!opponentSwitched){
                    opponentSwitched = true;
                    opponentPokemon = opponentTrainer.getPokemons().get(1); // Switch to the second Pokémon
                    setDialogue( opponentTrainer.getName() + " switched to " + playerPokemon.getName() + "!");
                    drawPokemons();
                    updateStats();
                    currentState = GameState.WAITING_FOR_OPPONENT_CONFIRMATION;
                }else{
                    currentState = GameState.WIN;
                }


                break;
            case LOSS:
                ExploringState.getOverworldState().getOverworldScreen().ableToBattle = false;
                setDialogue("You lost!!");
                drawDialogueBox();
                if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                    firstBossTheme.stop();
                    GameContext.getInstance().setState(ExploringState.getOverworldState());

                }
                break;
        }
        drawDialogueBox();
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        batch.begin();
        firstBossTheme.play();
        Texture background = new Texture(Gdx.files.internal("resources/Battle/BattleBackgroundLib.png"));
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();
        drawPokemons();
        drawInfoEnemyBox();
        drawInfoPlayerBox();
        handleDialogue();
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
        dialogueBoxTexture.dispose();
        pokemonInformationTexture.dispose();
        font.dispose();
    }
}
