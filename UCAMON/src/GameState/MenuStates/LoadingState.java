package GameState.MenuStates;


import GameState.GameState;
import GameState.GameContext;
import GameState.ExploringState;
import GameState.InitialGameStates.InitialMenuState;

/**
 * Clase encargada de cargar la partida.
 */
public class LoadingState implements GameState {
    /**
     * \Carga la partida.
     */
    @Override
    public void handle() {
        try{
            GameContext.getInstance().load.load();
            GameContext.getInstance().setState(new ExploringState());
        }catch(Exception e){
            GameContext.getInstance().setState(new InitialMenuState());
        }
    }
}
