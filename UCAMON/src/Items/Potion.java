package Items;

import Entities.Player;
import GameState.GameContext;
import PokemonFactory.Pokemon.Pokemon;

import java.io.Serializable;

/**
 * La clase Potion representa una poción en el juego que se puede usar para curar a un Pokemón del jugador.
 */

public class Potion extends Buyable implements Serializable {

    public Potion() {
    }

    /**
     * Obtiene el nombre de la poción.
     *
     * @return El nombre de la poción, que es "Potion".
     */
    @Override
    public String getName() {
        return "Potion";
    }

    /**
     * Método para usar la poción y curar a un Pokemón del jugador.
     * El Pokémon recibe un aumento en su salud igual al 60% de su salud máxima.
     * Si la salud del Pokémon después de usar la poción excede su salud máxima, se establece en su salud máxima.
     *
     * @param enemy   No se utiliza en este caso.
     * @param pokemon El Pokémon del jugador que recibirá la curación.
     */
    @Override
    public boolean use(Pokemon enemy, Pokemon pokemon) {
        pokemon.setHp((int) (pokemon.getHpMax() + pokemon.getHpMax()*0.60));
        if(pokemon.getHp() > pokemon.getHpMax()) pokemon.setHp(pokemon.getHpMax());
        System.out.println(GameContext.getInstance().getPlayer().getName() + " gave " + pokemon.getName() + " a potion");
        Buyable.removeItem("Potion");
        return true;
    }

    /**
     * Representación de cadena de la poción.
     *
     * @return La representación de cadena de la poción, que es "Potion".
     */
    @Override
    public String toString() {
        return "Potion";
    }
}