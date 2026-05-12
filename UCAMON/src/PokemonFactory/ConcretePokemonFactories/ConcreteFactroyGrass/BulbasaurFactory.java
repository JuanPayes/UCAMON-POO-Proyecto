package PokemonFactory.ConcretePokemonFactories.ConcreteFactroyGrass;

import PokemonFactory.ConcretePokemonFactories.PokemonFactory;
import PokemonFactory.Pokemon.Attack.*;
import PokemonFactory.Pokemon.Grass.Bulbasaur;
import PokemonFactory.Pokemon.POKEMONTYPE;
import PokemonFactory.Pokemon.Pokemon;
import com.badlogic.gdx.graphics.Texture;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class BulbasaurFactory implements PokemonFactory {
    public Pokemon createPokemon(int hp, int level,int hpMax,int xp, int speed) {
        ArrayList<Attack> attacks = new ArrayList<>();
        attacks.add(new RazorLeaf());
        attacks.add(new Scratch());
        Texture sprite = new Texture("resources/PokemonSprites/Bulbasaur.png");
        Texture spriteOP = new Texture("resources/PokemonSprites/OpponentPokemon/Bulbasaur_OP.png");
        return new Bulbasaur("Bulbasaur", hp, level,hpMax, xp,speed, POKEMONTYPE.GRASS,attacks, sprite, spriteOP);
    }
}
