package Battle;

import Battle.Animations.BattleAnimation;
import aurelienribon.tweenengine.TweenManager;
import com.badlogic.gdx.graphics.Texture;

public interface BattleEventPlayer {
    void playBattleAnimation(BattleAnimation animation, BATTLE_PARTY party);

    void setPokemonSprite(Texture region, BATTLE_PARTY party);

    DialogueBox getDialogueBox();

    public StatusBox getStatusBox(BATTLE_PARTY party);

    public BattleAnimation getBattleAnimation();

    public TweenManager getTweenManager();

    public void queueEvent(BattleEvent event);
}
