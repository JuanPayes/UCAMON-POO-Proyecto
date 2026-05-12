package GameState.BattleStates;

import java.util.Random;

import GameState.ExploringState;
import Items.Buyable;
import PokemonFactory.Pokemon.Pokemon;
import GameState.GameState;
import java.util.Scanner;
import GameState.GameContext;

/**
 * Funge como la clase Abstracta del patrón de diseño template,además de ser la clase padre de otros
 * States concretos. En este caso, el método Template es handle, dado que no se sobrescribe en
 * las clases concretas.
 *
 */
public abstract class BattleState implements GameState{
    protected Pokemon playerPokemon;
    protected Pokemon opponentPokemon;


    /**
     * Ejecuta todo lo relacionado a una balla con un pokemon.
     */
    @Override
    public void handle() {
        //initializePlayerPokemon();
        //initializeOpponentPokemon();
        displayBattleStartMessage();
        /*
        if (playerPokemon.isFainted()) {
            System.out.println("Heal your pokemon!!!");
            GameContext.getInstance().setState(new ExploringState());
            return;
        }
        initializeOpponentPokemon();
        displayBattleStartMessage();
        boolean firstPlayer = playerPokemon.getSpeed() >= opponentPokemon.getSpeed();
        while (!playerPokemon.isFainted() && !opponentPokemon.isFainted()) {
            if (firstPlayer) {
                playerTurn();
                if (opponentPokemon.isFainted()) {
                    handleVictory();
                    return;
                }
            } else {
                opponentTurn();
                if (playerPokemon.isFainted()) {
                    if (canSwitchPokemon()) {
                        switchPokemonAutomatically();
                    } else {
                        handleDefeat();
                        return;
                    }
                }
            }
            firstPlayer = !firstPlayer;
        }
         */


    }

    /**
     * Inicializa el pokemon del jugador.
     */
    protected void initializePlayerPokemon() {

        //playerPokemon = GameContext.getInstance().getPlayer().getPokemons().get(0);
    }

    protected abstract void initializeOpponentPokemon();

    protected abstract void displayBattleStartMessage();

    protected abstract void handleVictory();

    protected abstract void handleDefeat();

    protected abstract boolean canRun();

    /**
     * Ejecuta el turno del jugador.
     */
    protected void playerTurn() {
        System.out.println("Your turn.");
        System.out.println("1. Attack \n2. Bag \n3. Run \n4. Switch Pokemon");
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        scanner.nextLine();
        switch (choice) {
            case 1:
                for (int i = 0; i < playerPokemon.getAttacks().size(); i++) {
                    System.out.println((i + 1) + ": " + playerPokemon.getAttacks().get(i).getName());
                }
                int option = scanner.nextInt() - 1;
                if (option < 0 || option >= playerPokemon.getAttacks().size()) {
                    System.out.println("Invalid choice! Try again.");
                    return;
                }
                playerPokemon.getAttacks().get(option).use(opponentPokemon, playerPokemon);
                break;
            case 2:
                useBag();
                break;
            case 3:
                if (canRun()) {
                    System.out.println("You ran");
                    GameContext.getInstance().setState(new ExploringState());
                    return;
                } else {
                    System.out.println("You can't run from a trainer battle!");
                }
                break;
            case 4:
                if (canSwitchPokemon()) {
                    switchPokemonManually();
                } else {
                    System.out.println("You can't switch Pokemon!");
                }
                break;
            default:
                System.out.println("Invalid Option");
                break;
        }
        displayBattleStatus();
    }


    /**
     * Provee acceso al jugador a usar los items durante la batalla.
     */
    private void useBag() {
        System.out.println("1. Use Pokeball\n2. Use potion");
        Scanner scanner = new Scanner(System.in);
        int selection = scanner.nextInt();
        scanner.nextLine();
        if (selection == 1) {
            if(canRun()){
                if (Buyable.getItem("Pokeball") != null) {
                    try {
                        Buyable.getItem("Pokeball").use(opponentPokemon, playerPokemon);
                    } catch (Exception e) {
                        System.out.println("Something went wrong");
                    }
                } else {
                    System.out.println("You don't have pokeballs...");
                }
            }else{
                System.out.println("You can't use a pokeball against a trainer");
            }

        } else if (selection == 2) {
            if (Buyable.getItem("Potion") != null) {
                try {
                    Buyable.getItem("Potion").use(opponentPokemon, playerPokemon);
                } catch (Exception e) {
                    System.out.println("Something went wrong");
                }
            } else {
                System.out.println("You don't have potions...");
            }
        }
    }

    /**
     * @return Retorna el valor booleano dependiendo si el jugador tiene más pokemons con vida
     */
    private boolean canSwitchPokemon() {
        return GameContext.getInstance().getPlayer().getPokemons().size() > 1 && (!GameContext.getInstance().getPlayer().getPokemons().get(1).isFainted() || !GameContext.getInstance().getPlayer().getPokemons().get(0).isFainted());
    }

    /**
     * Cambia de pokemon manualmente durante la batalla
     */
    private void switchPokemonManually() {
        System.out.println("Switching Pokemon...");
        Pokemon secondPokemon = GameContext.getInstance().getPlayer().getPokemons().get(1);
        playerPokemon = secondPokemon;
        System.out.println("You switched to " + playerPokemon.getName());
    }

    /**
     * Cambia de pokemon si el pokemon es derrotado.
     */
    private void switchPokemonAutomatically() {
        System.out.println(playerPokemon.getName() + " fainted! Switching to next Pokemon...");
        Pokemon secondPokemon = GameContext.getInstance().getPlayer().getPokemons().get(1);
        playerPokemon = secondPokemon;
        System.out.println("You switched to " + playerPokemon.getName());
    }


    /**
     * Ejecuta el turno del jugador durante la batalla.
     */
    protected void opponentTurn() {
        System.out.println("Opponent's turn.");
        Random random = new Random();
        opponentPokemon.getAttacks().get(random.nextInt(opponentPokemon.getAttacks().size())).use(playerPokemon, opponentPokemon);
        displayBattleStatus();
    }


    /**
     * Despliega las stats de los pokemons después de cada turno.
     */
    protected void displayBattleStatus() {
        System.out.println(playerPokemon.getName() + " | HP: " + playerPokemon.getHp());
        System.out.println(opponentPokemon.getName() + " | HP: " + opponentPokemon.getHp());
    }
}
