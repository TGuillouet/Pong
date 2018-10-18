package tguillouet.itescia.GameObject;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Ball extends Asset {
    private Integer directionX;
    private Integer directionY;
    private Integer speed = 15;

    public Ball(Integer w, Integer h, Integer posX, Integer posY, Paint paint) {
        super(w, h, posX, posY, paint);
    }

    @Override
    public void render(Canvas canvas) {
        this.setXPos(this.getXPos() + directionX * speed);
        this.setYPos(this.getYPos() + directionY * speed);
        super.render(canvas);

    }

    public void setDirection(Integer dirX, Integer dirY) {
        this.directionX = dirX;
        this.directionY = dirY;
    }

    public Boolean collide(Player player) {
        return this.getRect().intersect(player.getRect());
    }

    public Integer[] getCenter() {
        return new Integer[]{getRect().centerX(), getRect().centerY()};
    }

    public Integer[] getDirection() {
        return new Integer[] { directionX, directionY };
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
    }
}
