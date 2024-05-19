package controller;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import entity.Entity;

public class PlayerController extends InputAdapter {
    private Entity player;
    public PlayerController(Entity pl){
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
