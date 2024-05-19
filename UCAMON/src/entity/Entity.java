package entity;

public class Entity {

    private int x;
    private int y;
    private TileMap map;
    public Entity() {

    }
    public Entity(TileMap map,int x, int y) {
        this.map = map;
        this.x = x;
        this.y = y;
        map.getTile(x,y).setEntity(this);
    }

    public boolean move(int xcor,int ycor){
        if(x+xcor >= map.getWidth() || x + xcor < 0 || y +ycor >= map.getHeight()||y+ycor<0 ){
            return false;
        }
        if(map.getTile(x+xcor,y+ycor).getEntity() != null){
            return false;
        }
        map.getTile(x,y).setEntity(null);
        x+=xcor;
        y+=ycor;
        map.getTile(x,y).setEntity(this);
        return true;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
