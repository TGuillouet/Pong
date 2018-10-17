package tguillouet.itescia.GameObject;

import android.graphics.Paint;

public class Player extends Asset {
    private Integer score = 0;

    private String name;

    public Player(String name, Integer w, Integer h, Integer posX, Integer posY, Paint paint) {
        super(w, h, posX, posY, paint);
        this.name = name;
    }

    public void resetPosition() {
        this.setYPos(this.getBasePosY());
    }

    public String getName() {
        return name;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
