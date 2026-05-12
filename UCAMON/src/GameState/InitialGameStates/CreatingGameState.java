package GameState.InitialGameStates;
import GameState.*;
import Entities.Player;
import GameState.GameState;
import GameState.InitialGameStates.CinematicState;

import java.util.Scanner;

/**
 * Clase encarga de crear una partida nueva.
 */
public class CreatingGameState implements GameState {
    /**
     * Ejecuta la creación de una partida.
     */
    @Override
    public void handle() {
        GameContext.getInstance().getPlayer().reset();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter player name:");
        String playerName = scanner.nextLine();
        Player player = GameContext.getInstance().getPlayer();
        player.setName(playerName);
        GameContext.getInstance().setPlayer(player);
        System.out.println("Welcome, " + playerName + "! Starting the game...");
        GameContext.getInstance().setState(new CinematicState());
    }
}
