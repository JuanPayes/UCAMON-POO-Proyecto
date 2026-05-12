package PokemonFactory.ConcretePokemonFactories.ConcreteFactoryNormal;

import PokemonFactory.ConcretePokemonFactories.PokemonFactory;
import PokemonFactory.Pokemon.Attack.Attack;
import PokemonFactory.Pokemon.Attack.BodySlam;
import PokemonFactory.Pokemon.Attack.HyperVoice;
import PokemonFactory.Pokemon.Attack.Scratch;
import PokemonFactory.Pokemon.Normal.Jigglypuff;
import PokemonFactory.Pokemon.POKEMONTYPE;
import PokemonFactory.Pokemon.Pokemon;
import com.badlogic.gdx.graphics.Texture;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class JigglypuffFactory implements PokemonFactory {
    public Pokemon createPokemon(int hp,int level,int hpMax, int xp, int speed) {
        ArrayList<Attack> attacks = new ArrayList<>();
        attacks.add(new HyperVoice());
        attacks.add(new Scratch());
        Texture sprite = new Texture("resources/PokemonSprites/Jigglypuff.png");
        Texture spriteOP = new Texture("resources/PokemonSprites/OpponentPokemon/Jigglypuff_OP.png");
        return new Jigglypuff("Jigglypuff", hp,level,hpMax, xp, speed, POKEMONTYPE.NORMAL,attacks, sprite, spriteOP);
    }
}
