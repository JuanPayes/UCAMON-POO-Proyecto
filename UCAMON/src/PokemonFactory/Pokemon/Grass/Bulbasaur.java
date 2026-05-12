package PokemonFactory.Pokemon.Grass;

import PokemonFactory.Pokemon.Attack.Attack;
import PokemonFactory.Pokemon.POKEMONTYPE;
import PokemonFactory.Pokemon.Pokemon;
import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;

public class Bulbasaur extends Pokemon{
    public Bulbasaur(String name, int hp, int level, int hpMax, int xp, int speed, POKEMONTYPE pokemontype, ArrayList<Attack> attacks, Texture sprite, Texture spriteOP) {
        super(name, hp, level,hpMax, xp, speed, pokemontype,attacks, sprite, spriteOP);
    }

    public Bulbasaur() {
    }

    @Override
    public String toString() {
        return "Bulbasaur";
    }
}
