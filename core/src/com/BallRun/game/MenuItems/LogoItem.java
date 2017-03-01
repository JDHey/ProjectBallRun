package com.BallRun.game.MenuItems;

import com.BallRun.game.Sprites.Assets;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;

/**
 * This is the logo which bounces up and down.
 * Created by HeyJD on 21/04/2015.
 */
public class LogoItem extends Actor {
    public static final int WIDTH = Assets.logoMain.getRegionWidth();

    public Runnable runnableTurnOnShadow = new Runnable() {
        @Override
        public void run() {
            isShadowOn = true;
            System.out.print("Shadow is on");
        }
    };

    private Sprite logoShadow = new Sprite(Assets.logoShadow);
    private boolean isShadowOn = false;

    /**
     * Places a logo with default inputs
     */
    public LogoItem(float x, float y) {
        setPosition(x, y);
        RepeatAction bouncyAction = new RepeatAction();
        bouncyAction.setCount(RepeatAction.FOREVER);
        bouncyAction.setAction(Actions.sequence(Actions.moveBy(0, -40, 2f, Interpolation.sine), Actions.moveBy(0, 40, 2f, Interpolation.sine)));
        this.addAction(bouncyAction);
    }

    public void setShadowPosition(float x, float y) {
        logoShadow.setPosition(x, y);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (isShadowOn) {
            logoShadow.draw(batch);
        }
        batch.draw(Assets.logoMain, getX(), getY());
    }

}
