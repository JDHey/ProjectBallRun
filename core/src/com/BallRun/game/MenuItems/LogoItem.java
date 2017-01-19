package com.BallRun.game.MenuItems;

import com.BallRun.game.Assets;
import com.BallRun.game.Main;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

/** Mainly made for looks. This makes the logo bounce up and down.
 *
 * Created by HeyJD on 21/04/2015.
 */
public class LogoItem extends MenuItem {
    private static final float DEFAULT_POSX = (Main.CAMERA_WIDTH/2) - (Assets.logoShadow.getRegionWidth()/2);
    private static final float DEFAULT_POSY =  Main.CAMERA_HEIGHT;
    private static final float DEFAULT_GOALX = DEFAULT_POSX;
    private static final float DEFAULT_GOALY = (Main.CAMERA_HEIGHT/2) - (Assets.logoMain.getRegionHeight()/2) + 300;

    private Sprite logoMain = new Sprite(Assets.logoMain);

    float maxShadowDistance = 50;
    Vector2 goal;
    private Vector2 velocity = new Vector2();

    /** Places a logo with default inputs */
    public LogoItem() {
        super(Assets.logoShadow, DEFAULT_POSX, DEFAULT_POSY, DEFAULT_GOALX, DEFAULT_GOALY);
        assignPositionAndGoal();

    }

    public LogoItem(float x, float y) {
        super(Assets.logoShadow, x, y);
        assignPositionAndGoal();
    }

    public LogoItem(float x, float y, float goalX, float goalY) {
        super(Assets.logoShadow, x, y, goalX, goalY);
        assignPositionAndGoal();
    }

    private void assignPositionAndGoal() {
        logoMain.setPosition(getX(), getY());
        this.goal = new Vector2(getGoalX(), getGoalX());
    }

    @Override
    public void update(float deltaTime, int speed) {
        super.update(deltaTime, speed);

        //This changes the goal once the goal is met
        if (logoMain.getY() <= super.getGoalY()+10) {
            goal.y = getGoalY()+maxShadowDistance;
        } else if (logoMain.getY() >= super.getGoalY()+maxShadowDistance-1) {
            goal.y = getGoalY();
        }

        /* Too much acceleration
        logoShadow.translate(((goal.x - logoShadow.getX()) / 2f)  * deltaTime * speed, ((goal.y - logoShadow.getY()) / 2f)  * deltaTime * speed);
        */

        /* Too Linear
        if (logoShadow.getY() < goal.y)
            logoShadow.translateY(1);
        else if (logoShadow.getY() > goal.y)
            logoShadow.translateY(-1);
        */

        /* Juuust right \/\/\/\/ */
        //If super hasn't reached its goal (i.e. stopped moving), then don't bounce
        if (Math.abs(getY() - getGoalY()) >= 5) {
            logoMain.translate(((goal.x - logoMain.getX()) / 2f) * deltaTime * speed, ((goal.y - logoMain.getY()) / 2f) * deltaTime * speed);
        } else {
            if (logoMain.getY() < goal.y)
                velocity.y = interpolateVelocity(30, velocity.y, deltaTime * 8000);
            else if (logoMain.getY() > goal.y)
                velocity.y = interpolateVelocity(-30, velocity.y, deltaTime * 8000);

            logoMain.translate(((goal.x - logoMain.getX()) / 2f) * deltaTime * speed, velocity.y * deltaTime);
        }
    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);
        logoMain.draw(batch);
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

}
