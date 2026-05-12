package Tiles;

import Entities.Entity;
import Entities.RenderableEntity;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Tile {
    private RenderableEntity entity;
    private TextureRegion textureRegion;

    public Tile(Texture texture) {
        this.textureRegion = new TextureRegion(texture);
    }

    public Tile(TextureRegion textureRegion) {
        this.textureRegion = textureRegion;
    }

    public TextureRegion getTextureRegion() {
        return textureRegion;
    }

    public Texture getTexture() {
        return textureRegion.getTexture();
    }

    public RenderableEntity getEntity() {
        return entity;
    }

    public void setEntity(RenderableEntity entity) {
        this.entity = entity;
    }

    public void setTexture(Texture texture) {
        this.textureRegion = new TextureRegion(texture);
    }

    public void setTextureRegion(TextureRegion textureRegion) {
        this.textureRegion = textureRegion;
    }
}
