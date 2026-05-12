package GameState.MenuStates;

import GameState.GameState;
import GameState.GameContext;
import GameState.InitialGameStates.InitialMenuState;

/**
 * Clase para salir del modo exploratorio
 */
public class QuittingState implements GameState {
    /**
     * Regresa al menu inicial.
     */
    @Override
    public void handle() {
        System.out.println("Thanks for playing!");
        GameContext.getInstance().setState(new InitialMenuState());
    }
}
