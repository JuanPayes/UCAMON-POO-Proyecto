package GameState.InitialGameStates;

import GameState.GameState;
import GameState.GameContext;
import Main.GameApp;
import Screens.InitialScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

/**
 * Clase encarga del menu inicial
 */
public class InitialMenuState implements GameState {
    /**
     * Muestra el menu al inicio del juego
     */
    @Override
    public void handle() {
        GameApp gameApp = GameContext.getInstance().getGame();
        gameApp.setScreen(new InitialScreen(gameApp));


    }
    }
