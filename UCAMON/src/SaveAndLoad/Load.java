package SaveAndLoad;

import GameState.GameContext;
import Items.Buyable;
import Items.Pokeball;
import Items.Potion;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.Map;

/**
 * Esta clase se encarga de cargar los datos guardados del juego.
 * Utiliza la clase DataStorage para almacenar los datos cargados.
 */

public class Load {
    public Load() {
    }

    /**
     * Método encargado de determinar y retornar un objeto de tipo Buyable basado en el nombre del ítem proporcionado.
     *
     * @param itemName el nombre del ítem que se desea obtener.
     * @return El ítem correspondiente, o null si el nombre no coincide con ningún ítem.
     */

    public Buyable getItem(String itemName){
        Buyable item = null;
        switch(itemName){
            case "Pokeball":
                item = new Pokeball();
                break;
            case "Potion":
                item = new Potion();
                break;
        }
        return item;
    }

    /**
     * Carga los datos de un juego guardado desde un archivo.
     * Lee un archivo "save.dat" que contiene los datos de la partida guardada y los carga en GameContext.
     * Los datos cargados incluyen el nivel del juego, la elección inicial de pokemon, el nombre y el dinero del jugador,
     * así como los items y los Pokemón del jugador.
     * Si ocurre algún error durante la carga, se imprime un mensaje de error y se lanza una excepción.
     *
     * @throws NullPointerException Si ocurre un error al cargar el juego.
     */

    public void load() {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("save.dat")));
            DataStorage ds = (DataStorage) ois.readObject();
            ois.close();
            GameContext.getInstance().setGamelevel(ds.GameLevel);
            GameContext.getInstance().setInitialChoice(ds.initialChoice);
            GameContext.getInstance().getPlayer().setName(ds.playerName);
            GameContext.getInstance().getPlayer().setMoney(ds.money);
            GameContext.getInstance().getPlayer().getItems().clear();

            for (Map.Entry<String, Integer> entry : ds.itemCounts.entrySet()) {
                String itemName = entry.getKey();
                int itemAmount = entry.getValue();

                for (int j = 0; j < itemAmount; j++) {
                    Buyable item = getItem(itemName);
                    if (item != null) {
                        GameContext.getInstance().getPlayer().getItems().add(item);
                    }
                }
            }
            GameContext.getInstance().getPlayer().getPokemons().clear();
            GameContext.getInstance().getPlayer().getPokemons().addAll(ds.pokemons);
            System.out.println("Loading game...");
        } catch (Exception e) {
            System.out.println("Couldn't load game");
            throw new NullPointerException();
        }
    }
}