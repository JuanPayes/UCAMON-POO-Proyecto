package Util;

import com.badlogic.gdx.tools.texturepacker.TexturePacker;

public class TexturePackedTool {

    public static void main(String[] args) {
        TexturePacker.process("resources/unpacked/","resources/packed/","textures");
    }
}
