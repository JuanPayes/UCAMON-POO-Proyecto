package PokemonFactory.Pokemon.Attack;

import PokemonFactory.Pokemon.Pokemon;

import java.util.Random;

public interface TypedAttack extends Attack {
    /**
     * Metodo que sirve para obtener el tipo de pokemon enemigo y en base a este determinar que tan efectivo sera el ataque que se le hace.Podria ser multiplicado por dos, por uno o por cero punto cinco.
     * @param enemy Recibe el pokemon al que se le hara el ataque.
     * @return Returna el multiplicador de efectividad.
     */
    double getTypeMultiplier(Pokemon enemy);

    /**
     * Metodo para decidir la posibilidad que el ataque falle.
     * @return Returna la posibilidad que falle
     */
    double getMissChance();

    @Override
    default boolean use(Pokemon enemy, Pokemon pokemon) {
        double missChance = getMissChance();
        if (Math.random() < missChance) {
            System.out.println(pokemon.getName() + "'s " + getName() + " missed!");
            return false;
        }

        int baseDamage = getBaseDamage();
        double levelMultiplier = 1 + (pokemon.getLevel() - 1) * 0.05;
        double typeMultiplier = getTypeMultiplier(enemy);
        double xpMultiplier = 1 + ((pokemon.getXp() - 30) / 100.0) * 0.2;

        int damage = (int) (baseDamage * levelMultiplier * typeMultiplier * xpMultiplier);
        enemy.setHp(enemy.getHp() - damage);

       // System.out.println(pokemon.getName() + " has used " + getName() + "!");
        //printEffectivenessMessage(typeMultiplier);
        return true;
    }

    /**
     * Obtiene el daño base de cada ataque.
     * @return Devuelve el daño base.
     */
    int getBaseDamage();

    /**
     * Muestra un mensaje que determina la efectividad del ataque realizado.
     * @param typeMultiplier Recibe el pokemon enemigo. Dependiendo del pokemon enemigo, se determina la efectivad de daño que se le hara.
     */
    default void printEffectivenessMessage(double typeMultiplier) {
        if (typeMultiplier == 0.5) {
            System.out.println("It's not very effective...");
        } else if (typeMultiplier == 2.0) {
            System.out.println("It's super effective!");
        }
    }
}
