package PokemonFactory.Pokemon.Attack;

import PokemonFactory.Pokemon.Pokemon;

import java.io.Serializable;

public interface Attack extends Serializable {
    /**
     * Metodo que sirve para calcular la cantidad de daño que hara cada ataque evaluando si el ataque falla o si es super efectivo.
     * @param enemy Recibe el pokemon enemigo al que se le realizara el ataque.
     * @param pokemon Recibe el pokemon que esta realizando el ataque
     */
    boolean use(Pokemon enemy, Pokemon pokemon);

    /**
     * Obtiene el nombre en String del ataque que se esta usando.
     * @return Devuelve el nombre del ataque
     */
    String getName();
}
