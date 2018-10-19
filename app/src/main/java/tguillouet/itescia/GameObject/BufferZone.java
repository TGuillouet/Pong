package tguillouet.itescia.GameObject;

import android.graphics.Paint;

public class BufferZone extends Asset {

    public enum Buff {
        NONE,
        SPEED
    }

    private Buff buffType = Buff.NONE;

    public BufferZone(Integer xPos, Integer yPos, Integer size, Paint paint) {
        super(xPos, yPos, size, paint);
    }

    protected void setBuffType(Buff buff) {
        this.buffType = buff;
    }

    public Buff getBuffType() {
        return buffType;
    }
}

