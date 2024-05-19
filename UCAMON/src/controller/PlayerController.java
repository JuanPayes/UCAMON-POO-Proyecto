package controller;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import entity.Player;

public class PlayerController extends InputAdapter {
    private Player player;
    public PlayerController(Player pl){
        this.player = pl;
    }
    @Override
    public boolean keyDown(int keycode){
        if(keycode == Input.Keys.W){
            player.move(0,1);
        }
        if(keycode == Input.Keys.S){
            player.move(0,-1);
        }
        if(keycode == Input.Keys.A){
            player.move(-1,0);
        }
        if(keycode == Input.Keys.D){
            player.move(1,0);
        }
        return false;
    }
}
