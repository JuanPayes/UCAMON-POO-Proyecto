package GameState.BattleStates;

import GameState.GameContext;
import PokemonFactory.FactoryClient;
import PokemonFactory.Pokemon.Pokemon;
import PokemonFactory.PokemonEnum;

import java.util.Random;

/**
 * Se encarga de crear un pokemon salvaje al azar.
 */
public class WildPokemonSelector {
    private static Random random = new Random();

    public static Pokemon getRandomWildPokemon() {
        int choice = random.nextInt(100);
        int level = random.nextInt(3 + 2* GameContext.getInstance().getGamelevel()) + 1;
        int levelMew = random.nextInt(15) + 10;

        if (choice < 1) {
            return FactoryClient.getPokemon(PokemonEnum.MEW, 100 + (levelMew - 1) * (int) (100 * 0.1), levelMew, 100 + (levelMew - 1) * (int) (100 * 0.1), 100, 100 + (levelMew - 1) * (int) (100 * 0.07));
        } else if (choice < 21) {
            return FactoryClient.getPokemon(PokemonEnum.GROWLITHE, 30 + (level - 1) * (int) (30 * 0.1), level, 30 + (level - 1) * (int) (30 * 0.1), 30, 45 + (level - 1) * (int) (45 * 0.07));
        } else if (choice < 41) {
            return FactoryClient.getPokemon(PokemonEnum.JIGGLYPUFF, 50 + (level - 1) * (int) (50 * 0.1), level, 50 + (level - 1) * (int) (50 * 0.1), 30, 35 + (level - 1) * (int) (35 * 0.07));
        } else if (choice < 61) {
            return FactoryClient.getPokemon(PokemonEnum.ODDISH, 25 + (level - 1) * (int) (25 * 0.1), level, 25 + (level - 1) * (int) (25 * 0.1), 30, 35 + (level - 1) * (int) (35 * 0.07));
        } else if (choice < 81) {
            return FactoryClient.getPokemon(PokemonEnum.PSYDUCK, 40 + (level - 1) * (int) (40 * 0.1), level, 40 + (level - 1) * (int) (40 * 0.1), 30, 50 + (level - 1) * (int) (50 * 0.07));
        } else {
            int subChoice = random.nextInt(4);
            switch (subChoice) {
                case 0:
                    return FactoryClient.getPokemon(PokemonEnum.GROWLITHE, 30 + (level - 1) * (int) (30 * 0.1), level, 50 + (level - 1) * (int) (50 * 0.1), 30, 45 + (level - 1) * (int) (45 * 0.07));
                case 1:
                    return FactoryClient.getPokemon(PokemonEnum.JIGGLYPUFF, 50 + (level - 1) * (int) (50 * 0.1), level, 50 + (level - 1) * (int) (50 * 0.1), 30, 35 + (level - 1) * (int) (35 * 0.07));
                case 2:
                    return FactoryClient.getPokemon(PokemonEnum.ODDISH, 25 + (level - 1) * (int) (25 * 0.1), level, 25 + (level - 1) * (int) (25 * 0.1), 30, 35 + (level - 1) * (int) (35 * 0.07));
                case 3:
                    return FactoryClient.getPokemon(PokemonEnum.PSYDUCK, 40 + (level - 1) * (int) (40 * 0.1), level, 40 + (level - 1) * (int) (40 * 0.1), 30, 50 + (level - 1) * (int) (50 * 0.07));
                default:
                    throw new IllegalStateException("Unexpected value: " + subChoice);
            }
        }
    }
}