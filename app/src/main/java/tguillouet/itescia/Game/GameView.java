package tguillouet.itescia.Game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

import java.sql.SQLOutput;

import tguillouet.itescia.GameObject.Ball;
import tguillouet.itescia.GameObject.Player;
import tguillouet.itescia.GameObject.Text;

public class GameView extends View implements View.OnTouchListener {

    public Boolean gamePaused = false;

    private Player player1;
    private Text player1Score;

    private Player player2;
    private Text player2Score;

    private Ball ball;

    private Paint p = new Paint();

    private Text winText;

    private DisplayMetrics metrics;

    public GameView(Context context) {
        super(context);

        this.setBackgroundColor(Color.BLACK);

        metrics = new DisplayMetrics();
        ((android.view.WindowManager)context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(metrics);

        this.setOnTouchListener(this);

        p.setColor(Color.WHITE);

        initAssets(context);
    }

    private void initAssets(Context context) {
        /* Init players */
        player1 = new Player("Player 1", 40, 200, 300, this.getHalfHeight(), p);
        player1Score = new Text("0", this.getHalfWidth() - 200, 120, 200, context);

        player2 = new Player("Player 1",40, 200, this.getW() - 300, this.getHalfHeight(), p);
        player2Score = new Text("0", this.getHalfWidth() + 100, 120, 200, context);

        /* Init ball */
        ball = new Ball(40,40, 350, this.getHalfHeight() - 20, p);
        ball.setDirection(1, 1);


        winText = new Text("", this.getHalfWidth() / 2, this.getHalfHeight(), 160, context);

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        /* Ressource https://gamedev.stackexchange.com/questions/56271/android-multitouch-how-to-detect-movement-on-non-primary-pointer-finger */
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                if (!gamePaused) updatePlayers(event);
                else {
                    unpauseGame();
                    resetGame();
                }
                break;
        }
        return true;
    }

    /*
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

    private void unpauseGame() {
        this.gamePaused = false;
    }

    private void resetGame() {
        player1.setScore(0);
        player2.setScore(0);
        resetBall(player1, "right");
        resetPlayersPos();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (player1.getScore() == 1 || player2.getScore() == 10) {
            String playerName = (player1.getScore() == 1)? player1.getName(): player2.getName();
            winText.setText(playerName + " win the game !");
            winText.render(canvas);
            this.gamePaused = true;
        }

        if (!gamePaused) {
            player1.render(canvas);
            player2.render(canvas);
            ball.render(canvas);
            player1Score.render(canvas);
            player2Score.render(canvas);
            checkCollisions();
            invalidate();
        }
    }

    private void checkCollisions() {
        if ((ball.getCenter()[1] >= this.getH()) || (ball.getCenter()[1] <= 0)) {
            ball.setDirection(ball.getDirection()[0], ball.getDirection()[1] * -1); /* We only need to invert Y axis */
        } else if (ball.getCenter()[0] >= this.getW()) {
            incrementScore(player1, player1Score);
            resetBall(player2, "left");
            resetPlayersPos();
        } else if (ball.getCenter()[0] <= 0) {
            incrementScore(player2, player2Score);
            resetBall(player1, "right");
            resetPlayersPos();
        }else if (ball.collide(player1) || ball.collide(player2)) {
            ball.setDirection(ball.getDirection()[0] * -1, ball.getDirection()[1]); /* We only need to invert X axis */
        }
    }

    /**
     *
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
        Integer offset = (!orientation.equals("right"))? ((!orientation.equals("left"))? 50: -50): 50;
        ball.setXPos(player.getXPos() + offset);
        ball.setYPos(this.getHalfHeight() - (ball.getAssetWidth() / 2));
    }

    /**
     * Reset the players positions
     */
    private void resetPlayersPos() {
        player1.resetPosition();
        player2.resetPosition();
    }

    /* Getters */
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
