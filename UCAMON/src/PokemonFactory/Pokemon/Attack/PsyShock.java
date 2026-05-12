package PokemonFactory.Pokemon.Attack;

import PokemonFactory.Pokemon.Pokemon;

public class PsyShock implements TypedAttack {

    @Override
    public int getBaseDamage() {
        return 12;
    }

    @Override
    public double getTypeMultiplier(Pokemon enemy) {
        switch (enemy.getPokemontype()) {
            case PSYCHIC:
                return 1.0;
            case NORMAL, FIRE, GRASS:
                return 2.0;
            default:
                return 1.0;
        }
    }

    @Override
    public double getMissChance() {
        return 0.15;
    }

    @Override
    public String getName() {
        return "PsyShock";
    }
}
