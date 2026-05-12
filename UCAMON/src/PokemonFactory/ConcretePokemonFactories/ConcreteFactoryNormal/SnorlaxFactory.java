package PokemonFactory.ConcretePokemonFactories.ConcreteFactoryNormal;

import PokemonFactory.ConcretePokemonFactories.PokemonFactory;
import PokemonFactory.Pokemon.Attack.Attack;
import PokemonFactory.Pokemon.Attack.BodySlam;
import PokemonFactory.Pokemon.Attack.Scratch;
import PokemonFactory.Pokemon.Normal.Snorlax;
import PokemonFactory.Pokemon.POKEMONTYPE;
import PokemonFactory.Pokemon.Pokemon;
import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;

public class SnorlaxFactory implements PokemonFactory {
    public Pokemon createPokemon(int hp, int level,int hpMax,int xp, int speed) {
        ArrayList<Attack> attacks = new ArrayList<>();
        attacks.add(new BodySlam());
        attacks.add(new Scratch());
        Texture spriteOP = new Texture("resources/PokemonSprites/OpponentPokemon/Snorlax_OP.png");
        return new Snorlax("Snorlax", hp, level,hpMax,xp, speed, POKEMONTYPE.NORMAL,attacks, spriteOP, spriteOP);
    }
}
