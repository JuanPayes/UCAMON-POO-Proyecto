package PokemonFactory.Pokemon.Fire;

import PokemonFactory.Pokemon.Attack.Attack;
import PokemonFactory.Pokemon.POKEMONTYPE;
import PokemonFactory.Pokemon.Pokemon;
import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;

public class Growlithe extends Pokemon{
    public Growlithe(String name, int hp, int level, int hpMax,int xp, int speed, POKEMONTYPE pokemontype, ArrayList<Attack> attacks, Texture sprite, Texture spriteOP) {
        super(name, hp, level, hpMax,xp, speed, pokemontype,attacks,sprite, spriteOP);
    }

    public Growlithe() {
    }

    @Override
    public String toString() {
        return "Growlithe";
    }

}
