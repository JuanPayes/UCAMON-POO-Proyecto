package entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TilePrueba {
    private Entity entity;
    private TextureRegion textureRegion;

    public TilePrueba(Texture texture) {
        this.textureRegion = new TextureRegion(texture);
    }

    public TilePrueba(TextureRegion textureRegion) {
        this.textureRegion = textureRegion;
    }

    public TextureRegion getTextureRegion() {
        return textureRegion;
    }

    public Texture getTexture() {
        return textureRegion.getTexture();
    }

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    public void setTexture(Texture texture) {
        this.textureRegion = new TextureRegion(texture);
    }

    public void setTextureRegion(TextureRegion textureRegion) {
        this.textureRegion = textureRegion;
    }
}
