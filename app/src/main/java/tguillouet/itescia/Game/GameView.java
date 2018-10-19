package tguillouet.itescia.Game;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.annotation.RequiresApi;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import tguillouet.itescia.GameObject.Asset;
import tguillouet.itescia.GameObject.Ball;
import tguillouet.itescia.GameObject.BufferSpeed;
import tguillouet.itescia.GameObject.BufferZone;
import tguillouet.itescia.GameObject.Button;
import tguillouet.itescia.GameObject.Player;
import tguillouet.itescia.GameObject.Text;

public class GameView extends View implements View.OnTouchListener {

    public Boolean gamePaused = false;

    /* Game assets */
    private Player player1;
    private Text player1Score;
    private Player player2;
    private Text player2Score;
    private Ball ball;
    private Button pauseBtn;

    /* Win screen */
    private Text winText;
    private Text subText;

    /* Utils */
    private Paint p = new Paint();
    private DisplayMetrics metrics;
    private MediaPlayer mp = new MediaPlayer();
    private Vibrator vibration;

    private BufferZone bf;

    private Integer maxScore = 10;

    public GameView(Context context) {
        super(context);

        this.setBackgroundColor(Color.BLACK);

        metrics = new DisplayMetrics();
        ((android.view.WindowManager)context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(metrics);

        this.setOnTouchListener(this);

        p.setColor(Color.WHITE);

        initAssets(context);
    }

    /**
     * Load a sound in the media player
     * @param fileName The name of the file who will be loaded
     * @param context The application context
     */
    private void initMediaPlayer(String fileName, Context context) {
        try {
            mp.reset();
            AssetFileDescriptor afd;
            afd = context.getAssets().openFd(fileName);
            mp.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());
            mp.prepare();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Init assets values
     */
    private void initAssets(Context context) {
        /* Init players */
        player1 = new Player("Player 1",  percentWidth(2f), percentHeight(20f), percentWidth(15f), this.getHalfHeight(), p);
        player1Score = new Text("0", this.getHalfWidth() - percentWidth(5f),  percentHeight(11f), percentHeight(20f), context);

        player2 = new Player("Player 2",percentWidth(2f),  percentHeight(20f), this.getW() - percentWidth(15f), this.getHalfHeight(), p);
        player2Score = new Text("0", this.getHalfWidth() + percentWidth(5f), percentHeight(11f), percentHeight(20f), context);

        /* Init ball */
        ball = new Ball(percentWidth(2f), percentHeight(4f), percentWidth(18f), this.getHalfHeight() - percentHeight(2f), p, scaledSpeed(1.3f));
        ball.setDirection(-1, 1);

        winText = new Text("", this.getHalfWidth() / 2, this.getHalfHeight(), percentHeight(15f), context);
        subText = new Text("Tap anywhere to restart", this.getHalfWidth() / 2, this.getHalfHeight() + percentHeight(7f), percentHeight(6f), context);

        pauseBtn = new Button("||", this.getHalfWidth(), this.getH() - percentHeight(14f), percentWidth(13f), percentHeight(11f), percentHeight(10f), context);

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        /* Ressource https://gamedev.stackexchange.com/questions/56271/android-multitouch-how-to-detect-movement-on-non-primary-pointer-finger */
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                if (pauseBtn.getTouch(event)) {
                    if (!gamePaused) {
                        ball.setSpeed(0);

                        winText.setText("Paused");
                        subText.setText("Tap on pause button to resume");
                        this.gamePaused = true;
                    } else {
                        ball.setSpeed(scaledSpeed(1.3f));
                        this.gamePaused = false;
                    }
                } else if (!gamePaused) updatePlayers(event);
                else if (gamePaused && (player1.getScore() == this.maxScore || player2.getScore() == this.maxScore)) {
                    unpauseGame();
                    resetGame();
                    invalidate();
                }
                break;
        }
        return true;
    }

    /**
     * Update the position of each player at each touch event thrown
     * @param event
     */
    private void updatePlayers(MotionEvent event) {
        int count = event.getPointerCount();
        if (count < 3) {
            for (int i = 0; i < count; i++) {
                if (event.getX(i) < this.getHalfWidth()) {
                    player1.setYPos(Math.round(event.getY(i)));
                } else {
                    player2.setYPos(Math.round(event.getY(i)));
                }
            }
        }
    }

    /**
     * Unpause the game
     */
    private void unpauseGame() {
        this.gamePaused = false;
    }

    /**
     * Reset the state of the game
     */
    private void resetGame() {
        player1.setScore(0);
        player2.setScore(0);
        player1Score.setText("0");
        player2Score.setText("0");
        resetBall(player1, "right");
        resetPlayersPos();
        ball.setExchangeCount(0);
        bf = null;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (player1.getScore() == this.maxScore || player2.getScore() == this.maxScore) {
            String playerName = (player1.getScore() == this.maxScore)? player1.getName(): player2.getName();
            winText.setText(playerName + " win the game !");
            subText.setText("Tap anywhere to restart");
            winText.setXPos(this.getHalfWidth() - Asset.getTextWidth(winText, winText.getPaint()) / 2);
            subText.setXPos(this.getHalfWidth() - Asset.getTextWidth(subText, subText.getPaint()) / 2);
            winText.render(canvas);
            subText.render(canvas);
            this.gamePaused = true;
        } else {
            player1.render(canvas);
            player2.render(canvas);
            if (bf != null) {
                bf.render(canvas);
            }
            ball.render(canvas);
            player1Score.render(canvas);
            player2Score.render(canvas);
            pauseBtn.render(canvas);
            if (gamePaused) {
                winText.setXPos(this.getHalfWidth() - Asset.getTextWidth(winText, winText.getPaint()) / 2);
                subText.setXPos(this.getHalfWidth() - Asset.getTextWidth(subText, subText.getPaint()) / 2);
                winText.render(canvas);
                subText.render(canvas);
            }
            checkCollisions();
            invalidate();
        }
    }

    /**
     * Check the collisions between players, balls and walls
     */
    private void checkCollisions() {
        applyBuff();
        if ((ball.getCenter()[1] >= this.getH()) || (ball.getCenter()[1] <= 0)) {
            ball.setDirection(ball.getDirection()[0], ball.getDirection()[1] * -1); /* We only need to invert Y axis */
        } else if (ball.getCenter()[0] >= this.getW()) {
            playSound("explosion.mp3");
            incrementScore(player1, player1Score);
            resetBall(player2, "left");
            resetPlayersPos();
        } else if (ball.getCenter()[0] <= 0) {
            playSound("explosion.mp3");
            incrementScore(player2, player2Score);
            resetBall(player1, "right");
            resetPlayersPos();
        }else if (ball.collide(player1) || ball.collide(player2)) {
            ball.setDirection(ball.getDirection()[0] * -1, ball.getDirection()[1]); /* We only need to invert X axis */
            playSound("pong.mp3");
            vibrate();
            if (ball.getExchangeCount().equals(6)) {
                Paint p = new Paint();
                p.setColor(Color.rgb(231, 117, 0));
                bf = new BufferSpeed(this.random(percentWidth(13f), percentWidth(26f), this.getHalfWidth()), this.random(percentWidth(13f), percentWidth(26f), this.getHalfHeight()), percentHeight(11f), p);
            }
        }
    }

    private void applyBuff() {
        if (bf != null) {
            ball.setBuffType(bf);
            if (((BufferSpeed) bf).buff(ball)) {
                bf = null;
            }
            ball.setExchangeCount(0);
        }
    }

    /**
     * Increment the score of a player
     * @param player The player who win the play
     * @param score  The score of the player
     */
    private void incrementScore(Player player, Text score) {
        player.setScore(player.getScore() + 1);
        score.setText(player.getScore().toString());
    }

    /**
     * Reset the position of the ball
     * @param player The player who lost the point
     * @param orientation The orientation of the opponent
     */
    private void resetBall(Player player, String orientation) {
        /* Reset the position of the ball */
        Integer offset = (!orientation.equals("right"))? ((!orientation.equals("left"))? percentWidth(3f): -percentWidth(3f)): percentWidth(3f);
        ball.setXPos(player.getXPos() + offset);
        ball.setYPos(this.getHalfHeight() - (ball.getAssetWidth() / 2));

        /* Set the new direction of the ball */
        Integer xDir = (!orientation.equals("right"))? ((!orientation.equals("left"))? 1: -1): 1;
        ball.setDirection(xDir, ball.getDirection()[1]);

        ball.setSpeed(scaledSpeed(1.3f));
    }

    /**
     * Reset the players positions
     */
    private void resetPlayersPos() {
        player1.resetPosition();
        player2.resetPosition();
    }

    /**
     * Play a sound
     * @param soundName The name of the file who will be loaded
     */
    private void playSound(String soundName) {
        initMediaPlayer(soundName, getContext());
        if(!mp.isPlaying()) {
            mp.start();
        }
    }

    /**
     * Make the phone vibrate
     */
    private void vibrate() {
        vibration = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibration.vibrate(VibrationEffect.createOneShot(30,VibrationEffect.DEFAULT_AMPLITUDE));
        }else{
            vibration.vibrate(30);
        }
    }

    private Integer random(Integer min, Integer max, Integer origin) {
        Random rand = new Random();
        Integer r = origin;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            r = (origin - min) + rand.nextInt((max) + 1);
        }
        return r;
    }

    private Integer percentWidth(Float percent) {
        return Math.round((percent / 100) * this.getW());
    }

    private Integer percentHeight(Float percent) {
        return Math.round((percent / 100) * this.getH());
    }

    private Integer scaledSpeed(Float percentSpeed) {
        return Math.round((percentSpeed / 100) * this.getH());
    }

    /**
     * Getters
     */
    public Integer getW() {
        return metrics.widthPixels;
    }

    public Integer getH() {
        return metrics.heightPixels;
    }

    private Integer getHalfWidth() {
        return metrics.widthPixels / 2;
    }

    private Integer getHalfHeight() {
        return metrics.heightPixels / 2;
    }

}
