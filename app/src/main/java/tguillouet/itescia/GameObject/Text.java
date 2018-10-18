package tguillouet.itescia.GameObject;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class Text extends Asset {
    private String text;

    public Text(String text, Integer posX, Integer posY, Integer fontSize, Context appContext) {
        super(appContext, posX, posY, fontSize);
        this.text = text;
    }

    @Override
    public void render(Canvas canvas) {
        canvas.drawText(this.text, this.getXPos(), this.getYPos(), this.getPaint());
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
