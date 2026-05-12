package PokemonFactory.Pokemon.Water;

import PokemonFactory.Pokemon.Attack.Attack;
import PokemonFactory.Pokemon.POKEMONTYPE;
import PokemonFactory.Pokemon.Pokemon;
import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;

public class Psyduck extends Pokemon{
    public Psyduck(String name, int hp, int nivel, int hpMax, int xp, int speed, POKEMONTYPE pokemontype, ArrayList<Attack> attacks, Texture sprite, Texture spriteOP) {
        super(name, hp, nivel ,hpMax, xp, speed, pokemontype,attacks,sprite,spriteOP);
    }

    public Psyduck() {
    }

    @Override
    public String toString() {
        return "Psyduck";
    }
}
