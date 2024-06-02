package Util;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import entity.DIRECTION;

import java.util.HashMap;
import java.util.Map;

public class AnimationSet {

    private Map<DIRECTION, Animation> walking;
    private Map<DIRECTION, TextureRegion> standing;

    public AnimationSet(Animation walkNorth, Animation walkSouth, Animation walkEast, Animation walkWest, TextureRegion standingNorth, TextureRegion standingSouth, TextureRegion standingEast, TextureRegion standingWest) {
        walking = new HashMap<DIRECTION, Animation>();
        standing = new HashMap<DIRECTION, TextureRegion>();
        walking.put(DIRECTION.NORTH, walkNorth);
        walking.put(DIRECTION.SOUTH, walkSouth);
        walking.put(DIRECTION.EAST, walkEast);
        walking.put(DIRECTION.WEST, walkWest);
        standing.put(DIRECTION.NORTH, standingNorth);
        standing.put(DIRECTION.SOUTH, standingSouth);
        standing.put(DIRECTION.EAST, standingEast);
        standing.put(DIRECTION.WEST, standingWest);
    }

    public Animation getWalkingAnimation(DIRECTION dir) {
        return walking.get(dir);
    }

    public TextureRegion getStandingAnimation(DIRECTION dir) {
return standing.get(dir);
    }
}
