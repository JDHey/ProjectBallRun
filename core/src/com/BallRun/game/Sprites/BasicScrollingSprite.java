package com.BallRun.game.Sprites;

import com.BallRun.game.GameController;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

/**
 * A sprite that scrolls left and has basic functionality
 * Created by HeyJD on 20-01-17.
 */
public class BasicScrollingSprite {
    public static final float DEFAULT_WIDTH = 128;
    public static final float DEFAULT_HEIGHT = 128;

    private Sprite sprite;

    public BasicScrollingSprite(float posX, float posY) {
        this(posX, posY, Assets.block);
    }

    public BasicScrollingSprite(float posX, float posY, TextureRegion tr) {
        sprite = new Sprite(tr);
        sprite.setPosition(posX, posY);
        //sprite.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    public void updateMotion(float deltaTime) {
        sprite.translateX(GameController.BLOCK_GRAVITY.x * deltaTime);
    }

    public void draw(SpriteBatch batch, float alpha) {
        sprite.draw(batch, alpha);
    }

    public Rectangle getBoundingRectangle() {
        return sprite.getBoundingRectangle();
    }

    /**
     *  Checks collision with ball
     *  Uses AABB Intersection algorithm
     *  For every axis {
     *      if a.max < b.min or if a.min > b.max
     *      return false
     *      }
     *      return true if passed the test
     *
     *  EDIT: Changed it to use a bounding box
     */
    public boolean checkCollision(Ball ball) {
        /*
        if (ball.getX() > this.getX()+DEFAULT_WIDTH || ball.getX()+DEFAULT_WIDTH < this.getX()) {
            return false;
        } else if (ball.getY() > this.getY()+DEFAULT_HEIGHT || ball.getY()+DEFAULT_HEIGHT < this.getY()){
            return false;
        } else {
            return true;
        }*/
        return ball.getBoundingRectangle().overlaps(sprite.getBoundingRectangle());
    }


    public void setRegion(TextureRegion t) {
        sprite.setRegion(t);
    }

    public void setPosition(float x, float y) {
        sprite.setPosition(x, y);
    }

    public void setSize(float width, float height) {
        sprite.setSize(width,height);
    }

    public float getX() {
        return sprite.getX();
    }

    public float getY() { return sprite.getY(); }
}
