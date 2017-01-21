package com.BallRun.game;

import com.BallRun.game.Sprites.Assets;
import com.BallRun.game.Sprites.Ball;
import com.BallRun.game.Sprites.BasicScrollingSprite;
import com.BallRun.game.Sprites.Block;
import com.BallRun.game.Sprites.Score;
import com.BallRun.game.Sprites.Spike;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.Queue;

/** This class takes care of the main gameplay mechanics during play
 *
 * Created by HeyJD on 9/04/2015.
 */
public class GameController {
    public static enum GameState { PLAYING, PAUSED, FINISHED }

    private GameState currentGameState = GameState.PLAYING;
    public static final float GAME_SPEED = 1014;

    public static final Vector2 blockGravity = new Vector2(-GAME_SPEED, 0);

    private static final float FADE_OUT_SPEED = 0.02f;
    private float drawAlpha = 1;

    private float backgroundSrcX = 0;

    public static final float BALL_MAX_FALL_VELOCITY = 3000;
    public static final float BALL_MAX_JUMP_VELOCITY = 1500;
    public static final float BALL_INITIAL_JUMP_VELOCITY = 700;
    private static final int BALL_SPAWN_X = 256;
    private static final int BALL_SPAWN_Y = 640;

    public final Ball ball;

    public final Score score;

    private GameLevelGeneration level;


    public GameController() {
        this.currentGameState = GameState.PLAYING;
        this.ball = new Ball(BALL_SPAWN_X, BALL_SPAWN_Y);
        this.score = new Score(Main.CAMERA_WIDTH - Score.WIDTH, Main.CAMERA_HEIGHT-Score.HEIGHT);
        this.level = new GameLevelGeneration();

        if (!SaveFile.isMute) {
            Assets.music.play();
        }
    }

    private void updateBall(float deltaTime) {
        //If the ball is half way out of the screen, kill();
        if (!ball.isDead() && ball.getY() <= 0-Ball.HEIGHT /2) {
            kill();
        }
        ball.updateMotion(deltaTime);
    }


    private void kill() {
        //pauseFloor = true;
        level.setPaused(true);
        if (!SaveFile.isMute) {
            Assets.music.stop(); //This freezes the GalaxyS5 half a second due to hardware problems
            Assets.deathSound.play();
        }
        ball.die();
    }

    /**
     * Keeps all the updates in one neat spot
     * @param deltaTime
     */
    public void updateAll(float deltaTime) {
        if (currentGameState != GameState.PAUSED) {
            updateBall(deltaTime);
            if (!level.isPaused()) {
                updateScore(deltaTime);
            }
            level.updateAll(deltaTime, ball.getBoundingRectangle());
            checkCollisions();
        }

        //Finish game if the ball is dead and out of the screen
        if (ball.isDead() && ball.getY() < 0- Ball.HEIGHT) {
            currentGameState = GameState.FINISHED;
        }
    }

    private void checkCollisions() {
        Queue<BasicScrollingSprite> collisions = level.getQueueOfCollidingSprites();

        if (ball.getState() != Ball.STATE_JUMPING) {
            ball.setState(Ball.STATE_FALLING); //Stays falling if there's no block below.
        }

        while(!collisions.isEmpty()) {
            BasicScrollingSprite sprite = collisions.poll();
            if (sprite instanceof Block) {
                //If the block is higher than 50% of the blocks height, teleport it to the top and call it grounded
                //If it is lower, let it fall
                if ((ball.getY() - sprite.getY()) > (Block.DEFAULT_HEIGHT * 0.50)) {
                    ball.setY(sprite.getY() + Block.DEFAULT_HEIGHT - 1); //Move it up so it's not stuck in the block
                    ball.setState(Ball.STATE_GROUNDED);
                } else {
                    kill();
                }
            } else if (sprite instanceof Spike) {
                kill();
            }
        }
    }

    public void drawAllItems(SpriteBatch batch) {
        batch.draw(Assets.gameBackground, 0, 0, Math.round(getBackgroundSrcX()), 0, Math.round(Main.CAMERA_WIDTH), Math.round(Main.CAMERA_HEIGHT));

        if (drawAlpha > 0) {
            level.drawAll(batch, drawAlpha);
            ball.draw(batch, drawAlpha);
            score.draw(batch, drawAlpha);

            //Make sure it continues the transition all the way to 0 alpha
            if (drawAlpha != 1 && drawAlpha != 0) {
                fadeOut();
            }
        }

        if (drawAlpha < 1) {
            Color c = batch.getColor();
            batch.setColor(c.r, c.g, c.b, 1-drawAlpha); //set alpha to 1
            batch.draw(Assets.menuBackground, 0, 0, Math.round(Main.CAMERA_WIDTH), Math.round(Main.CAMERA_HEIGHT));
            batch.setColor(c.r, c.g, c.b, 1f);
        }
    }

    /** There is only a fadeOut, no fadeIn */
    public void fadeOut() {
        drawAlpha -= FADE_OUT_SPEED;
        if (drawAlpha > 1) { drawAlpha = 1; } else
        if (drawAlpha < 0) { drawAlpha = 0; }
    }

    /**
     * Finger on screen
     */
    public void touchDown() {
        // Only if on the ground and not dead
        if (ball.getState() == Ball.STATE_GROUNDED && !ball.isDead()) {
            ball.setState(Ball.STATE_JUMPING);
        }
    }

    /**
     * Finger lifted off screen
     */
    public void touchUp() {
        if (!ball.isDead()) { ball.setState(Ball.STATE_FALLING); }
    }

    public float getDrawAlpha() {
        return drawAlpha;
    }

    /**
     * Only used when there's a scrolling background
     * @param deltaTime
     */
    private void incrementBackgroundSrcX(float deltaTime) {
        //I multiply by 1000 so it doesn't noticeably (if it's noticeable at all) reset too often
        if (backgroundSrcX >= Assets.gameBackground.getWidth()*1000) {
            backgroundSrcX = backgroundSrcX - Assets.gameBackground.getWidth(); //Reset it so it doesn't overflow
        }

        //Because blockGravity.x is negative, I do -=. This makes sure backgroundSrcX is positive
        backgroundSrcX -= blockGravity.x * deltaTime;
    }

    private void updateScore(float deltaTime) {
        score.incrementScore(deltaTime);
    }

    public float getBackgroundSrcX() {
        return backgroundSrcX;
    }

    public GameState getCurrentGameState() { return currentGameState; }

    public void dispose() {
        score.dispose();
    }
}
