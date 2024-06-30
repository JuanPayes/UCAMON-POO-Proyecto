package Rooms;

import entity.Entity;

import java.util.ArrayList;
import java.util.List;

public class RoomPosition {
    private Room room;
    private int x;
    private int y;
    private List<Entity> entities;

    public RoomPosition(Room room, int x, int y, List<Entity> entities) {
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

    public List<Entity> getEntities() {
        return entities;
    }
}
