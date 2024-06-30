package Rooms;

import entity.Entity;
import entity.TileMap;
import java.util.List;

public abstract class Room {
    protected TileMap map;
    protected List<Entity> entities;

    public Room(TileMap map, List<Entity> entities) {
        this.map = map;
        this.entities = entities;
    }

    public abstract void initialize();

    public TileMap getMap() {
        return map;
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public abstract String getId();
}
