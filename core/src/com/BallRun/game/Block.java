package com.BallRun.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

/**
 * Mostly used to make blocks, but can be set to any TextureRegion
 * Created by HeyJD on 9/04/2015.
 */
public class Block {
    public static final float WIDTH = 128;
    public static final float HEIGHT = 128;

    private Sprite sprite;
    private boolean isDeadly;

    public Block(float posX, float posY) {
       this(posX, posY, Assets.block);
    }

    public Block(float posX, float posY, TextureRegion tr) {
        this(posX, posY, tr, false);
    }

    public Block(float posX, float posY, TextureRegion tr, boolean isDeadly) {
        sprite = new Sprite(tr);
        sprite.setPosition(posX, posY);
        sprite.setSize(WIDTH, HEIGHT);
        this.isDeadly = isDeadly;
    }

    public void updateMotion(float deltaTime) {
        //--Round the input so it gets the same results at the backgroundX source int--
        //Actually, rounding it makes it stutter too much, so I guess I won't
        sprite.translateX(GameController.blockGravity.x * deltaTime);
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
        if (ball.getX() > this.getX()+WIDTH || ball.getX()+WIDTH < this.getX()) {
            return false;
        } else if (ball.getY() > this.getY()+HEIGHT || ball.getY()+HEIGHT < this.getY()){
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
        sprite.setPosition(x,y);
    }

    public void setSize(float width, float height) {
        sprite.setSize(width,height);
    }

    public float getX() {
        return sprite.getX();
    }

    public float getY() {
        return sprite.getY();
    }

    public float getHeight() { return sprite.getHeight(); }

    public float getWidth() { return sprite.getWidth(); }

    public boolean isDeadly() {
        return isDeadly;
    }
}
