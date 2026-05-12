package Main;

import Battle.Animations.*;
import Screens.InitialScreen;
import Util.SkinGenerator;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import java.io.File;

public  class GameApp extends Game{
    private AssetManager assetManager;
    private TweenManager tweenManager;
    private ShaderProgram overlayShader;
    private ShaderProgram transitionShader;

    private Skin skin;
    public void create() {

        assetManager = new AssetManager();
        assetManager.load("resources/packed/textures.atlas", TextureAtlas.class);



        assetManager.finishLoading();
        //skin = SkinGenerator.generateSkin(assetManager);
        setScreen(new InitialScreen(this));



        tweenManager = new TweenManager();
        Tween.registerAccessor(BattleAnimation.class, new BattleAnimationAccesor());
        Tween.registerAccessor(BattleSprite.class, new BattleSpriteAccesor());
        Tween.registerAccessor(AnimatedBattleSprite.class, new BattleSpriteAccesor());
       // Tween.registerAccessor(BattleBlinkTransition.class, new BattleBlinkTransitionAccessor());

    }
    public void render() {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        super.render();
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    public ShaderProgram getOverlayShader() {
        return overlayShader;
    }

    public ShaderProgram getTransitionShader() {
        return transitionShader;
    }

    public Skin getSkin() {
        return skin;
    }
}
