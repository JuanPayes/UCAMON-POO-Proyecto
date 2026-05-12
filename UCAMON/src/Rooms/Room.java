package Rooms;

import Entities.RenderableEntity;
import Tiles.TileMap;
import java.util.List;

public abstract class Room {
    protected TileMap map;
    protected List<RenderableEntity> entities;

    public Room(TileMap map, List<RenderableEntity> entities) {
        this.map = map;
        this.entities = entities;
    }

    public abstract void initialize();

    public TileMap getMap() {
        return map;
    }

    public List<RenderableEntity> getEntities() {
        return entities;
    }

    public abstract String getID();
}
