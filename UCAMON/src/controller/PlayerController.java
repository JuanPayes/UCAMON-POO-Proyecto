package controller;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import entity.Entity;
import com.badlogic.gdx.utils.Array;

public class PlayerController extends InputAdapter {
    private Entity player;
    private Array<Integer> keyPressed;
    public PlayerController(Entity pl){
        this.player = pl;
        this.keyPressed = new Array<>();
    }
    @Override
    public boolean keyDown(int keycode){
        if (!keyPressed.contains(keycode, true)){
            keyPressed.add(keycode);
        }
        return true;
    }

    public boolean keyUp(int keycode){
        keyPressed.removeValue(keycode, true);
        return true;
    }

    public void update(float delta){
        for(int keycode : keyPressed){
            if (keycode == Input.Keys.W){
               player.move(0,1);
            }
            if (keycode == Input.Keys.S){
                player.move(0,-1);
            }
            if (keycode == Input.Keys.D){
                player.move(1,0);
            }
            if (keycode == Input.Keys.A){
                player.move(-1,0);
            }
        }
    }
}
