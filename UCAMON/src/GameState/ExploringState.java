package GameState;

import Entities.Entity;
import GameState.BattleStates.BattleTrainerState;
import GameState.BattleStates.WildPokemonBattleState;
import GameState.MenuStates.PauseState;
import Main.GameApp;
import Screens.OverworldScreen;

import java.util.Scanner;

/**
 * Clase encarga de la exploración libre en el mapa.
 */
public class ExploringState implements GameState {

    private OverworldScreen overworldScreen;

    private static ExploringState overworldState;

    public ExploringState() {
    }

    public static ExploringState getOverworldState(){
        if (overworldState == null) {
            overworldState = new ExploringState();
        }
        return overworldState;
    }
    /**
     * Muestra las distintas opciones que tiene el jugador
     * en el modo exploratorio.
     */
    @Override
    public void handle() {
        GameApp gameApp = GameContext.getInstance().getGame();
        showOverworldScreen(gameApp);
    }


    /**
     * Muestra la pantalla del overworld que es unica.
     */
    public void showOverworldScreen(GameApp gameApp) {
        if (overworldScreen == null) {
            overworldScreen = new OverworldScreen(gameApp);
        }
        gameApp.setScreen(overworldScreen);
    }

    public OverworldScreen getOverworldScreen() {
        return overworldScreen;
    }

    public void setOverworldScreen(OverworldScreen overworldScreen) {
        this.overworldScreen = overworldScreen;
    }
}
