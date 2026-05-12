package Battle.Animations;

import Battle.BATTLE_PARTY;
import Battle.BattleEvent;
import Battle.BattleEventPlayer;

public class AnimationBattleEvent extends BattleEvent {
    private BATTLE_PARTY primary;
    private BattleAnimation animation;

    /**
     * Animation event contructor
     */
    public AnimationBattleEvent(BATTLE_PARTY primary, BattleAnimation animation) {
        this.animation = animation;
        this.primary = primary;
    }

    /**
     * begins animation
     */
    @Override
    public void begin(BattleEventPlayer player) {
        super.begin(player);
        player.playBattleAnimation(animation, primary);
    }

    /**
     * updates the animation
     */
    @Override
    public void update(float delta) {
        animation.update(delta);
    }

    /**
     * Finishes the animation
     * @return
     */
    @Override
    public boolean finished() {
        return this.getPlayer().getBattleAnimation().isFinished();
    }

}
