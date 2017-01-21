package com.BallRun.game.Sprites;

import com.BallRun.game.Main;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Makes a cloud at a random position
 * with a random cloud texture and a random size
 * going at a random speed
 * being drawn with a random alpha
 *
 * Created by HeyJD on 8/06/2015.
 */
public class Cloud extends BasicScrollingSprite {
    float speed;
    float alpha;

    public Cloud() {
        super(Main.CAMERA_WIDTH, Main.CAMERA_HEIGHT);
        setPosition(Main.CAMERA_WIDTH, getRandomPosition());
        setRegion(getRandomCloud());
        setSize(400+(float)(Math.random()*80f),222+(float)(Math.random()*60f));
        speed = (float)Math.random()*1.2f+0.5f;
        alpha = (float)Math.random()*0.9f+0.3f;
    }

    private TextureRegion getRandomCloud() {
        int rand = (int)Math.floor(Math.random()*3+1);
        TextureRegion returnTexture = null;
        switch(rand) {
            case 1:
                returnTexture = Assets.cloud1;
                break;
            case 2:
                returnTexture = Assets.cloud2;
                break;
            case 3:
                returnTexture = Assets.cloud3;
                break;
        }
        return returnTexture;
    }

    private float getRandomPosition() {
        return (float)Math.random()*500+500;
    }

    @Override
    public void updateMotion(float deltaTime) {
        super.updateMotion(deltaTime*speed);
    }

    @Override
    public void draw(SpriteBatch batch, float drawAlpha) {
        super.draw(batch, drawAlpha*alpha);
    }
}
