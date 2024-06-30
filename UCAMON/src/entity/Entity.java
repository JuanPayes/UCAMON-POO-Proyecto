package entity;


import Util.AnimationSet;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;

public class Entity {

    protected int x;
    protected int y;
    private TileMap map;
    private DIRECTION facing;

    private float  worldX, worldY;
    private int srcX, srcY;
    private int destX, destY;
    private float animTimer;
    private float ANIM_TIME = 0.5f;

    private float walkTimer;
    private boolean moveRequestThisFrame;

    protected ACTORSTATE state;

    private AnimationSet animations;
    private TextureRegion staticTexture;

    public Entity() {
    }

    public Entity(TileMap map, int x, int y, AnimationSet animations) {
        this.map = map;
        this.x = x;
        this.y = y;
        this.worldX = x;
        this.worldY = y;
        this.animations = animations;
        map.getTile(x,y).setEntity(this);
        this.state = ACTORSTATE.STANDING;
        this.facing = DIRECTION.SOUTH;
    }

    public Entity(TileMap map,int x, int y, TextureRegion staticTexture) {
        this.map = map;
        this.x = x;
        this.y = y;
        this.worldX = x;
        this.worldY = y;
        this.staticTexture = staticTexture;
        this.state = ACTORSTATE.STANDING;
        this.facing = DIRECTION.SOUTH;
        map.getTile(x,y).setEntity(this);
    }

    public boolean move(DIRECTION dir){
        if (state == ACTORSTATE.WALKING) {
            if (facing == dir) {
                moveRequestThisFrame = true;
            }
            return false;
        }
        if(x+ dir.getDx() >= map.getWidth() || x + dir.getDx() < 0 || y + dir.getDy() >= map.getHeight()||y+ dir.getDy() <0){
            return false;
        }
        if(map.getTile(x+ dir.getDx(),y+ dir.getDy()).getEntity() != null){
            return false;
        }
        initializeMove(dir);
        map.getTile(x,y).setEntity(null);
        x+= dir.getDx();
        y+= dir.getDy();
        map.getTile(x,y).setEntity(this);
        return true;
    }

    private void initializeMove(DIRECTION dir){
        this.facing = dir;
        this.srcX = x;
        this.srcY = y;
        this.destX = x + dir.getDx();
        this.destY = y + dir.getDy();
        this.worldX = x;
        this.worldY = y;
        animTimer = 0f;
        state = ACTORSTATE.WALKING;
    }

    private void finishMove(){
        state = ACTORSTATE.STANDING;
        this.worldX = destX;
        this.worldY = destY;
        this.srcX = 0;
        this.srcY = 0;
        this.destX = 0;
        this.destY = 0;
    }

    public void update(float delta){
        if(state == ACTORSTATE.WALKING){
            animTimer += delta;
            walkTimer += delta;
            worldX = Interpolation.linear.apply(srcX, destX, animTimer/ANIM_TIME);
            worldY = Interpolation.linear.apply(srcY, destY, animTimer/ANIM_TIME);
            if(animTimer > ANIM_TIME){
                float leftOverTime = animTimer - ANIM_TIME;
                walkTimer -= leftOverTime;
                finishMove();
                if(moveRequestThisFrame){
                    move(facing);
                }else {
                    walkTimer = 0f;
                }
            }
        }
        moveRequestThisFrame = false;
    }

    public TextureRegion getSprite() {
        if (animations != null) {
            if (state == ACTORSTATE.WALKING) {
                return animations.getWalkingAnimation(facing).getKeyFrame(walkTimer, true);
            } else if (state == ACTORSTATE.STANDING) {
                return animations.getStandingAnimation(facing);
            }
            return animations.getStandingAnimation(DIRECTION.SOUTH);
        } else {
            return staticTexture;
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

    public ACTORSTATE getState() {
        return state;
    }

    public void setState(ACTORSTATE state) {
        this.state = state;
    }
}
