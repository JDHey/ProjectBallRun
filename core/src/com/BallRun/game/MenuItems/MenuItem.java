package com.BallRun.game.MenuItems;

import com.BallRun.game.Sprites.Assets;
import com.BallRun.game.Main;
import com.BallRun.game.SaveFile;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/** Any item in a menu uses this class
 * Created by HeyJD on 20/04/2015.
 */
public class MenuItem {
    public static final float BUTTON_MIDDLE_X = (Main.CAMERA_WIDTH/2) - (Assets.buttonWidth/2);
    public static final float BUTTON_MIDDLE_Y = (Main.CAMERA_HEIGHT/2) - (Assets.buttonHeight/2);
    public static final int DEFAULT_SPEED = 5;

    private Sprite sprite;
    private Vector2 goal;
    private boolean stateDown;

    public MenuItem(TextureRegion t) {
        this(t, 0, 0, 0, 0);
    }

    public MenuItem(TextureRegion t, float posX, float posY) {
        this(t, posX, posY, posX, posY);
    }

    public MenuItem(TextureRegion t, float posX, float posY, float goalX, float goalY) {
        this.sprite = new Sprite(t);
        sprite.setPosition(posX, posY);
        this.goal = new Vector2(goalX, goalY);
        stateDown = false;
    }

    public void update(float deltaTime, int speed) {
        if (sprite.getX() != goal.x || sprite.getY() != goal.y) {
            //Halves the distance every update
            sprite.translate(((goal.x - sprite.getX()) / 2f) * deltaTime * speed, ((goal.y - sprite.getY()) / 2f) * deltaTime * speed);
        }
    }

    public void update(float deltaTime) {
        update(deltaTime, DEFAULT_SPEED);
    }

    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }

    //Changes button image
    public void setTouchDown(boolean stateDown) {
        //Make sure it's using the button sheet
        try {
            if (sprite.getTexture() != Assets.buttonSheet) {
                throw new Exception("Wrong texture for Button TouchDown");
            }

            //Don't do anything if state hasn't changed
            if (this.stateDown != stateDown) {
                this.stateDown = stateDown;

                if (stateDown) {
                    sprite.setRegionY(sprite.getRegionY() + Assets.buttonHeight);
                    sprite.setRegionHeight(Assets.buttonHeight);
                } else {
                    sprite.setRegionY(sprite.getRegionY() - Assets.buttonHeight);
                    sprite.setRegionHeight(Assets.buttonHeight);
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /** Currently just plays the click sound */
    public void playClickedSound() {
        if (!SaveFile.isMute) {
            Assets.clickSound.play();
        }
    }

    public void setGoal(float x, float y) {
        goal.set(x,y);
    }

    public void setGoalX(float x) {
        goal.x = x;
    }

    public void setGoalY(float y) {
        goal.y = y;
    }

    public float getGoalX() {
        return goal.x;
    }

    public float getGoalY() {
        return goal.y;
    }

    public void setX(float x) { this.sprite.setX(x); }

    public void setY(float y) { this.sprite.setY(y); }

    public float getX() {
        return sprite.getX();
    }

    public float getY() {
        return sprite.getY();
    }

    public void setRegion(TextureRegion t) {
        sprite.setRegion(t);
    }

    public void setSize(float width, float height) {
        sprite.setSize(width, height);
    }

    public Rectangle getBoundingRectangle() {
        return sprite.getBoundingRectangle();
    }
}
