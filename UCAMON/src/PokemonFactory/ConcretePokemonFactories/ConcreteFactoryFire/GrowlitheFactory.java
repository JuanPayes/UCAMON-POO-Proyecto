package PokemonFactory.ConcretePokemonFactories.ConcreteFactoryFire;

import PokemonFactory.ConcretePokemonFactories.PokemonFactory;
import PokemonFactory.Pokemon.Attack.Attack;
import PokemonFactory.Pokemon.Attack.BodySlam;
import PokemonFactory.Pokemon.Attack.FireBlast;
import PokemonFactory.Pokemon.Attack.Scratch;
import PokemonFactory.Pokemon.Fire.Growlithe;
import PokemonFactory.Pokemon.POKEMONTYPE;
import PokemonFactory.Pokemon.Pokemon;
import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;

public class GrowlitheFactory implements PokemonFactory {

    public Pokemon createPokemon( int hp, int level,int hpMax,int xp, int speed) {
        ArrayList<Attack> attacks = new ArrayList<>();
        attacks.add(new FireBlast());
        attacks.add(new Scratch());
        Texture sprite = new Texture("resources/PokemonSprites/Growlithe.png");
        Texture spriteOP = new Texture("resources/PokemonSprites/OpponentPokemon/Growlithe_OP.png");
        return new Growlithe("Growlithe", hp, level,hpMax,xp, speed, POKEMONTYPE.FIRE,attacks, sprite, spriteOP);
    }
}
