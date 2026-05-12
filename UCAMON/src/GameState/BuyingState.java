package GameState;
import Items.*;
import Main.GameApp;
import Screens.StoreScreen;

import java.util.ArrayList;

import java.util.Scanner;

/**
 * Clase encargada del estado de compra durante el juego.
 */
public class BuyingState implements GameState {

    public BuyingState(){

    }


    /**
     * Ejecuta la opción de comprar items para el usario.
     */
    @Override
    public void handle() {
        GameApp gameApp = new GameApp();
        GameContext.getInstance().getGame().setScreen(new StoreScreen(gameApp));
    }

}