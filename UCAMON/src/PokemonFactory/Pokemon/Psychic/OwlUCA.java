package PokemonFactory.Pokemon.Psychic;

import PokemonFactory.Pokemon.Attack.Attack;
import PokemonFactory.Pokemon.POKEMONTYPE;
import PokemonFactory.Pokemon.Pokemon;
import com.badlogic.gdx.graphics.Texture;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class OwlUCA extends Pokemon {

    public OwlUCA(String name, int hp, int level, int hpMax, int xp, int speed, POKEMONTYPE pokemontype, ArrayList<Attack> attacks, Texture sprite, Texture spriteOP) {
        super(name, hp, level, hpMax, xp, speed, pokemontype,attacks, sprite, spriteOP);
    }

    public OwlUCA() {
    }

    @Override
    public String toString() {
        return "OwlUCA";
    }

}
