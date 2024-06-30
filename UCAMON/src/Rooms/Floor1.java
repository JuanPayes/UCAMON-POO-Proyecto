package Rooms;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import entity.Entity;
import entity.TileMap;

import java.util.ArrayList;
import java.util.List;

public class Floor1 extends Room{

    Texture floorTexture;
    List<TextureRegion> stairs;


    TileMap floor;

    public Floor1(List<Entity> entities) {
        super(null, entities);
    }

    @Override
    public void initialize() {
        floorTexture = new Texture("resources/Tiles/Floors/LibFloor.png");

        stairs = new ArrayList<TextureRegion>();
        for (int i = 0; i < 6; i++) {
            stairs.add(new TextureRegion(new Texture("resources/Tiles/Floors/Stairs/stairway_"+i+".png")));
        }

        floor = new TileMap(15, 15, floorTexture);

        for (int x = 0; x < floor.getWidth(); x++) {
            for (int y = 0; y < floor.getHeight(); y++) {
                floor.setTile(x, y, new TextureRegion(floorTexture));
            }
        }
        this.map = floor;

        addStairs(map, 13, 14);


    }

    @Override
    public String getId() {
        return "Floor1";
    }

    public void addStairs(TileMap map, int startX, int startY) {
        int stairsLayout[][] = {
                {0, 1},
                {2, 3},
                {4, 5}
        };

        for (int i = 0; i < stairsLayout.length; i++) {
            for (int j = 0; j < stairsLayout[i].length; j++) {
                int tileIndex = stairsLayout[i][j];
                if (tileIndex >= 0) {
                    int x = startX + j;
                    int y = startY - i;
                    addTile(map, x, y, stairs.get(tileIndex));
                }
            }
        }
    }


    private void addTile(TileMap map, int x, int y, TextureRegion tile) {
        Entity buildingTile = new Entity(map, x, y, tile);
        entities.add(buildingTile);
        map.getTile(x, y).setEntity(buildingTile);
    }

}
