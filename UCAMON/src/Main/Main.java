package Main;
import GameState.*;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;

public class Main { public static void main(String[] args) {
    GameContext.getInstance().getPlayer().reset();
    TexturePacker.process("resources/unpacked/","resources/packed/","textures");
    LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
    config.title = "UCAMON";
    config.height = 400;
    config.width = 600;
    config.vSyncEnabled = true;
    GameApp app = new GameApp();

    try{
        new LwjglApplication(app, config);

    }catch (Exception e){
        e.printStackTrace();
    }
        GameContext.getInstance().setGame(app);
        GameContext.getInstance().start();

    }
}
