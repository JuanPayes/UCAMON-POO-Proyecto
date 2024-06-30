package Rooms;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import entity.Entity;
import entity.TileMap;

import java.util.List;

public class Floor1 extends Room{

    Texture floorTexture;
    TileMap floor;

    public Floor1(List<Entity> entities) {
        super(null, entities);
    }

    @Override
    public void initialize() {
        floorTexture = new Texture("resources/Tiles/Floors/LibFloor.png");

        floor = new TileMap(15, 15, floorTexture);

        for (int x = 0; x < floor.getWidth(); x++) {
            for (int y = 0; y < floor.getHeight(); y++) {
                floor.setTile(x, y, new TextureRegion(floorTexture));
            }
        }
        this.map = floor;
    }

    @Override
    public String getId() {
        return "Floor1";
    }
}
