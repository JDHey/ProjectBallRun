package com.BallRun.game.MenuItems;

import com.BallRun.game.Sprites.Assets;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

/**
 * Holds a score and displays it
 * Created by HeyJD on 07-02-17.
 */
public class ScoreLabel extends Label {
    public static final int BACKGROUND_WIDTH = 250;
    public static final int BACKGROUND_HEIGHT = 100;
    private static final float SCORE_FONT_SCALE = 2f;
    TextureRegion scoreBoxTexture = Assets.skinGreen.getAtlas().findRegion("button_03");

    public ScoreLabel(String text) {
        super(text, Assets.skinGreen);
        this.setFontScale(SCORE_FONT_SCALE);
        this.setWidth(BACKGROUND_WIDTH);
        this.setHeight(BACKGROUND_HEIGHT);
        this.setAlignment(Align.center);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(scoreBoxTexture, getX() - (BACKGROUND_WIDTH / 2) + (this.getWidth() / 2), getY() + (BACKGROUND_HEIGHT / 2) - (this.getHeight() / 2), BACKGROUND_WIDTH, BACKGROUND_HEIGHT);
        super.draw(batch, parentAlpha);
    }
}
