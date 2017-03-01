package com.BallRun.game;

import com.BallRun.game.Sprites.Assets;
import com.BallRun.game.Sprites.Ball;
import com.BallRun.game.Sprites.BasicScrollingSprite;
import com.BallRun.game.Sprites.Block;
import com.BallRun.game.Sprites.Spike;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.Queue;

/**
 * This class takes care of the main gameplay mechanics during play
 * <p/>
 * Created by HeyJD on 9/04/2015.
 */
public class GameController {
    public static enum GameState {READY, PLAYING, PAUSED, FINISHED}

    private GameState currentGameState = GameState.READY;
    public static final float GAME_SPEED = 1014;

    public static final Vector2 BLOCK_GRAVITY = new Vector2(-GAME_SPEED, 0);
    private float drawAlpha = 1;

    private float backgroundSrcX = 0;

    public static final float BALL_MAX_FALL_VELOCITY = 3000;
    public static final float BALL_MAX_JUMP_VELOCITY = 1500;
    public static final float BALL_INITIAL_JUMP_VELOCITY = 700;
    private static final int BALL_SPAWN_X = 256;
    private static final int BALL_SPAWN_Y = 1080;
    private static final float SCORE_SCALE = 10f; //Scales the points the player receives

    private final Ball ball;
    private float score;


    private GameLevelGeneration level;


    public GameController() {
        this.ball = new Ball(BALL_SPAWN_X, BALL_SPAWN_Y);
        this.ball.setInvincible(2);
        this.level = new GameLevelGeneration();
    }

    private void updateBall(float deltaTime) {
        //If the ball is half way out of the screen, kill();
        if (!ball.isDead() && ball.getY() <= 0 - Ball.HEIGHT / 2) {
            kill();
        }
        ball.updateMotion(deltaTime);
    }


    private void kill() {
        if (ball.isInvincible()) {
            if (ball.getY() < 0) {
                ball.setY(0);
                ball.setState(Ball.STATE_GROUNDED);
            }
        } else {
            //pauseFloor = true;
            level.setPaused(true);
            if (!SaveFile.isMute()) {
                Assets.music.stop(); //This freezes the GalaxyS5 half a second due to hardware problems
                Assets.deathSound.play();
            }
            ball.die();
        }
    }

    /**
     * Keeps all the updates in one neat spot
     *
     * @param deltaTime
     */
    public void updateAll(float deltaTime) {
        if (currentGameState == GameState.PLAYING) {
            updateBall(deltaTime);
            if (!level.isPaused()) {
                updateScore(deltaTime);
            }
            level.updateAll(deltaTime, ball.getBoundingRectangle());

            checkCollisions();
        }

        //Finish game if the ball is dead and out of the screen
        if (ball.isDead() && ball.getY() < 0 - Ball.HEIGHT) {
            currentGameState = GameState.FINISHED;
        }
    }

    private void checkCollisions() {
        Queue<BasicScrollingSprite> collisions = level.getQueueOfCollidingSprites();

        //Kill it if it hits a block below 50% of the block. 0% if ball is invincible.
        float ballUnderBlockKillHeight = ball.isInvincible() ? 0.0f : 0.5f;

        if (ball.getState() != Ball.STATE_JUMPING) {
            ball.setState(Ball.STATE_FALLING); //Stays falling if there's no block below.
        }

        while (!collisions.isEmpty()) {
            BasicScrollingSprite sprite = collisions.poll();
            if (sprite instanceof Block) {
                //If the block is higher than 50% of the blocks height, teleport it to the top and call it grounded
                //If it is lower, let it fall
                if ((ball.getY() - sprite.getY()) > (Block.DEFAULT_HEIGHT * ballUnderBlockKillHeight)) {
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
        }

        if (drawAlpha < 1) {
            Color c = batch.getColor();
            batch.setColor(c.r, c.g, c.b, 1 - drawAlpha); //set alpha to 1
            batch.draw(Assets.menuBackground, 0, 0, Math.round(Main.CAMERA_WIDTH), Math.round(Main.CAMERA_HEIGHT));
            batch.setColor(c.r, c.g, c.b, 1f);
        }
    }

    /**
     * Finger on screen
     */
    public void touchDown() {
        if (currentGameState == GameState.PLAYING) {
            // Only if on the ground and not dead
            if (ball.getState() == Ball.STATE_GROUNDED && !ball.isDead()) {
                ball.setState(Ball.STATE_JUMPING);
            }
        }

    }

    /**
     * Finger lifted off screen
     */
    public void touchUp() {
        if (currentGameState == GameState.READY || currentGameState == GameState.PAUSED) {
            //Start game
            Gdx.app.log("GameController", "Starting game");

            startGame();
        } else if (currentGameState == GameState.PLAYING) {
            if (!ball.isDead()) {
                ball.setState(Ball.STATE_FALLING);
            }
        }
    }

    private void startGame() {
        if (!SaveFile.isMute()) {
            Assets.music.play();
        }

        currentGameState = GameState.PLAYING;
    }

    public int getScore() {
        return Math.round(score);
    }

    /**
     * Only used when there's a scrolling background
     *
     * @param deltaTime
     */
    private void incrementBackgroundSrcX(float deltaTime) {
        //I multiply by 1000 so it doesn't noticeably (if it's noticeable at all) reset too often
        if (backgroundSrcX >= Assets.gameBackground.getWidth() * 1000) {
            backgroundSrcX = backgroundSrcX - Assets.gameBackground.getWidth(); //Reset it so it doesn't overflow
        }

        //Because BLOCK_GRAVITY.x is negative, I do -=. This makes sure backgroundSrcX is positive
        backgroundSrcX -= BLOCK_GRAVITY.x * deltaTime;
    }

    private void updateScore(float deltaTime) {
        score += SCORE_SCALE * deltaTime;
    }

    public float getBackgroundSrcX() {
        return backgroundSrcX;
    }

    public GameState getCurrentGameState() {
        return currentGameState;
    }

    public Ball getBall() {
        return ball;
    }

    public void dispose() {
    }
}
