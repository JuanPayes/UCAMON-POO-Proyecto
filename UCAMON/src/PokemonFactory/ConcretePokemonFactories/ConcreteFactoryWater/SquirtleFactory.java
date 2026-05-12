package PokemonFactory.ConcretePokemonFactories.ConcreteFactoryWater;

import PokemonFactory.ConcretePokemonFactories.PokemonFactory;
import PokemonFactory.Pokemon.Attack.Attack;
import PokemonFactory.Pokemon.Attack.HydroBomb;
import PokemonFactory.Pokemon.Attack.Scratch;
import PokemonFactory.Pokemon.Attack.WaterGun;
import PokemonFactory.Pokemon.POKEMONTYPE;
import PokemonFactory.Pokemon.Pokemon;
import PokemonFactory.Pokemon.Water.Squirtle;
import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;

public class SquirtleFactory implements PokemonFactory {
    public Pokemon createPokemon(int hp, int level,int hpMax,int xp, int speed) {
        ArrayList<Attack> attacks = new ArrayList<>();
        attacks.add(new WaterGun());
        attacks.add(new Scratch());
        Texture sprite = new Texture("resources/PokemonSprites/Squirtle.png");
        Texture sprite_OP = new Texture("resources/PokemonSprites/OpponentPokemon/Squirtle_OP.png");
        return new Squirtle("Squirtle", hp, level,hpMax, xp,speed,POKEMONTYPE.WATER,attacks, sprite, sprite_OP);
    }
}
