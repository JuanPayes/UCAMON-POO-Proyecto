package PokemonFactory.Pokemon.Water;

import PokemonFactory.Pokemon.Attack.Attack;
import PokemonFactory.Pokemon.Attack.BodySlam;
import PokemonFactory.Pokemon.POKEMONTYPE;
import PokemonFactory.Pokemon.Pokemon;
import com.badlogic.gdx.graphics.Texture;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class Squirtle extends Pokemon{

    public Squirtle(String name, int hp, int nivel, int hpMax , int xp, int speed, POKEMONTYPE pokemontype, ArrayList<Attack> attacks, Texture sprite, Texture spriteOP) {
        super(name, hp, nivel, hpMax, xp, speed, pokemontype, attacks, sprite, spriteOP);
    }

    public Squirtle() {
    }

    @Override
    public String toString() {
        return "Squirtle";
    }
}
