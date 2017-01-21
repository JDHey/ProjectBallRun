package com.BallRun.game;

import com.BallRun.game.Sprites.Score;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.util.ArrayList;
import java.util.List;

/** A static class to load and save.
 * I only want to load once at the start of the game, otherwise it's twitchy everytime it loads.
 * Created by HeyJD on 29/04/2015.
 */
public class SaveFile {
    private static final String TAG = "ERROR";
    private static final String SAVE_FILE_NAME = ".ballrun";
    public static final int SCORE_LIMIT = 5; //The limit of the amount of high scores to save

    public static float[] scoreArray = {0f,0f,0f,0f,0f};
    public static boolean isMute = false;

    public static void load() {
        try {
            //If the file doesn't exist
            if (!Gdx.files.local(SAVE_FILE_NAME).exists()) {
                createFile();
            } else {
                FileHandle fileHandle = Gdx.files.local(SAVE_FILE_NAME);
                String[] strings = fileHandle.readString().split("\n");

                isMute = Boolean.parseBoolean(strings[0]);

                for (int i = 0; i < SCORE_LIMIT; i++) {
                    scoreArray[i] = Float.parseFloat(strings[i+1]);
                }
            }
        } catch (Throwable e) {
            Gdx.app.log(TAG, "Couldn't retrieve file");
            Gdx.app.log(TAG, e.getMessage());

        }
    }

    /** Parse in the scores to save
     * @param scoreList
     */
    public static void save(List<Score> scoreList) {
        try {
            float score;
            FileHandle fileHandle = Gdx.files.local(SAVE_FILE_NAME);

            //First put a line without appending to replace the file
            fileHandle.writeString(Boolean.toString(isMute)+"\n", false);

            for (int i = 0; i < SCORE_LIMIT; i++) {
                score = scoreList.get(i).getScore();
                fileHandle.writeString(Float.toString(score)+"\n", true);
                scoreArray[i] = score;
            }
        } catch (Throwable e) {
            Gdx.app.log(TAG, "Couldn't save file");
            Gdx.app.log(TAG, e.getMessage());
        }
    }

    /** Used for saving the mute variable */
    public static void save() {
        try {
            FileHandle fileHandle = Gdx.files.local(SAVE_FILE_NAME);

            //First put a line without appending to replace the file
            fileHandle.writeString(Boolean.toString(isMute)+"\n", false);

            for (int i = 0; i < SCORE_LIMIT; i++) {
                fileHandle.writeString(Float.toString(scoreArray[i])+"\n", true);
            }
        } catch (Throwable e) {
            Gdx.app.log(TAG, "Couldn't save file");
            Gdx.app.log(TAG, e.getMessage());
        }
    }

    /** Parses a list of empty scores into a save (overwrites existing file if one already exists)*/
    public static void createFile() {
        List<Score> scoreList = new ArrayList<Score>();
        for (int i=0; i<SCORE_LIMIT;i++) {
            scoreList.add(new Score(0,0));
        }
        save(scoreList);
    }
}
