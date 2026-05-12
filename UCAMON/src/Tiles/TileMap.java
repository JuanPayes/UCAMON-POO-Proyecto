package Tiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TileMap {
    private int width, height;
    private Tile[][] tiles;//Tiles Matrix

    public TileMap(int width, int height, Texture defaultTexture){
        this.width = width;
        this.height =  height;
        tiles = new Tile[width][height];

        TextureRegion defaultTextureRegion = new TextureRegion(defaultTexture);

        for(int x = 0; x < width; x++){
            for(int y = 0; y < height; y++){
                tiles[x][y] = new Tile(defaultTextureRegion);
            }
        }
    }

    public void setTile(int x, int y, TextureRegion textureRegion){
        tiles[x][y] = new Tile(textureRegion);
    }

    public void setTile(int x, int y, Texture texture){
        if (x >= 0 && x < width && y >= 0 && y < height){
            tiles[x][y] = new Tile(new TextureRegion(texture));
        }
    }

    public Tile getTile(int x, int y){
        return  tiles[x][y];
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {return height;}

}
