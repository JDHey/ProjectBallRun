package com.BallRun.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/** Main ball
 * Created by HeyJD on 9/04/2015.
 */
public class Ball {
    public static final float WIDTH = 128;
    public static final float HEIGHT = 128;

    private int ball_state = STATE_FALLING;
    public static final int STATE_GROUNDED = 1;
    public static final int STATE_JUMPING = 2;
    public static final int STATE_FALLING = 3;

    private static float circumference = (float)(WIDTH*Math.PI);
    private float rotation_speed = -1;

    private boolean dead = false;

    private Sprite sprite;
    private Vector2 velocity = new Vector2();

    public Ball(float posX, float posY) {
        sprite = new Sprite(getRandomBall());
        sprite.setPosition(posX, posY);
        sprite.setSize(WIDTH, HEIGHT);
    }

    /** Returns a random textureRegion */
    private TextureRegion getRandomBall() {
        int rand = (int) Math.round(Math.random()*5)+1; //1-6
        TextureRegion returnTexture = null;
        switch(rand) {
            case 1:
                returnTexture = Assets.owl;
                break;
            case 2:
                returnTexture = Assets.pig;
                break;
            case 3:
                returnTexture = Assets.penguin;
                break;
            case 4:
                returnTexture = Assets.cow;
                break;
            case 5:
                returnTexture = Assets.sheep;
                break;
            case 6:
                returnTexture = Assets.hippo;
                break;
        }
        return returnTexture;
    }

    /** Using interpolation for motion */
    public void updateMotion(float deltaTime) {
        if (ball_state == STATE_JUMPING) {
            if (velocity.y >= GameController.BALL_MAX_JUMP_VELOCITY) {
                ball_state = STATE_FALLING; //Once reached max velocity, start falling
            } else {
                velocity.y = interpolateVelocity(GameController.BALL_MAX_JUMP_VELOCITY, velocity.y, deltaTime * 8000);
            }
        } else if (ball_state == STATE_FALLING) {
            velocity.y = interpolateVelocity(-GameController.BALL_MAX_FALL_VELOCITY, velocity.y, deltaTime * 5000);
        }

        rotate(deltaTime);
        sprite.translate(velocity.x * deltaTime, velocity.y * deltaTime);
    }

    private void rotate(float deltaTime) {
        if (!isDead()) {
            //Calculates the rotational speed based off the circumference and block speed
            rotation_speed = ((GameController.GAME_SPEED*deltaTime)/circumference)*360;
            sprite.rotate(-rotation_speed);
        }
    }

    /** Interpolation */
    private float interpolateVelocity(float vGoal, float vCurrent, float deltaTime) {
        float vDifference = vGoal - vCurrent;

        if (vDifference > deltaTime) {
            return vCurrent + deltaTime;
        }
        if (vDifference < -deltaTime) {
            return vCurrent - deltaTime;
        }

        return vGoal;
    }

    public void draw(SpriteBatch batch, float alpha) {
        sprite.draw(batch, alpha);
    }

    //I don't use sprite.getBoundingRectangle() because that rotates with the ball, I don't want that
    public Rectangle getBoundingRectangle() {
        return new Rectangle(getX(),getY(),WIDTH,HEIGHT);
    }

    /** Could turn the STATES into a class to force the input later. */
    public void setState(int state) {
        switch (state) {
            case STATE_GROUNDED:
                velocity.x = 0;
                velocity.y = 0;
                ball_state = STATE_GROUNDED;
                break;
            case STATE_JUMPING:
                if (!SaveFile.isMute) {
                    Assets.jumpSound.play(Assets.jumpSoundVolume);
                }
                velocity.y = GameController.BALL_INITIAL_JUMP_VELOCITY;
                ball_state = STATE_JUMPING;
                break;
            case STATE_FALLING:
                ball_state = STATE_FALLING;
                break;
            default:
                throw new IndexOutOfBoundsException();
        }
    }

    public int getState() {
        return ball_state;
    }

    public boolean isDead() {
        return dead;
    }

    public void die() {
        this.dead = true;
        setState(STATE_JUMPING);
    }

    public void setX(float x) { this.sprite.setX(x); }

    public void setY(float y) { this.sprite.setY(y); }

    public float getX() {
        return sprite.getX();
    }

    public float getY() {
        return sprite.getY();
    }
}
