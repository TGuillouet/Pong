package tguillouet.itescia.GameObject;

import android.graphics.Paint;

public class BufferSpeed extends BufferZone {

    public BufferSpeed(Integer xPos, Integer yPos, Integer size, Paint paint) {
        super(xPos, yPos, size, paint);
        this.setBuffType(Buff.SPEED);
    }

    public Boolean buff(Ball ball) {
        if (ball.getRect().intersect(this.getRect())) {
            Integer newSpeed = Math.round(ball.getSpeed() * 1.7f);
            ball.setSpeed(newSpeed);
            ball.setBuffed(true);
            return true;
        }
        return false;
    }

}
