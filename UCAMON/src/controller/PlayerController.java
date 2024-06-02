package controller;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import entity.DIRECTION;
import entity.Entity;
import com.badlogic.gdx.utils.Array;

public class PlayerController extends InputAdapter {
    private Entity player;
    private boolean up, down, left, right;


    public PlayerController(Entity pl){
        this.player = pl;

    }


    @Override
    public boolean keyDown(int keycode){
        if (keycode == Input.Keys.W){
            up = true;
        }
        if (keycode == Input.Keys.A){
            left = true;
        }
        if (keycode == Input.Keys.S){
            down = true;
        }
        if (keycode == Input.Keys.D){
            right = true;
        }
        return false;
    }

    public boolean keyUp(int keycode){
        if (keycode == Input.Keys.W){
            up = false;
        }
        if (keycode == Input.Keys.A){
            left = false;
        }
        if (keycode == Input.Keys.S){
            down = false;
        }
        if (keycode == Input.Keys.D){
            right = false;
        }

        return false;
    }

    public void update(float delta){

        if (up) {
            player.move(DIRECTION.NORTH);
            return;
        }
        if (down) {
            player.move(DIRECTION.SOUTH);
            return;
        }
        if (left) {
            player.move(DIRECTION.WEST);
            return;
        }
        if (right) {
            player.move(DIRECTION.EAST);
            return;
        }
    }
}


