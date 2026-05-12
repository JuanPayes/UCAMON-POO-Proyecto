package SaveAndLoad;

import GameState.GameContext;
import Items.Buyable;
import Items.Pokeball;
import Items.Potion;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Esta clase se encarga de guardar los datos del juego en un archivo binario llamado "save.dat".
 * Utiliza la clase DataStorage para almacenar los datos a guardar.
 */

public class Save {

    public Save() {
    }

    /**
     * Guarda los datos del juego en un archivo binario llamado "save.dat".
     * Recopila los datos del juego, incluyendo el dinero del jugador, el nivel del juego,
     * la elección inicial del jugador, la cantidad de cada ítem que posee el jugador,
     * el nombre del jugador y los Pokémon capturados por el jugador, y los almacena en
     * un objeto DataStorage.
     */

    public void save() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("save.dat")));
            DataStorage ds = new DataStorage();
            ds.money = GameContext.getInstance().getPlayer().getMoney();
            ds.GameLevel = GameContext.getInstance().getGamelevel();
            ds.initialChoice = GameContext.getInstance().getInitialChoice();
            Map<String, Integer> itemCountMap = new HashMap<>();
            for (Buyable item : GameContext.getInstance().getPlayer().getItems()) {
                String itemName = item.getName();
                itemCountMap.put(itemName, itemCountMap.getOrDefault(itemName, 0) + 1);
            }
            ds.playerName = GameContext.getInstance().getPlayer().getName();
            ds.itemCounts.putAll(itemCountMap);
            ds.pokemons.addAll(GameContext.getInstance().getPlayer().getPokemons());
            oos.writeObject(ds);
            oos.close();
            System.out.println("You´ve saved the game");
        } catch (Exception e) {
            System.out.println("Couldn't save game");
        }
    }

}