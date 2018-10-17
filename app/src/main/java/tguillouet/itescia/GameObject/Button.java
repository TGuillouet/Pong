package tguillouet.itescia.GameObject;

import android.content.Context;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

public class Button extends Asset /*implements View.OnTouchListener */ implements View.OnClickListener {

    private String btnText;

    public Button(String text, Integer posX, Integer posY, Integer fontSize, Context appContext) {
        super(appContext, posX, posY, fontSize);
        this.btnText = text;
    }

    public Button(Integer w, Integer h, Integer posX, Integer posY, Paint paint) {
        super(w, h, posX, posY, paint);
    }

    /*@Override
    public boolean onTouch(View v, MotionEvent event) {
        switch(event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:

                break;
        }
        return false;
    }*/

    @Override
    public void onClick(View v) {
        System.out.println("Btn clicked: " + getBtnText());
    }

    public String getBtnText() {
        return btnText;
    }

    public void setBtnText(String btnText) {
        this.btnText = btnText;
    }
}
