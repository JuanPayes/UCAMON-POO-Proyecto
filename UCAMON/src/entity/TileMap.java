package entity;

import com.badlogic.gdx.graphics.Texture;

public class TileMap {
    private int width, height;
    private TilePrueba[][] tiles;//Tiles Matrix

    public TileMap(int width, int height, Texture grassTexture, Texture brownTexture){
        this.width = width;
        this.height =  height;
        tiles = new TilePrueba[width][height];

        for(int i = 0; i < width;i++){
            for(int j = 0; j< height;j++){
                if (i == width / 2 && j == height / 2){
                    tiles[i][j] = new TilePrueba(brownTexture, TERRAIN.BROWN_GRASS);
                }else{
                    tiles[i][j] = new TilePrueba(grassTexture, TERRAIN.GRASS_1);
                }
            }
        }
    }

    public TilePrueba getTile(int x, int y){
        return  tiles[x][y];
    }

    public int getWidth() {
        return width;
    }


    public int getHeight() {
        return height;
    }

}
