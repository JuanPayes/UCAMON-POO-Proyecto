package GameState;

import Main.GameApp;
import PokemonFactory.Pokemon.Pokemon;
import Screens.PokemonCenterScreen;

import java.util.Scanner;

/**
 * Clase para el Centro Pokemon
 */
public class PokemonCenterState implements GameState{
    /**
     * Muestra menu del centro pokemon
     */
    @Override
    public void handle() {
        GameApp gameApp = GameContext.getInstance().getGame();
        gameApp.setScreen(new PokemonCenterScreen(gameApp));

    }
}
