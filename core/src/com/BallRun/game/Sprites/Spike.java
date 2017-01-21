package com.BallRun.game.Sprites;

import com.badlogic.gdx.math.Rectangle;

/**
 * A spike. May add more functionality later.
 * Created by HeyJD on 20-01-17.
 */
public class Spike extends BasicScrollingSprite {
    public final int WIDTH = 128;
    public final int HEIGHT = 34;
    public final int SPIKE_COLLISION_MARGIN = 20;

    public Spike(float posX, float posY) {
        super(posX, posY, Assets.spike);
        setSize(WIDTH, HEIGHT);
    }

    @Override
    public Rectangle getBoundingRectangle() {
        //Custom collision checker
        //Makes it fit the visual size of the spikes
        Rectangle spikeCollision = super.getBoundingRectangle();
        spikeCollision.set(spikeCollision.getX()+SPIKE_COLLISION_MARGIN, spikeCollision.getY(), WIDTH-SPIKE_COLLISION_MARGIN, HEIGHT);
        return spikeCollision;
    }

}
