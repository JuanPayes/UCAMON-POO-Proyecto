package PokemonFactory.Pokemon.Attack;

import PokemonFactory.Pokemon.Pokemon;

public class QuantumCalculus implements TypedAttack {

    @Override
    public int getBaseDamage() {
        return 16;
    }

    @Override
    public double getTypeMultiplier(Pokemon enemy) {
        switch (enemy.getPokemontype()) {
            case PSYCHIC:
                return 0.5;
            case WATER, FIRE, GRASS:
                return 1.0;
            case NORMAL:
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
        return "Quantum Calculus";
    }
}
