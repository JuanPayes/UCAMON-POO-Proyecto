package SaveAndLoad;

import PokemonFactory.Pokemon.Pokemon;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * Esta clase representa el almacenamiento de datos del juego, incluyendo información sobre el jugador,
 * su progreso en el juego, los objetos que posee y los Pokémon capturados.
 * La clase implementa la interfaz Serializable para permitir la serialización de los datos.
 */

public class DataStorage implements Serializable {
    /**
     * Nombre del jugador.
     */
    String playerName;

    /**
     * Cantidad de dinero del jugador.
     */
    int money;

    /**
     * Mapa que almacena la cantidad de cada tipo de item que posee el jugador.
     */
    public Map<String, Integer> itemCounts = new HashMap<>();

    /**
     * Lista que contiene los Pokémon capturados por el jugador.
     */
    public ArrayList<Pokemon> pokemons = new ArrayList<>();

    /**
     * Nivel actual del juego.
     */
    int GameLevel;

    /**
     * Elección del pokemon inicial del jugador.
     */
    int initialChoice;
}