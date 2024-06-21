package main;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;

public class Main {
    public static void main(String[] args) {
        TexturePacker.process("resources/unpacked/","resources/packed/","textures");
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "UCAMON";
        config.height = 400;
        config.width = 600;
        config.vSyncEnabled = true;

        new LwjglApplication(new Pokemon(), config);
    }
}