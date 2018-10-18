package tguillouet.itescia.GameObject;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;

import tguillouet.itescia.Menu.MenuActivity;

public class Button extends Asset /* implements View.OnTouchListener */ /*implements View.OnClickListener*/ {

    private String btnText;
    private Paint innerPaint;

    private Rect containerRect;
    private Rect innerRect;

    public Button(String text, Integer posX, Integer posY, Integer width, Integer height, Integer fontSize, Context appContext) {
        super(appContext, posX, posY, fontSize, width, height);
        this.btnText = text;

        this.innerPaint = new Paint();
        this.innerPaint.setColor(Color.BLACK);

        Integer textWidth = getTextWidth(this, this.getPaint());
        Integer textHeight = getTextHeight(this, this.getPaint());

        containerRect = new Rect(this.getXPos() - (Math.round(textWidth * 1.2f) / 2),
                                 this.getYPos() - (Math.round(textHeight * 1.7f) / 2),
                                this.getXPos() + (Math.round(textWidth * 1.2f) / 2),
                              this.getYPos() + (Math.round(textHeight * 2.5f) / 2));
//
//        innerRect = new Rect(this.getXPos() - (Math.round(textWidth * 1.1f) / 2),
//                             this.getYPos() - (Math.round(textHeight * 1.2f) / 2),
//                            this.getXPos() + (Math.round(textWidth * 1.1f) / 2),
//                          this.getYPos() + (Math.round(textHeight * 2.0f) / 2));
    }

    public Boolean getTouch(MotionEvent event){
        return containerRect.contains((int) event.getX(), (int) event.getY());
    }

    @Override
    public void render(Canvas canvas) {
        canvas.drawRect(containerRect, this.innerPaint);
//        canvas.drawRect(innerRect, this.innerPaint);
        canvas.drawText(this.btnText, this.getXPos(), this.getYPos() + ((this.getPaint().descent() - this.getPaint().ascent()) / 2), this.getPaint());
    }

    public String getBtnText() {
        return btnText;
    }
}
