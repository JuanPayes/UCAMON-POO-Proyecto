package Entities;

import GameState.GameContext;
import Items.Buyable;
import PokemonFactory.Pokemon.Pokemon;


import java.util.ArrayList;

/**
 * La clase Player representa al jugador en el juego.
 * El jugador puede tener una serie de Pokémon, items y dinero.
 */
public class Player extends Entity {
    /**
     * Lista de artículos que posee el jugador
     */
    protected ArrayList<Buyable> items;

    /**
     *Dinero que posee el jugador
     */
    private int money;

    /**
     * Artículo que el jugador está intentando comprar actualmente
     */
    protected Buyable buyable;

    public Player() {

    }

    public Buyable getBuyable() {
        return buyable;
    }

    public void setBuyable(Buyable buyable) {
        this.buyable = buyable;
    }


    /**
     * Reinicia los atributos del jugador.
     * Restablece el dinero a 100, vacía la lista de artículos y la lista de Pokémon.
     */
    public void reset() {
        this.money = 100;
        this.items = new ArrayList<>();
        this.pokemons = new ArrayList<>();
    }

    public Player(String name, ArrayList<Pokemon> pokemons, int xposition, int YPosition, ArrayList<Buyable> items, int money, Buyable buyable) {
        super(name, pokemons, xposition, YPosition);
        this.items = items;
        this.money = money;
        this.buyable = buyable;
    }

    public ArrayList<Buyable> getItems() {
        return items;
    }

    public void setItems(ArrayList<Buyable> items) {
        this.items = items;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    /**
     * Método para que el jugador se mueva en el juego.
     * Este método será utilizado cuand se implemente la interfaz gráfica.
     */
    public void move(){}


}