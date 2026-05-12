package GameState;

import Entities.Player;
import GameState.InitialGameStates.InitialMenuState;
import Main.GameApp;
import SaveAndLoad.Load;
import SaveAndLoad.Save;



/**
 * GameContext funciona como la clase Contexto, que hace referencia al estado inicial, para dar continuidad
 * al hilo del juego.El contexto se comunica con los States concretos via la interface GameState.
 */
public class GameContext {
    /**
     * Instancia única consecuencia del patrón Singelton.
     */
    private static GameContext instance;
    /**
     * Estado actual del contexto del juego.
     */
    private GameState state;

    /**
     * Nivel actual del juego.
     */
    protected int Gamelevel;

    /**
     * Opción de pokemon inicial.
     */
    private int initialChoice;

    /**
     * Jugador de la partida.
     */
    protected Player player;


    /**
     * Apartado de guardado.
     */
    public Save save;
    /**
     * Apartado de cargar partida.
     */
    public Load load;

    /**
     * Aplicación del juego
     */
    private GameApp game;
    /**
     * @return Retorna la única instancia de la partida o si no existe la crea.
     */
    public static GameContext getInstance(){
        if (instance == null){
            instance = new GameContext();
        }
        return instance;
    }

    public GameContext() {
        this.state = new InitialMenuState();
        this.player = new Player();
        this.save = new Save();
        this.load = new Load();
    }

    public void setState(GameState state) {
        this.state = state;
        if (state != null) {
            state.handle();
        }
    }

    public GameApp getGame() {
        return game;
    }

    public void setGame(GameApp game) {
        this.game = game;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getGamelevel() {
        return  Gamelevel;
    }

    public void setGamelevel(int gamelevel) {
        Gamelevel = gamelevel;
    }

    public void checkLevel(){
        if(Gamelevel< 2 ){
            if(player.getPokemons().size() != 1){
                Gamelevel = 2;
            }
        }

    }



    public int getInitialChoice() {
        return initialChoice;
    }

    public void setInitialChoice(int initialChoice) {
        this.initialChoice = initialChoice;
    }

    /**
     * Inicia todo el juego.
     */
    public void start() {
        while (state != null) {
            state.handle();
        }
    }

}


