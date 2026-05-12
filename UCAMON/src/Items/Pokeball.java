package Items;

import Entities.Player;
import GameState.ExploringState;
import GameState.GameContext;
import PokemonFactory.Pokemon.Pokemon;
import java.io.Serializable;

/**
 * La clase Pokeball representa una pokebola en el juego que se puede usar para atrapar Pokemones salvajes.
 * Hereda de Buyable, por lo que tiene acceso a sus métodos.
 */

public class Pokeball extends Buyable implements Serializable {
    public Pokeball() {
    }


    /**
     * Obtiene el nombre de la pokebola.
     *
     * @return El nombre de la pokebola, que es "Pokeball".
     */
    @Override
    public String getName() {
        return "Pokeball";
    }

    /**
     * Método para usar la pokebola para intentar atrapar a un Pokémon salvaje.
     * La probabilidad de éxito de atrapar el Pokemón depende del porcentaje de su salud restante.
     * Si el lanzamiento de la pokebola tiene éxito, el Pokemón salvaje es capturado y se añade al equipo del jugador.
     * Además, se elimina la pokebola del inventario del jugador y se imprime un mensaje indicando que el jugador ha capturado al Pokemón.
     * El Pokemón del jugador también gana experiencia equivalente al doble del nivel del Pokemón salvaje capturado.
     * Se verifica si el Pokemón del jugador sube de nivel tras ganar experiencia.
     *
     * @param enemy   El Pokemón salvaje al que se intenta atrapar.
     * @param pokemon El Pokemón del jugador que está utilizando la pokebola.
     */

    @Override
    public boolean use(Pokemon enemy, Pokemon pokemon) {
        double catchSuccessRate;
        double remainingHpPercent = (double) enemy.getHp() / enemy.getHpMax();

        if (remainingHpPercent == 1.0) {
            catchSuccessRate = 0.05;
        } else if (remainingHpPercent >= 0.75) {
            catchSuccessRate = 0.15;
        } else if (remainingHpPercent >= 0.5) {
            catchSuccessRate = 0.25;
        } else if (remainingHpPercent >= 0.16) {
            catchSuccessRate = 0.4;
        } else {
            catchSuccessRate = 0.7;
        }

        double randomValue = Math.random();

        if (randomValue < catchSuccessRate) {
            GameContext.getInstance().getPlayer().getPokemons().add(enemy);
            Buyable.removeItem("Pokeball");
            System.out.println("You have caught " + enemy.getName() + "!");
            pokemon.gainXp(enemy.getLevel() * 2);
            pokemon.checkLevelUp();
            return true;
        } else {
            return false;
        }
    }

    /**
     * Representación de cadena de la pokebola.
     *
     * @return La representación de cadena de la pokebola, que es "Pokeball".
     */
    @Override
    public String toString() {
        return "Pokeball";
    }
}