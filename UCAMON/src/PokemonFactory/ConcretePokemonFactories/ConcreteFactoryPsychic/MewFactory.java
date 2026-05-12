package PokemonFactory.ConcretePokemonFactories.ConcreteFactoryPsychic;

import PokemonFactory.ConcretePokemonFactories.PokemonFactory;
import PokemonFactory.Pokemon.Attack.*;
import PokemonFactory.Pokemon.POKEMONTYPE;
import PokemonFactory.Pokemon.Pokemon;
import PokemonFactory.Pokemon.Psychic.Mew;
import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;

public class MewFactory implements PokemonFactory {
    public Pokemon createPokemon(int hp, int level,int hpMax,int xp, int speed) {
        ArrayList<Attack> attacks = new ArrayList<>();
        attacks.add(new Psychic());
        attacks.add(new PsyShock());
        attacks.add(new Scratch());
        Texture sprite = new Texture("resources/PokemonSprites/Mew.png");
        Texture spriteOP = new Texture("resources/PokemonSprites/OpponentPokemon/Mew_OP.png");
        return new Mew("Mew", hp, level,hpMax,xp, speed, POKEMONTYPE.PSYCHIC,attacks, sprite, spriteOP);
    }
}
