package PokemonFactory.Pokemon.Attack;

import PokemonFactory.Pokemon.Pokemon;

public class FlameThrower implements TypedAttack {

    @Override
    public int getBaseDamage() {
        return 12;
    }

    @Override
    public double getTypeMultiplier(Pokemon enemy) {
        switch (enemy.getPokemontype()) {
            case PSYCHIC, WATER:
                return 0.5;
            case NORMAL, FIRE:
                return 1.0;
            case GRASS:
                return 2.0;
            default:
                return 1.0;
        }
    }

    @Override
    public double getMissChance() {
        return 0.20;
    }

    @Override
    public String getName() {
        return "FlameThrower";
    }
}
