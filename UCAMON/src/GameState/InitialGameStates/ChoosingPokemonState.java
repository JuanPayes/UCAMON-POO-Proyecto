package GameState.InitialGameStates;

import GameState.GameState;
import PokemonFactory.FactoryClient;
import PokemonFactory.PokemonEnum;
import GameState.GameContext;
import java.util.Scanner;
import GameState.ExploringState;
/**
 * Clase encargaa para escoger un pokemon al inicio de cada partida.
 */
public class ChoosingPokemonState implements GameState {
    /**
     * Ejecuta un breve dialogo y después el jugador escoge un pokemon y recibe monedas.
     */
    @Override
    public void handle() {
        Scanner sc = new Scanner(System.in);
        System.out.println("NPC: Hey! This place is absolute chaos!");
        System.out.println("Press Enter to continue...");
        sc.nextLine();
        System.out.println("NPC: You want to go in? Here, I'll give you a couple of things that can help! (the NPC has has given you 100 coins, lucky you!).");
        System.out.println("Press Enter to continue...");
        sc.nextLine();
        System.out.println("NPC: That's all I have left. When the pokemon went crazy, they stole all my money!");
        System.out.println("Press Enter to continue...");
        sc.nextLine();
        System.out.println("NPC: Surely whenever you defeat one, they'll drop some of the money they stole from me! Those darn pokemon...");
        System.out.println("Press Enter to continue...");
        sc.nextLine();
        System.out.println("NPC: Anyway, here, to help you in your journey, choose one of these pokemon. I hope they help in your adventure!");

        boolean validChoice = false;
        while(!validChoice) {
            System.out.println("1.Charmander\n2.Bulbasaur\n3.Squirtle");
            int choice =sc.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("You have chosen Charmander!");
                    GameContext.getInstance().setInitialChoice(1);
                    GameContext.getInstance().getPlayer().getPokemons().add(FactoryClient.getPokemon(PokemonEnum.CHARMANDER,25 ,1,25,0, 45));
                    validChoice = true;
                    break;
                case 2:
                    System.out.println("You have chosen Bulbasaur!");
                    GameContext.getInstance().setInitialChoice(2);
                    GameContext.getInstance().getPlayer().getPokemons().add(FactoryClient.getPokemon(PokemonEnum.BULBASAUR, 30,1,30, 0, 30));
                    validChoice = true;
                    break;
                case 3:
                    System.out.println("You have chosen Squirtle!");
                    GameContext.getInstance().setInitialChoice(3);
                    GameContext.getInstance().getPlayer().getPokemons().add(FactoryClient.getPokemon(PokemonEnum.SQUIRTALE, 40,1,40, 0, 25));
                    validChoice = true;
                    break;
                default:
                    System.out.println("Invalid option! Please choose a valid Pokemon!");
                    break;
            }
        }
        System.out.println("NPC: Remember, Pokemons that are faster than yours attack first, so be careful! ");
        System.out.println("Press Enter to continue...");
        sc.nextLine();
        sc.nextLine();
        System.out.println("NPC: Oh, and one more thing! I've heard people say there is an extremely rare pokemon roaming around... beware if you find it, it must be very powerful!");
        System.out.println("Press Enter to continue...");
        sc.nextLine();
        System.out.println("NPC: That's all I can do for you! Please beware and save us all from OwlUCA!");
        System.out.println("Press enter to begin the adventure...");
        sc.nextLine();
        GameContext.getInstance().setGamelevel(1);
        GameContext.getInstance().setState(new ExploringState());
    }
}
