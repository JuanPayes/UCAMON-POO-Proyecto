package PokemonFactory;

import PokemonFactory.ConcretePokemonFactories.ConcreteFactoryFire.CharmanderFactory;
import PokemonFactory.ConcretePokemonFactories.ConcreteFactoryFire.GrowlitheFactory;
import PokemonFactory.ConcretePokemonFactories.ConcreteFactoryNormal.JigglypuffFactory;
import PokemonFactory.ConcretePokemonFactories.ConcreteFactoryNormal.SnorlaxFactory;
import PokemonFactory.ConcretePokemonFactories.ConcreteFactoryPsychic.MewFactory;
import PokemonFactory.ConcretePokemonFactories.ConcreteFactoryPsychic.OwlUCAFactory;
import PokemonFactory.ConcretePokemonFactories.ConcreteFactoryWater.PsyduckFactory;
import PokemonFactory.ConcretePokemonFactories.ConcreteFactoryWater.SquirtleFactory;
import PokemonFactory.ConcretePokemonFactories.ConcreteFactroyGrass.BulbasaurFactory;
import PokemonFactory.ConcretePokemonFactories.ConcreteFactroyGrass.OddishFactory;
import PokemonFactory.ConcretePokemonFactories.PokemonFactory;
import PokemonFactory.Pokemon.Pokemon;

public class FactoryClient {
    public FactoryClient() {}

    /**
     * Metodo para manejar de una manera mas ordenada todas las factorias de los diferentes pokemones para crearlos.
     * @param pokemonEnum Determina el pokemon que se va a crear.
     * @param hp Determina la vida del pokemon.
     * @param level Determina el nivel del pokemon.
     * @param hpMax Determina la vida maxima del pokemon.
     * @param xp Determina la experiencia que tiene el pokemon.
     * @param speed determina la velocidad del pokemon.
     * @return Da como resultado el pokemon que se creara.
     */
    public static Pokemon getPokemon(PokemonEnum pokemonEnum, int hp, int level, int hpMax, int xp, int speed) {
        PokemonFactory factory = null;

        switch (pokemonEnum) {
            case CHARMANDER -> factory = new CharmanderFactory();
            case GROWLITHE -> factory = new GrowlitheFactory();
            case SQUIRTALE ->  factory = new SquirtleFactory();
            case PSYDUCK -> factory = new PsyduckFactory();
            case BULBASAUR -> factory = new BulbasaurFactory();
            case ODDISH -> factory = new OddishFactory();
            case JIGGLYPUFF -> factory = new JigglypuffFactory();
            case SNORLAX -> factory = new SnorlaxFactory();
            case OWLUCA -> factory = new OwlUCAFactory();
            case MEW -> factory = new MewFactory();
            default -> {
                System.out.println("There is no factory");
                return null;
            }
        }

        return factory.createPokemon(hp, level, hpMax,xp, speed);
    }
}
