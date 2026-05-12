package PokemonFactory.ConcretePokemonFactories;

import PokemonFactory.Pokemon.POKEMONTYPE;
import PokemonFactory.Pokemon.Pokemon;

public interface PokemonFactory {
    /**
     * Metodo que crea el pokemon. Dentro del metodo se crea una colleccion de tipo ataque para agregar los ataques del pokemon que se quiere crear.Por ejemplo:
     * ArrayList<Attack> attacks = new ArrayList<>();
     * attacks.add(new "Nombre del ataque"());
     * @param hp Determina la vida del pokemon.
     * @param level Determina el nivel del pokemon.
     * @param hpMax Determina la vida máxima.
     * @param xp Determina la experiencia del pokemon.
     * @param speed Determina la velocidad del pokemon.
     * @return Retorna un nuevo pokemon con todas las cosas que este necesita para ser creado.
     */
    public Pokemon createPokemon(int hp, int level, int hpMax, int xp, int speed);

}
