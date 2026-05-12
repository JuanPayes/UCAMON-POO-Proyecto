package PokemonFactory.Pokemon.Attack;

import PokemonFactory.Pokemon.Pokemon;

public class BodySlam implements TypedAttack {

    @Override
    public int getBaseDamage() {
        return 13;
    }

    @Override
    public double getTypeMultiplier(Pokemon enemy) {
        switch (enemy.getPokemontype()) {
            case PSYCHIC:
                return 0.5;
            case WATER, FIRE, NORMAL, GRASS:
                return 1.0;
            default:
                return 1.0;
        }
    }

    @Override
    public double getMissChance() {
        return 0.25;
    }

    @Override
    public String getName() {
        return "Body Slam";
    }
}
