package com.BallRun.game.Sprites;

/**
 * Created by HeyJD on 01-03-17.
 */
public class Water extends BasicScrollingSprite {
    public Water(float posX, float posY) {
        super(posX, posY, Assets.water);
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    @Override
    public void updateMotion(float deltaTime) {
        super.updateMotion(deltaTime);

    }
}
