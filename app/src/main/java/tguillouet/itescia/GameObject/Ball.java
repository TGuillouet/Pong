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
        this.setXPos(this.getXPos() + directionX);
        this.setYPos(this.getYPos() + directionY);
        super.render(canvas);

    }

    public void setDirection(Integer dirX, Integer dirY) {
        this.directionX = dirX * speed;
        this.directionY = dirY * speed;
    }

    public Boolean collide(Player player) {
        return this.getRect().intersect(player.getRect());
    }

    public Integer[] getCenter() {
        return new Integer[]{getRect().centerX(), getRect().centerY()};
    }

    public Integer[] getDirection() {
        return new Integer[] { directionX / speed, directionY / speed };
    }
}
