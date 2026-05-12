package GameState.InventoryStates;
import java.util.Scanner;
import GameState.MenuStates.PauseState;
import GameState.GameState;
import Items.*;
import Main.GameApp;
import PokemonFactory.Pokemon.Pokemon;
import GameState.GameContext;
import Screens.BagScreen;

/**
 * Clase encargada de mostrar el inventario
 */
public class InventoryState implements GameState{
    /**
     * Mostrar items del usuario...
     */
    @Override
    public void handle() {
        GameApp gameApp = GameContext.getInstance().getGame();
        gameApp.setScreen(new BagScreen(gameApp));
    }

}