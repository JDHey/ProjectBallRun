package com.BallRun.game.MenuItems;

import com.BallRun.game.Assets;
import com.BallRun.game.SaveFile;
import com.BallRun.game.Score;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

/** A type of menu item which displays a list of high scores
 * Created by HeyJD on 27/04/2015.
 */
public class Highscores extends MenuItem {
    public static final float WIDTH = 374;
    public static final float HEIGHT = Score.HEIGHT*1;
    private static final float PADDING = 0;
    private static final String TITLE = "Highscores";

    List<Score> scoreList;
    boolean newHighscore = false;
    BitmapFont bitmapFont;

    public Highscores(float goalX, float goalY, Score lastScore) {
        super(Assets.scoreBox, 0 - WIDTH, goalY, goalX, goalY);
        setSize(WIDTH,HEIGHT);
        loadScores(lastScore.getScore());

        bitmapFont = Assets.ComputerFont;
        bitmapFont.setScale(1.1f);
    }

    @Override
    public void update(float delta, int speed) {
        super.update(delta, speed);
        int incremeter = Score.HEIGHT-20;
        int i = incremeter;

        //Updates the list from the top down
        for (Score score : scoreList) {
            score.setPosition(getX()+PADDING, getY()-i);
            i += incremeter;
        }

        //Only saves once
        if (newHighscore) {
            SaveFile.save(scoreList);
            newHighscore = false;
        }
    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);

        for (Score score : scoreList) {
            score.draw(batch);
        }
        bitmapFont.draw(batch, TITLE, (getX()+WIDTH/2)-(bitmapFont.getBounds(TITLE).width/2), getY() + HEIGHT - ((HEIGHT - bitmapFont.getCapHeight()) / 2));
    }

    /** Converts the floats to Score objects and loads them into scoreList */
    private void loadScores(float lastScore) {
        scoreList = new ArrayList<Score>();
        float scoreNumber;
        Score score;
        for (int i=0; i<SaveFile.SCORE_LIMIT; i++) {
            scoreNumber = SaveFile.scoreArray[i];
            if (scoreNumber < lastScore && !newHighscore) {
                score = new Score(getX(), getY(), lastScore);
                score.setSize(WIDTH, score.getSprite().getHeight());
                scoreList.add(score);
                newHighscore = true;
            }

            //This is to stop it from adding one too many after adding a new highScore
            if (!newHighscore || i<SaveFile.SCORE_LIMIT-1) {
                score = new Score(getX(), getY(), scoreNumber);
                score.setSize(WIDTH, score.getSprite().getHeight());
                scoreList.add(score);
            }
        }
    }

    /** Resets the high scores */
    public void resetScores() {
        SaveFile.createFile();
        //dispose(); Calling it makes it crash
        loadScores(-1);
    }

    public void dispose() {
        for (Score score : scoreList) {
            score.dispose();
        }
    }


}
