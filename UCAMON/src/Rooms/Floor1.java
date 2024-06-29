package Rooms;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import entity.Entity;
import entity.TileMap;

import java.util.List;

public class Floor1 extends Room{

    Texture floorTexture;

    public Floor1(TileMap map, List<Entity> entities) {
        super(map, entities);
    }

    @Override
    public void initialize() {
        floorTexture = new Texture("resoureces/Tiles/Floors/LibFloor.png");

        for (int x = 0; x < map.getWidth(); x++) {
            for (int y = 0; y < map.getHeight(); y++) {
                map.setTile(x, y, new TextureRegion(floorTexture));
            }
        }
    }
}
