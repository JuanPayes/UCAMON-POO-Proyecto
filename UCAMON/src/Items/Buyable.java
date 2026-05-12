package Items;

import Entities.Player;
import GameState.GameContext;
import PokemonFactory.Pokemon.Pokemon;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * La clase abstracta Buyable representa un item que se puede comprar en el juego.
 * Esta clase proporciona métodos para manejar la compra, el uso y la visualización de estos elementos.
 * La clase implementa la interfaz Serializable para permitir la serialización de los datos.
 */

public abstract class Buyable implements Serializable {
    /**
     * Nombre del elemento.
     */
    private String name;

    /**
     * Precio del item.
     */
    private int price;

    /**
     * Cantidad total de items.
     */
    protected static int itemAmount = 0;

    public Buyable(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public Buyable() {
    }

    public int getItemAmount() {
        return itemAmount;
    }

    public void setItemAmount(int itemAmount) {
        this.itemAmount = itemAmount;
    }

    /**
     * Representación de cadena del item.
     *
     * @return La representación de cadena del item concatenado con el precio.
     */

    public String toString() {
        return name + ";" + price;
    }


    public String getName() {
        return "";
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price =price;
    }

    /**
     * Método abstracto para usar el item en un Pokemón enemigo y en un Pokemón del jugador.
     *
     * @param enemy   El Pokemón enemigo.
     * @param pokemon El Pokemón del jugador.
     */
    public abstract boolean use(Pokemon enemy, Pokemon pokemon);



    /**
     * Cuenta la cantidad de cada item en una lista de items.
     *
     * @param items La lista de item.
     * @return Una lista de objetos ItemCount que contiene el nombre y la cantidad de cada item.
     */

    public static int countItems(String name,ArrayList<Buyable> items) {
        int count = 0;
        for (Buyable item : items) {
            if(item.getName().equals(name)) {
                count++;
            }
        }

        return count;
    }

    /**
     * Elimina un item de la lista de items del jugador.
     *
     * @param name El nombre del item a eliminar.
     */

    public static void removeItem(String name){
        for(int i = 0 ; i < GameContext.getInstance().getPlayer().getItems().size(); i++){
            if(GameContext.getInstance().getPlayer().getItems().get(i).getName()  == name){
                GameContext.getInstance().getPlayer().getItems().remove(i);
            }
        }
    }

    /**
     * Obtiene un item de la lista de items del jugador por su nombre.
     *
     * @param name El nombre del item a obtener.
     * @return El item correspondiente al nombre especificado, o null si no se encuentra.
     */
    public static Buyable getItem(String name) {
        for (Buyable i : GameContext.getInstance().getPlayer().getItems()) {
            if (i.getName().equals(name)) {
                return i;
            }
        }
        return null;
    }

    public static boolean hasItem(String name){
        Buyable item = null;
        for (Buyable i : GameContext.getInstance().getPlayer().getItems()) {
            if (i.getName().equals(name)) {
                item = i;
            }
        }
        return item != null;
    }
}