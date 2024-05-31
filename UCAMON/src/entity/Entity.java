package entity;


import com.badlogic.gdx.math.Interpolation;

public class Entity {

    private int x;
    private int y;
    private TileMap map;

    private float  worldX, worldY;
    private int srcX, srcY;
    private int destX, destY;
    private float animTimer;
    private float ANIM_TIME = 0.5f;

    private ACTOR_STATE state;

    public enum ACTOR_STATE {
        WALKING,
        STANDING,
        ;
    }

    public Entity() {

    }
    public Entity(TileMap map,int x, int y) {
        this.map = map;
        this.x = x;
        this.y = y;
        this.worldX = x;
        this.worldY = y;
        map.getTile(x,y).setEntity(this);
        this.state = ACTOR_STATE.WALKING;
    }

    public boolean move(int xcor,int ycor){
        if (state != ACTOR_STATE.STANDING) {
            return false;
        }
        if(x+xcor >= map.getWidth() || x + xcor < 0 || y +ycor >= map.getHeight()||y+ycor<0 ){
            return false;
        }
        if(map.getTile(x+xcor,y+ycor).getEntity() != null){
            return false;
        }
        initializeMove(x, y, xcor, ycor);
        map.getTile(x,y).setEntity(null);
        x+=xcor;
        y+=ycor;
        map.getTile(x,y).setEntity(this);
        return true;
    }

    private void initializeMove(int oldX, int oldY, int dx, int dy){
        this.srcX = oldX;
        this.srcY = oldY;
        this.destX = oldX + dx;
        this.destY = oldY + dy;
        this.worldX = oldX;
        this.worldY = oldY;
        animTimer = 0f;
        state = ACTOR_STATE.WALKING;
    }

    private void finishMove(){
        state = ACTOR_STATE.STANDING;
    }

    public void update(float delta){
        if(state == ACTOR_STATE.WALKING){
            animTimer += delta;
            worldX = Interpolation.pow2.apply(srcX, destX, animTimer/ANIM_TIME);
            worldY = Interpolation.pow2.apply(srcY, destY, animTimer/ANIM_TIME);
            if(animTimer > ANIM_TIME){
                finishMove();
            }
        }
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

    public float getWorldX() {
        return worldX;
    }

    public float getWorldY(){
        return worldY;
    }
}
