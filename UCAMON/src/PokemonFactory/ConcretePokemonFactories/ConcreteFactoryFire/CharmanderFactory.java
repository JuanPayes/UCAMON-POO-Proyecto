package PokemonFactory.ConcretePokemonFactories.ConcreteFactoryFire;

import PokemonFactory.ConcretePokemonFactories.PokemonFactory;
import PokemonFactory.Pokemon.Attack.Attack;
import PokemonFactory.Pokemon.Attack.BodySlam;
import PokemonFactory.Pokemon.Attack.FlameThrower;
import PokemonFactory.Pokemon.Attack.Scratch;
import PokemonFactory.Pokemon.Fire.Charmander;
import PokemonFactory.Pokemon.POKEMONTYPE;
import PokemonFactory.Pokemon.Pokemon;
import com.badlogic.gdx.graphics.Texture;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class CharmanderFactory implements PokemonFactory {

    public Pokemon createPokemon(int hp, int level,int hpMax,int xp, int speed) {
        ArrayList<Attack> attacks = new ArrayList<>();
        attacks.add(new FlameThrower());
        attacks.add(new Scratch());
        Texture sprite = new Texture("resources/PokemonSprites/Charmander.png");
        Texture spriteOP = new Texture("resources/PokemonSprites/OpponentPokemon/Charmander_OP.png");
        return new Charmander("Charmander", hp, level, hpMax, xp, speed, POKEMONTYPE.FIRE,attacks, sprite, spriteOP);
    }
}
