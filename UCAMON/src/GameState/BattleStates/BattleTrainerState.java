package GameState.BattleStates;

import Entities.Entity;
import GameState.GameContext;
import Main.GameApp;
import PokemonFactory.FactoryClient;
import PokemonFactory.Pokemon.Pokemon;
import PokemonFactory.PokemonEnum;
import GameState.*;
import Screens.TrainerBattleScreen;

import java.util.ArrayList;
import java.util.Random;

/**
 * Estado Concreto de Batalla, y de juego,se sobreescriben métodos creados en Battle
 *  * State para concretizarlos a la batalla con un entrenador.
 */
public class BattleTrainerState extends BattleState {

    private int npc;


    public BattleTrainerState(int npc) {
        this.npc = npc;
    }

    /**
     *  Inicializa el pokemon del entrenador enemigo, como el primero en su
     *  ArrayList de Pokemons.
     */
    @Override
    protected void initializeOpponentPokemon() {
        //
    }

    /**
     * Despliega el mensaje inicial de batalla.
     */
    @Override
    protected void displayBattleStartMessage() {
        if (npc == 1){
            Entity opponentTrainer = new Entity("Merlina",BattleTrainerState.createTrainerPokemons(1),0,0);
            GameApp gameApp = GameContext.getInstance().getGame();
            gameApp.setScreen(new TrainerBattleScreen(gameApp,opponentTrainer));
        }else if (npc == 2){
            Entity opponentTrainer = new Entity("Merlina",BattleTrainerState.createTrainerPokemons(2),0,0);
            GameApp gameApp = GameContext.getInstance().getGame();
            gameApp.setScreen(new TrainerBattleScreen(gameApp,opponentTrainer));
        }

    }

    /**
     * Ejecuta el mensaje de victoria.
     */
    @Override
    protected void handleVictory() {
        GameContext.getInstance().setGamelevel(GameContext.getInstance().getGamelevel() + 1);
        System.out.println(opponentPokemon.getName() + " fainted! You win!");
        GameContext.getInstance().getPlayer().setMoney(GameContext.getInstance().getPlayer().getMoney() + 20);
        playerPokemon.gainXp(opponentPokemon.getLevel() * 3);
        playerPokemon.checkLevelUp();
        GameContext.getInstance().setState(new ExploringState());
    }

    /**
     * Ejecuta el mensaje de derrota.
     */
    @Override
    protected void handleDefeat() {
        System.out.println(playerPokemon.getName() + " fainted! You lose!");
        GameContext.getInstance().setState(new ExploringState());
    }

    /**
     * @return Retorna falso, si es una battala con un entrenador.
     */
    @Override
    protected boolean canRun() {
        return false;
    }

    /**
     * Ejecuta el turno del oponente.
     */
    @Override
    protected void opponentTurn() {
        if (opponentPokemon.isFainted()) {

        } else {
            Random random = new Random();
            opponentPokemon.getAttacks().get(random.nextInt(opponentPokemon.getAttacks().size())).use(playerPokemon, opponentPokemon);
            displayBattleStatus();
        }
    }

    /**
     * Ejecuta el turno del jugador.
     */
    @Override
    protected void playerTurn() {
        super.playerTurn();

    }





    /**
     * @param trainerType Entero que especifica que entrenador se necesita.
     * @return Retorna el ArrayList de Pokemons de el entrenado proeviamente especificado.
     */
    public static ArrayList<Pokemon> createTrainerPokemons(int trainerType) {
        ArrayList<Pokemon> pokemons = new ArrayList<>();
        if (trainerType == 1) {
            switch (GameContext.getInstance().getInitialChoice()) {
                case 1:
                    pokemons.add(FactoryClient.getPokemon(PokemonEnum.BULBASAUR, 40, 10, 40, 30, 30));
                    pokemons.add(FactoryClient.getPokemon(PokemonEnum.SQUIRTALE, 40, 10, 40, 30, 25));
                    break;
                case 2:
                    pokemons.add(FactoryClient.getPokemon(PokemonEnum.SQUIRTALE, 40, 10, 40, 30, 25));
                    pokemons.add(FactoryClient.getPokemon(PokemonEnum.CHARMANDER, 25, 10, 25, 0, 45));
                    break;
                case 3:
                    pokemons.add(FactoryClient.getPokemon(PokemonEnum.CHARMANDER, 25, 10, 25, 0, 45));
                    pokemons.add(FactoryClient.getPokemon(PokemonEnum.BULBASAUR, 40, 10, 40, 30, 30));
                    break;
                default:
                    System.out.println("Invalid initial choice.");
                    break;
            }
        } else {
            pokemons.add(FactoryClient.getPokemon(PokemonEnum.OWLUCA, 50, 15, 50, 30, 20));
            pokemons.add(FactoryClient.getPokemon(PokemonEnum.SNORLAX, 35, 10, 35, 30, 5));
        }
        return pokemons;
    }
}
