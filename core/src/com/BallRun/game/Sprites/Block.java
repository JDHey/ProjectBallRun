package com.BallRun.game.Sprites;

/**
 * Makes a basic Block
 * Created by HeyJD on 9/04/2015.
 */
public class Block extends BasicScrollingSprite {
    public static final float DEFAULT_WIDTH = 128;
    public static final float DEFAULT_HEIGHT = 128;

    public Block(float posX, float posY) {
        super(posX, posY, Assets.block);
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

}
