package com.BallRun.game;

import com.badlogic.gdx.Gdx;

/**
 * This is used for debugging purposes. Use this class for logging.
 * Turn DEBUG to false when building release apk
 * Created by HeyJD on 02-03-17.
 */
public class Log {
    public static final boolean DEBUG = false;

    public static void i(String tag, String string) {
        if (DEBUG) Gdx.app.log(tag, string);
    }

    public static void e(String tag, String string) {
        if (DEBUG) Gdx.app.error(tag, string);
    }

    public static void d(String tag, String string) {
        if (DEBUG) Gdx.app.debug(tag, string);
    }

}
