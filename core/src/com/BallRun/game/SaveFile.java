package com.BallRun.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

/**
 * A static class to load and save.
 * I only want to load once at the start of the game, otherwise it's twitchy everytime it loads.
 * Created by HeyJD on 29/04/2015.
 */
public class SaveFile {
    private static final String TAG = "ERROR";
    private static final String SAVE_FILE_NAME = ".ballrun";
    public static final int SCORE_LIMIT = 5; //The limit of the amount of high scores to save

    private static int[] scoreArray = {0, 0, 0, 0, 0};
    private static boolean isMute = false;

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
                    scoreArray[i] = Integer.parseInt(strings[i + 1]);
                }
            }
        } catch (Throwable e) {
            Gdx.app.log(TAG, "Couldn't retrieve file");
            Gdx.app.log(TAG, e.getMessage());

        }
    }

    /**
     * Used to save new scores
     *
     * @param intScoreArray
     */
    public static void save(int[] intScoreArray) {
        try {
            int score;
            FileHandle fileHandle = Gdx.files.local(SAVE_FILE_NAME);

            //First put a line without appending to replace the file
            fileHandle.writeString(Boolean.toString(isMute) + "\n", false);

            for (int i = 0; i < SCORE_LIMIT; i++) {
                score = Math.round(intScoreArray[i]);
                fileHandle.writeString(String.valueOf(score) + "\n", true);
                scoreArray[i] = score;
            }
        } catch (Throwable e) {
            Gdx.app.log(TAG, "Couldn't save file");
            Gdx.app.log(TAG, e.getMessage());
        }
    }

    /**
     * Used for saving the mute variable
     */
    public static void save() {
        try {
            FileHandle fileHandle = Gdx.files.local(SAVE_FILE_NAME);

            //First put a line without appending to replace the file
            fileHandle.writeString(Boolean.toString(isMute) + "\n", false);

            for (int i = 0; i < SCORE_LIMIT; i++) {
                fileHandle.writeString(String.valueOf(scoreArray[i]) + "\n", true);
            }
        } catch (Throwable e) {
            Gdx.app.log(TAG, "Couldn't save file");
            Gdx.app.log(TAG, e.getMessage());
        }
    }

    /**
     * Passes an empty array of scores into a save (overwrites existing file if one already exists)
     */
    public static void createFile() {
        /*List<Score> scoreList = new ArrayList<Score>();
        for (int i=0; i<SCORE_LIMIT;i++) {
            scoreList.add(new Score(0,0));
        }*/

        int[] newScoreArray = {0, 0, 0, 0, 0};
        save(newScoreArray);
    }


    public static int[] getScoreArray() {
        return scoreArray;
    }

    public static boolean isMute() {
        return isMute;
    }

    public static void setMute(boolean isMute) {
        SaveFile.isMute = isMute;
    }

}
