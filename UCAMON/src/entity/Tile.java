package entity;

public class Tile {
    private TERRAIN terrain;
    private Entity entity;
    public Tile(){}
    public Tile(TERRAIN terrain) {
        this.terrain = terrain;
    }

    public TERRAIN getTerrain() {
        return terrain;
    }

    public void setTerrain(TERRAIN terrain) {
        this.terrain = terrain;
    }

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }
}
