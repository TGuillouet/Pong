package tguillouet.itescia.GameObject;

import android.graphics.Paint;

public class BufferZone extends Asset {

    public enum Buff {
        NONE,
        SPEED
    }

    public BufferZone(Integer xPos, Integer yPos, Integer size, Paint paint) {
        super(xPos, yPos, size, paint);
    }
}
