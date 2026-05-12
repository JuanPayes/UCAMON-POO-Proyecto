package Battle;

import Battle.Animations.BattleAnimation;
import Battle.Animations.BattleSprite;
import Main.Config;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class BattleRenderer {

    /* Size of half of a square around pokemon, defining local coords */
    private int squareSize = 100;

    private float playerSquareMiddleX = 0;
    private float playerSquareMiddleY = 0;
    private int opponentSquareMiddleX = 0;
    private int opponentSquareMiddleY = 0;

    private AssetManager assetManager;

    /* Shader to make nice mask effects */
    private ShaderProgram maskShader;

    /* Variable to store default shader while we use the mask shader */
    private ShaderProgram defaultShader;

    private TextureRegion background;
    private TextureRegion platform;

    private Texture playerPokemon;
    private Texture opponentPokemon;

    private Texture pokemonTexture;

    public BattleRenderer(AssetManager assetManager, ShaderProgram maskShader) {
        this.assetManager = assetManager;
        this.maskShader = maskShader;
        TextureAtlas atlas = assetManager.get("resources/graphics_packed/battle/battlepack.atlas", TextureAtlas.class);
        background = atlas.findRegion("background");
        platform = atlas.findRegion("platform");
        pokemonTexture = assetManager.get("resources/PokemonSprites/Bulbasaur.png", Texture.class);
    }

    public void render(SpriteBatch batch, BattleAnimation animation, BATTLE_PARTY primarilyAnimated) {
        // recalc the player's square's middle
        playerSquareMiddleX = Gdx.graphics.getWidth()/2 - squareSize;
        playerSquareMiddleY = Gdx.graphics.getHeight()/2;

        // recalc the opponent's square's middle
        opponentSquareMiddleX = Gdx.graphics.getWidth()/2 + squareSize;
        opponentSquareMiddleY = Gdx.graphics.getHeight()/2;

        // bottom edge of platforms
        float platformYOrigin = playerSquareMiddleY - platform.getRegionHeight()/2*Config.SCALE;

        float playerAlpha = 1f;
        float opponentAlpha = 1f;

        float playerWidth = 1f;
        float playerHeight = 1f;
        float opponentWidth = 1f;
        float opponentHeight = 1f;

        float playerX = 0f;
        float playerY = 0f;
        if (playerPokemon != null) {
            playerX = playerSquareMiddleX - playerPokemon.getWidth()/2*Config.SCALE;
            playerY = platformYOrigin;
        }

        float opponentX = 0f;
        float opponentY = 0f;
        if (opponentPokemon != null) {
            opponentX = opponentSquareMiddleX - opponentPokemon.getWidth()/2*Config.SCALE;
            opponentY = platformYOrigin;
        }

        if (animation != null) {
            if (primarilyAnimated == BATTLE_PARTY.PLAYER) {
                playerWidth = animation.getPrimaryWidth();
                playerHeight = animation.getPrimaryHeight();

                playerAlpha = animation.getPrimaryAlpha();
                opponentAlpha = animation.getSecondaryAlpha();

                playerX = playerSquareMiddleX - playerPokemon.getWidth()/2*Config.SCALE * playerWidth;
                playerY = platformYOrigin+playerPokemon.getHeight()/2*Config.SCALE - playerPokemon.getHeight()/2*Config.SCALE*playerHeight;

                playerX += animation.getPrimaryOffsetX() * squareSize;
                playerY += animation.getPrimaryOffsetY() * squareSize;

                opponentX -= animation.getSecondaryOffsetX() * squareSize;
                opponentY -= animation.getSecondaryOffsetY() * squareSize;

                opponentWidth = animation.getSecondaryWidth();
                opponentHeight = animation.getSecondaryHeight();
            } else if (primarilyAnimated == BATTLE_PARTY.OPPONENT) {
                opponentWidth = animation.getPrimaryWidth();
                opponentHeight = animation.getPrimaryHeight();

                playerAlpha = animation.getSecondaryAlpha();
                opponentAlpha = animation.getPrimaryAlpha();

                opponentX = opponentSquareMiddleX - opponentPokemon.getWidth()/2*Config.SCALE * opponentWidth;
                opponentY = platformYOrigin+opponentPokemon.getHeight()/2*Config.SCALE - opponentPokemon.getHeight()/2*Config.SCALE*opponentHeight;

                playerX += animation.getSecondaryOffsetX() * squareSize;
                playerY += animation.getSecondaryOffsetY() * squareSize;

                opponentX -= animation.getPrimaryOffsetX() * squareSize;
                opponentY += animation.getPrimaryOffsetY() * squareSize;

                playerWidth = animation.getSecondaryWidth();
                playerHeight = animation.getSecondaryHeight();
            }
        }

        /* render background */
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        /* render platforms */
        batch.draw(platform,
                playerSquareMiddleX-platform.getRegionWidth()/2*Config.SCALE,
                platformYOrigin,
                platform.getRegionWidth()*Config.SCALE,
                platform.getRegionHeight()*Config.SCALE);
        batch.draw(platform,
                opponentSquareMiddleX-platform.getRegionWidth()/2*Config.SCALE,
                platformYOrigin,
                platform.getRegionWidth()*Config.SCALE,
                platform.getRegionHeight()*Config.SCALE);

        /* render both pokemon looking at eachother */
        if (playerPokemon != null) {
            batch.setColor(1f, 1f, 1f, playerAlpha);
            if (animation != null && animation.getPrimaryMask() != null && !animation.isFinished()) {
                this.renderWithMask(
                        batch,
                        pokemonTexture,
                        animation.getPrimaryMask(),
                        animation.getPrimaryMaskAmount(),
                        playerX,
                        playerY,
                        playerWidth*playerPokemon.getWidth()*Config.SCALE,
                        playerHeight*playerPokemon.getHeight()*Config.SCALE,
                        true);
            } else {
                batch.draw(
                        playerPokemon,
                        playerX,
                        playerY,
                        playerWidth*playerPokemon.getWidth()*Config.SCALE,
                        playerHeight*playerPokemon.getHeight()*Config.SCALE,
                        0,
                        0,
                        playerPokemon.getWidth(),
                        playerPokemon.getHeight(),
                        true,
                        false);
            }
        }
        if (opponentPokemon != null) {
            batch.setColor(1f, 1f, 1f, opponentAlpha);
            batch.draw(
                    opponentPokemon,
                    opponentX,
                    opponentY,
                    opponentWidth*opponentPokemon.getWidth()*Config.SCALE,
                    opponentHeight*opponentPokemon.getHeight()*Config.SCALE,
                    0,
                    0,
                    opponentPokemon.getWidth(),
                    opponentPokemon.getHeight(),
                    false,
                    false);
        }

        batch.setColor(1f, 1f, 1f, 1f);

        /* render battle sprites */
        if (animation != null && !animation.isFinished()) {
            for (BattleSprite sprite : animation.getSprites()) {
                batch.setColor(1f, 1f, 1f, sprite.getAlpha());
                float spriteX = 0f;
                float spriteY = 0f;
                if (primarilyAnimated == BATTLE_PARTY.PLAYER) {
                    spriteX = playerSquareMiddleX+sprite.getX()*squareSize - sprite.getWidth()*sprite.getRegion().getRegionWidth()/2;
                    spriteY = playerSquareMiddleY+sprite.getY()*squareSize - sprite.getHeight()*sprite.getRegion().getRegionHeight()/2;
                } else if (primarilyAnimated == BATTLE_PARTY.OPPONENT) {
                    spriteX = opponentSquareMiddleX - sprite.getX()*squareSize - sprite.getWidth()*sprite.getRegion().getRegionWidth()/2;
                    spriteY = opponentSquareMiddleY + sprite.getY()*squareSize - sprite.getHeight()*sprite.getRegion().getRegionHeight()/2;
                }
                batch.draw(
                        sprite.getRegion(),
                        spriteX-sprite.getRegion().getRegionWidth()*sprite.getWidth()*Config.SCALE/2,
                        spriteY-sprite.getRegion().getRegionHeight()*sprite.getHeight()*Config.SCALE/2,
                        (sprite.getRegion().getRegionWidth()*sprite.getWidth()*Config.SCALE)/2,
                        (sprite.getRegion().getRegionHeight()*sprite.getHeight()*Config.SCALE)/2,
                        Config.SCALE*sprite.getWidth()*sprite.getRegion().getRegionWidth(),
                        Config.SCALE*sprite.getHeight()*sprite.getRegion().getRegionHeight(),
                        1f,
                        1f,
                        sprite.getRotation());
                batch.setColor(1f, 1f, 1f, 1f);
            }
        }
    }
    

    

    public void setPokemonSprite(Texture region, BATTLE_PARTY turn) {
        if (turn == BATTLE_PARTY.PLAYER) {
            playerPokemon = region;
        } else if (turn == BATTLE_PARTY.OPPONENT) {
            opponentPokemon = region;
        }
    }

    private void renderWithMask(SpriteBatch batch, Texture texture, Texture mask, float maskAmount, float x, float y, float width, float height, boolean flipX) {
        defaultShader = batch.getShader();
        batch.setShader(maskShader);

        /* bind status effect to GL_TEXTURE1 */
        mask.bind(1);

        /* make sure the active texture is 0 */
        Gdx.graphics.getGL20().glActiveTexture(GL20.GL_TEXTURE0);

        maskShader.setUniformi("u_mask", 1); // what texture to mask
        maskShader.setUniformf("u_mix", maskAmount); // how much to mask the texture

        batch.draw(texture,
                x,
                y,
                width,
                height,
                0,
                0,
                texture.getWidth(),
                texture.getHeight(),
                flipX,
                false);

        batch.setShader(defaultShader);
    }

    public int squareSize() {
        return squareSize;
    }

    public float playerSquareMiddleX() {
        return playerSquareMiddleX;
    }

    public float playerSquareMiddleY() {
        return playerSquareMiddleY;
    }

    public int opponentSquareMiddleX() {
        return opponentSquareMiddleX;
    }

    public int opponentSquareMiddleY() {
        return opponentSquareMiddleY;
    }
}
