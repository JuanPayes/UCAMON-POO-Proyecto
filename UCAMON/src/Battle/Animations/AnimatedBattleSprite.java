package Battle.Animations;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AnimatedBattleSprite extends BattleSprite {
    private Animation animation;

    private long startTime;

    /**
     * AnimatedBattleSprite contructor
     */
    public AnimatedBattleSprite(Animation animation, float x, float y, float width, float height, float delay) {
        super(animation.getKeyFrames()[0], x, y, width, height);
        this.animation = animation;
        this.startTime = System.currentTimeMillis()+(long)(delay*1000l);
    }

    /**
     * sets AnimationMode to play mode
     */
    public void setAnimationMode(Animation.PlayMode mode) {
        this.animation.setPlayMode(mode);
    }

    /**
     * Abstract method from battleSprite to get the region where pokemon appears
     */
    @Override
    public TextureRegion getRegion() {
        if (startTime > System.currentTimeMillis()) { // not yet
            return super.getRegion(); // return first frame
        } else { // it's time babyyy
            float stateTime = ((System.currentTimeMillis()-startTime)/1000f);
            return animation.getKeyFrame(stateTime);
        }
    }
}

