package GameState.InventoryStates;

import GameState.GameContext;
import GameState.GameState;
import Main.GameApp;
import Screens.BagScreen;
import Screens.ViewPokemonScreen;

public class ViewPokemonState implements GameState {


    @Override
    public void handle() {
        GameApp gameApp = GameContext.getInstance().getGame();
        gameApp.setScreen(new ViewPokemonScreen(gameApp));
    }
}
