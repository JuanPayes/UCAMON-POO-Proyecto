package PokemonFactory.Pokemon.Attack;

import PokemonFactory.Pokemon.Pokemon;

public class Scratch implements TypedAttack {

    @Override
    public int getBaseDamage() {
        return 6;
    }

    @Override
    public double getTypeMultiplier(Pokemon enemy) {

        return 1.0;
    }

    @Override
    public double getMissChance() {
        return 0.15;
    }

    @Override
    public String getName() {
        return "Scratch";
    }
}
