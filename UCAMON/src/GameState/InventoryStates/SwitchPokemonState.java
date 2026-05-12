package GameState.InventoryStates;
import GameState.*;

import PokemonFactory.Pokemon.Pokemon;

import java.util.List;
import java.util.Scanner;

public class SwitchPokemonState implements GameState {
    @Override
    public void handle() {
        List<Pokemon> pokemons = GameContext.getInstance().getPlayer().getPokemons();
        if (pokemons == null || pokemons.isEmpty()) {
            System.out.println("You have no Pokémons!");
            GameContext.getInstance().setState(new ExploringState());
            return;
        }

        displayAvailablePokemons(pokemons);
        selectBattleTeam(pokemons);
        GameContext.getInstance().setState(new ExploringState());
    }

    private void displayAvailablePokemons(List<Pokemon> pokemons) {
        System.out.println("Choose your battle team (select 2 Pokémons):");
        int i = 1;
        for (Pokemon p : pokemons) {
            System.out.println(i + ". " + p.getName() + " | HP: " + p.getHp() + " | Speed: " + p.getSpeed() + " | Level: " + p.getLevel());
            i++;
        }
    }

    private void selectBattleTeam(List<Pokemon> pokemons) {
        Scanner scanner = new Scanner(System.in);
        int firstChoice = -1;
        int secondChoice = -1;

        while (firstChoice < 1 || firstChoice > pokemons.size()) {
            System.out.print("Select the first Pokémon (1-" + pokemons.size() + "): ");
            firstChoice = scanner.nextInt();
        }

        while (secondChoice < 1 || secondChoice > pokemons.size() || secondChoice == firstChoice) {
            System.out.print("Select the second Pokémon (1-" + pokemons.size() + "): ");
            secondChoice = scanner.nextInt();
        }


        Pokemon firstPokemon = pokemons.get(firstChoice - 1);
        Pokemon secondPokemon = pokemons.get(secondChoice - 1);
        pokemons.set(firstChoice - 1, pokemons.get(0));
        pokemons.set(secondChoice - 1, pokemons.get(1));
        pokemons.set(0, firstPokemon);
        pokemons.set(1, secondPokemon);

        System.out.println("Your battle team is set: " + pokemons.get(0).getName() + " and " + pokemons.get(1).getName());
    }
}
