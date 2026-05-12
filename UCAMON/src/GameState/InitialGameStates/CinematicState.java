package GameState.InitialGameStates;

import GameState.GameState;
import GameState.GameContext;
import Main.GameApp;
import Screens.CinematicScreen;

public class CinematicState implements GameState {


    @Override
    public void handle() {
        GameContext.getInstance().getPlayer().setMoney(100);

        GameApp app = GameContext.getInstance().getGame();
        GameContext.getInstance().getGame().setScreen(new CinematicScreen(app));
    }
}
