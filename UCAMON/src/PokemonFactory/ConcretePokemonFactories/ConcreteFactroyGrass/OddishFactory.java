package PokemonFactory.ConcretePokemonFactories.ConcreteFactroyGrass;

import PokemonFactory.ConcretePokemonFactories.PokemonFactory;
import PokemonFactory.Pokemon.Attack.Attack;
import PokemonFactory.Pokemon.Attack.Scratch;
import PokemonFactory.Pokemon.Attack.VineWhip;
import PokemonFactory.Pokemon.Grass.Oddish;
import PokemonFactory.Pokemon.POKEMONTYPE;
import PokemonFactory.Pokemon.Pokemon;
import com.badlogic.gdx.graphics.Texture;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class OddishFactory implements PokemonFactory {
    public Pokemon createPokemon(int hp, int level,int hpMax,int xp, int speed) {
        ArrayList<Attack> attacks = new ArrayList<>();
        attacks.add(new VineWhip());
        attacks.add(new Scratch());
        Texture sprite = new Texture("resources/PokemonSprites/Oddish.png");
        Texture spirteOP = new Texture("resources/PokemonSprites/OpponentPokemon/Oddish_OP.png");
        return new Oddish("Oddish",hp, level,hpMax, xp,speed, POKEMONTYPE.GRASS,attacks, sprite, spirteOP);
    }
}
