package entity;

public class TileMap {
    private int width, height;
    private Tile[][] tiles;//Tiles Matrix
    public TileMap(int width, int height){
        this.width = width;
        this.height =  height;
        tiles = new Tile[width][height];
        for(int i = 0; i < width;i++){
            for(int j = 0; j< height;j++){
                tiles[i][j] = new Tile();
            }
        }
    }

    public Tile getTile(int x, int y){
        return  tiles[x][y];
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
