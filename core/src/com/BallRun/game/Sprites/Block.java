package com.BallRun.game.Sprites;

import com.badlogic.gdx.utils.Pool;

/**
 * Makes a basic Block
 * Created by HeyJD on 9/04/2015.
 */
public class Block extends BasicScrollingSprite implements Pool.Poolable{
    public static final float DEFAULT_WIDTH = 128;
    public static final float DEFAULT_HEIGHT = 128;

    public Block(float posX, float posY) {
        super(posX, posY, Assets.block);
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    /**
     * Implemented from Poolable, called when freed from pool
     */
    public void reset() {
        setRegion(Assets.block);
    }

}
