package com.BallRun.game.MenuItems;

import com.BallRun.game.SaveFile;
import com.BallRun.game.Sprites.Assets;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import java.util.Arrays;

/**
 * Shows a list of high scores
 * Created by HeyJD on 03-02-17.
 */
public class Highscores extends Table {
    private static final String TITLE = "Highscores";
    TextureRegion topBoxTexture = Assets.skinGreen.getAtlas().findRegion("button_03");
    Label titleLabel = new Label(TITLE, Assets.skinGreen);

    int[] scoreArray = new int[SaveFile.SCORE_LIMIT];

    public Highscores(int x, int y) {
        this(x,y,-1);
    }

    public Highscores(int x, int y, int newScore) {
        super(Assets.skinGreen);
        this.setPosition(x, y);
        init(newScore);
    }

    /**
     * Initialises the highscores
     * @param newScore
     */
    private void init(int newScore) {
        this.clear(); //Clears all children

        //Load
        if (loadScores(newScore)) {
            SaveFile.save(scoreArray); //Save if new highscore
        }

        this.add(titleLabel).height(ScoreLabel.BACKGROUND_HEIGHT);
        this.row();
        for(int i : scoreArray) {
            ScoreLabel score = new ScoreLabel(String.valueOf(i));
            this.add(score).height(ScoreLabel.BACKGROUND_HEIGHT).padTop(0);
            this.row();
        }
        this.pack();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(topBoxTexture, getX()-15, getTop()- titleLabel.getHeight(), titleLabel.getWidth()+30, ScoreLabel.BACKGROUND_HEIGHT);
        super.draw(batch,parentAlpha);
    }

    /**
     * Loads the high scores and inserts a new one if possible
     * @param lastScore
     * @return newHighScore
     */
    private boolean loadScores(int lastScore) {
        boolean newHighScore = false;
        int[] saveFileArray = SaveFile.getScoreArray();
        int[] newScoreArray = new int[saveFileArray.length + 1];

        //Take first five ints from original array
        for (int i = 0; i < saveFileArray.length; i++) {
            newScoreArray[i] = saveFileArray[i];
        }

        newScoreArray[saveFileArray.length] = lastScore; //Add last score
        Arrays.sort(newScoreArray); //Sort array

        //Keep in mind the array is now sorted with lowest score at index 0.

        //Add first five ints back to original array
        //They are added in reverse due to sort sorting in reverse
        for (int i = 0; i < saveFileArray.length; i++) {
            this.scoreArray[i] = newScoreArray[saveFileArray.length - i];
        }

        //If the first score is not the sixth, then there's a new high score
        if (newScoreArray[0] != lastScore) {
            newHighScore = true;
        }

        return newHighScore;
    }

    /** Resets the high scores */
    public void resetScores() {
        SaveFile.createFile();
        init(-1);
    }

}
