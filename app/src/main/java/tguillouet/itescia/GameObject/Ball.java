package tguillouet.itescia.GameObject;

import android.graphics.Canvas;
import android.graphics.Paint;

import static tguillouet.itescia.GameObject.BufferZone.*;
import static tguillouet.itescia.GameObject.BufferZone.Buff.*;

public class Ball extends Asset {

    private Integer directionX;
    private Integer directionY;
    private Integer speed = 15;

    private Boolean isBuffed = false;
    private Buff buffType = NONE;

    private Integer exchangeCount = 0;

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
        Boolean b = this.getRect().intersect(player.getRect());

        if (b) {
            this.exchangeCount += 1;

            if (this.isBuffed) {
                switch (this.buffType) {
                    case SPEED:
                        this.setSpeed(15);
                        this.setBuffType(Buff.NONE);
                        break;
                    default:
                        break;
                }
            }
        }


        return b;
    }

    public Integer[] getCenter() {
        return new Integer[]{getRect().centerX(), getRect().centerY()};
    }

    public Integer[] getDirection() {
        return new Integer[] { directionX, directionY };
    }

    public Integer getSpeed() {
        return this.speed;
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
    }

    public void setExchangeCount(Integer exchangeCount) {
        this.exchangeCount = exchangeCount;
    }

    public Integer getExchangeCount() {
        return exchangeCount;
    }

    public Boolean getBuffed() {
        return isBuffed;
    }

    public void setBuffed(Boolean buffed) {
        isBuffed = buffed;
    }

    public Buff getBuffType() {
        return buffType;
    }

    public void setBuffType(BufferZone buffzone) {
        Buff buff = null;
        if (buffzone instanceof BufferSpeed) {
            buff = ((BufferSpeed) buffzone).getBuffType();
        }
        this.buffType = buff;
    }

    public void setBuffType(Buff buff) {
        this.buffType = buff;
    }
}
