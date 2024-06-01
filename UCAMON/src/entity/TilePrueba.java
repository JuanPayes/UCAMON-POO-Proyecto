package entity;

import com.badlogic.gdx.graphics.Texture;

public class TilePrueba {
    private Entity entity;
    private Texture texture;
    private TERRAIN terrain;

    public TilePrueba(Texture texture, TERRAIN terrain){
        this.texture = texture;
        this.terrain = terrain;
    }

    public Texture getTexture() {
        return texture;
    }

    public TERRAIN getTerrain(){
        return terrain;
    }
    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }
}
