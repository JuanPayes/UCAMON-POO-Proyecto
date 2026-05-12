package PokemonFactory.Pokemon;

import PokemonFactory.Pokemon.Attack.Attack;
import PokemonFactory.Pokemon.Attack.FireBlast;
import com.badlogic.gdx.graphics.Texture;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Clase Pokemon representa a un pokemon en el juego el cual cuenta con sus atributos relacionados y lista de ataques.
 */
public abstract class Pokemon implements Serializable {
    /**
     * El nombre del pokemon.
     */
    private String name;
    /**
     * La vida del pokemon.
     */
    private int hp;
    /**
     * La vida maxima del pokemon.
     */
    private int hpMax;
    /**
     * La experiencia del pokemon.
     */
    private int xp;
    private int level;
    /**
     * El nivel del pokemon.
     */
    private int speed;
    /**
     * El tipo de pokemon.
     */
    private POKEMONTYPE pokemontype;
    /**
     * La coleccion de ataque que tiene el pokemon.
     */
    private ArrayList<Attack> attacks = new ArrayList<>();

    /**
     * El sprite que tendra el pokemon en la mochila y en el combate
     */
    private Texture sprite;

    private Texture spriteOP;
    

    public Pokemon(String name, int hp, int level ,int hpMax, int xp,int speed, POKEMONTYPE pokemontype,ArrayList<Attack> attacks, Texture sprite, Texture spriteOP){
        this.name = name;
        this.hp = hp;
        this.level = level;
        this.hpMax =hpMax;
        this.xp = xp;
        this.pokemontype = pokemontype;
        this.speed=speed;
        this.attacks = attacks;
        this.sprite = sprite;
        this.spriteOP = spriteOP;
    }



    public Pokemon() {
    }
    public int getHpMax() {
      return  hpMax;
    }

    public void setHpMax(int hpMax) {
        this.hpMax = hpMax;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        if (hp < 0) {
            this.hp= 0;
        } else {
            this.hp = hp;
        }
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public POKEMONTYPE getPokemontype() {
        return pokemontype;
    }

    public void setPokemontype(POKEMONTYPE pokemontype) {
        this.pokemontype = pokemontype;
    }

    public int getSpeed() {
            return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public ArrayList<Attack> getAttacks() {
        return attacks;
    }

    public void setAttacks(ArrayList<Attack> attacks) {
        this.attacks = attacks;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setSprite(Texture sprite){
        this.sprite = sprite;
    }

    public Texture getSprite(){
        return sprite;
    }

    public void setSpriteOP(Texture spriteOP){
        this.spriteOP = spriteOP;
    }

    public Texture getSpriteOP(){
        return spriteOP;
    }

    public boolean isFainted(){
        return hp <= 0;
    }

    public void gainXp(int xpGained) {
        this.xp += xpGained;
        checkLevelUp();
    }

    public void checkLevelUp() {
        double requiredXp = Math.ceil(Math.sinh(Math.sqrt(level)));
        while (xp >= requiredXp) {
            xp -= requiredXp;
            levelUp();
            requiredXp = Math.ceil(Math.sinh(Math.sqrt(level))); // Recalculate required XP for the new level
        }

    }

    private void levelUp() {
        level++;
       // Reset XP after leveling up
        hpMax = hpMax + (int)( hpMax*0.1); // Increase max HP
        // = maxHp; // Restore HP to full on level up
        speed   =  speed + (int)(speed*0.05); // Increase speed

        System.out.println(name + " leveled up to level " + level + "!");
        System.out.println("New stats | HpMax: " + hpMax + " | Speed: " + speed);
    }
}
