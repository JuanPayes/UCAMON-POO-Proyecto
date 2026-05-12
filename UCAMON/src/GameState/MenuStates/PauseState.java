package GameState.MenuStates;

import GameState.GameState;
import GameState.ExploringState;
import GameState.GameContext;
import GameState.InventoryStates.InventoryState;
import Main.GameApp;
import Screens.PauseScreen;

import java.util.Scanner;

/**
 * Clase para pausar el juego
 */
public class PauseState implements GameState{
    /**
     * Detiene el juego y muestra opciones.
     */
    @Override
    public void handle() {
        GameApp gameApp = GameContext.getInstance().getGame();
        gameApp.setScreen(new PauseScreen(gameApp));
    }
}
