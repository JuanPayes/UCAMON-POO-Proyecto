package PokemonFactory.Pokemon.Attack;

import PokemonFactory.Pokemon.Pokemon;

public class FireBlast implements TypedAttack {

    @Override
    public int getBaseDamage() {
        return 14;
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
        return 0.35;
    }

    @Override
    public String getName() {
        return "Fireblast";
    }
}
