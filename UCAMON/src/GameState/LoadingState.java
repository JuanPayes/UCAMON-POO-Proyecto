package GameState;
import GameState.InitialGameStates.InitialMenuState;
public class LoadingState implements GameState {
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