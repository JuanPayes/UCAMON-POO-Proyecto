package Rooms;

import Entities.RenderableEntity;

import java.util.List;

public class RoomPosition {
    private Room room;
    private int x;
    private int y;
    private List<RenderableEntity> entities;

    public RoomPosition(Room room, int x, int y, List<RenderableEntity> entities) {
        this.room = room;
        this.x = x;
        this.y = y;
        this.entities = entities;
    }

    public Room getRoom() {
        return room;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public List<RenderableEntity> getEntities() {
        return entities;
    }
}
