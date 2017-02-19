package com.BallRun.game.Sprites;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Pool;

/**
 * Constructs a random environment sprite
 * Created by HeyJD on 21-01-17.
 */
public class EnvironmentSprite extends BasicScrollingSprite implements Pool.Poolable{
    public enum Sprite { GRASS, BUSH, CACTUS }


    public EnvironmentSprite(float posX, float posY) {
        super(posX, posY);
        setRegion(getRandomSprite());
    }

    private TextureRegion getRandomSprite() {
        TextureRegion returnTexture = null;
        int NumberOfChoices = Sprite.values().length;
        int rand = (int)Math.floor(Math.random()*NumberOfChoices);
        Sprite chosenValue = Sprite.values()[rand];

        switch(chosenValue) {
            case GRASS:
                returnTexture = Assets.grass;
                break;
            case BUSH:
                returnTexture = Assets.bush;
                break;
            case CACTUS:
                returnTexture = Assets.cactus;
                break;
        }
        return returnTexture;
    }

    public void reset() {
        setRegion(getRandomSprite());
    }
}
