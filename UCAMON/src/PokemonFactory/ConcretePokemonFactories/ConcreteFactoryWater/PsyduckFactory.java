package PokemonFactory.ConcretePokemonFactories.ConcreteFactoryWater;

import PokemonFactory.ConcretePokemonFactories.PokemonFactory;
import PokemonFactory.Pokemon.Attack.Attack;
import PokemonFactory.Pokemon.Attack.HydroBomb;
import PokemonFactory.Pokemon.Attack.Scratch;

import PokemonFactory.Pokemon.POKEMONTYPE;
import PokemonFactory.Pokemon.Pokemon;
import PokemonFactory.Pokemon.Water.Psyduck;
import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;

public class PsyduckFactory implements PokemonFactory {
    @Override
    public Pokemon createPokemon(int hp, int level,int hpMax,int xp, int speed) {
        ArrayList<Attack> attacks = new ArrayList<>();
        attacks.add(new HydroBomb());
        attacks.add(new Scratch());
        Texture sprite = new Texture("resources/PokemonSprites/Psyduck.png");
        Texture spriteOP = new Texture("resources/PokemonSprites/OpponentPokemon/Psyduck_OP.png");
        return new Psyduck("Psyduck", hp, level,hpMax,xp, speed, POKEMONTYPE.WATER,attacks, sprite, spriteOP);
    }
}
