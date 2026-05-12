package GameState.BattleStates;

import GameState.*;
import GameState.GameContext;
import Items.Pokeball;
import Main.GameApp;
import Screens.WildPokemonBattleScreen;

/**
 * Estado Concreto de Batalla, y de juego,se sobreescriben métodos creados en Battle
 * State para concretizarlos a la batalla de un pokemon Salvaje.
 */
public class WildPokemonBattleState extends BattleState {
    /**
     * Inicializa un pokemon salvaje aleatorio.
     */
    @Override
    protected void initializeOpponentPokemon() {
        opponentPokemon = WildPokemonSelector.getRandomWildPokemon();
    }

    /**
     * Despliega los datos del pokemon salvaje al inicio de la batalla.
     */
    @Override
    protected void displayBattleStartMessage() {
        opponentPokemon = WildPokemonSelector.getRandomWildPokemon();
        GameApp gameApp = GameContext.getInstance().getGame();
        gameApp.setScreen(new WildPokemonBattleScreen(gameApp,opponentPokemon));
    }

    /**
     * Ejecuta mensaje de derrota.
     */
    @Override
    public void handleVictory() {
        System.out.println(opponentPokemon.getName() + " fainted! You win!");
        GameContext.getInstance().getPlayer().setMoney(GameContext.getInstance().getPlayer().getMoney() + 10);
        playerPokemon.gainXp(opponentPokemon.getLevel() * 2);
        playerPokemon.checkLevelUp();
        GameContext.getInstance().setState(new ExploringState());
    }

    /**
     * Ejecuta Mensaje de derrota
     */
    @Override
    protected void handleDefeat() {
        System.out.println(playerPokemon.getName() + " fainted! You lose!");
        GameContext.getInstance().setState(new ExploringState());
    }

    /**
     * @return Retorna verdadero si es un pokemon salvaje.
     */
    @Override
    protected boolean canRun() {
        return true;
    }
}
