package Entities;
import PokemonFactory.Pokemon.Pokemon;
import java.util.ArrayList;

/**
 * La clase Entity representa una entidad en el juego que puede tener uno o varios Pokemones asociados.
 * Una entidad puede ser un jugador, un enemigo u otro tipo de personaje en el juego.
 */
public class Entity {

    /**
     Nombre de la entidad.
     */
    protected String name;

    /**
     * Lista de Pokémon asociados a la entidad.
     */
    protected ArrayList<Pokemon> pokemons;

    /**
     * Posición en el eje X. Se utilizará posteriormente en la implementación de la interfaz.
     */
    private int Xposition;

    /**
     * Posición en el eje Y. Se utilizará posteriormente en la implementación de la interfaz.
     */
    private int YPosition;

    public Entity() {
    }

    public Entity(String name, ArrayList<Pokemon> pokemons, int xposition, int YPosition) {
        this.name = name;
        this.pokemons = pokemons;
        Xposition = xposition;
        this.YPosition = YPosition;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Pokemon> getPokemons() {
        return pokemons;
    }

    public void setPokemons(ArrayList<Pokemon> pokemons) {
        this.pokemons = pokemons;
    }

    public int getXposition() {
        return Xposition;
    }

    public void setXposition(int xposition) {
        Xposition = xposition;
    }

    public int getYPosition() {
        return YPosition;
    }

    public void setYPosition(int YPosition) {
        this.YPosition = YPosition;
    }

}