package tguillouet.itescia.GameObject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.widget.ImageView;

@SuppressLint("AppCompatCustomView")
public class Asset {

    private Integer w = 0;
    private Integer h = 0;
    private Integer xPos = 0;
    private Integer yPos = 0;
    private final Integer basePosX;
    private final Integer basePosY;

    private Rect rect;
    private Paint paint;

    public Asset(Context appContext, Integer posX, Integer posY, Integer fontSize) {
        this.xPos = posX;
        this.yPos = posY;
        this.basePosX = posX;
        this.basePosY = posY;

        paint = new Paint();
        paint.setTextSize(fontSize);
        paint.setColor(Color.WHITE);
        paint.setTypeface(Typeface.createFromAsset(appContext.getAssets(), "fonts/Cave-Story.ttf"));
    }

    public Asset(Integer w, Integer h, Integer posX, Integer posY, Paint paint) {
        this.w = w;
        this.h = h;
        this.xPos = posX;
        this.yPos = posY;
        this.basePosX = posX;
        this.basePosY = posY;
        this.paint = paint;

        init();
    }

    private void init() {
        rect = new Rect(xPos - (w/2), yPos - (h/2), xPos + (w/2), yPos + (h/2));
    }

    public void render(Canvas canvas) {
        canvas.drawRect(getRect(), this.getPaint());
    }

    public Rect getRect() {
        return rect;
    }

    public void setRect(Rect rect) {
        this.rect = rect;
    }

    public Integer getAssetWidth() {
        return w;
    }

    public void setAssetWidth(Integer width) {
        this.w = w;
    }

    public Integer getAssetHeigth() {
        return h;
    }

    public void setAssetHeigth(Integer h) {
        this.h = h;
    }

    public Integer getXPos() {
        return xPos;
    }

    public void setXPos(Integer xPos) {
        this.xPos = xPos;
    }

    public Integer getYPos() {
        return yPos;
    }

    public void setYPos(Integer yPos) {
        this.yPos = yPos;
        rect.set(xPos - (w/2), yPos - (h/2), xPos + (w/2), yPos + (h/2));
    }

    public Integer getBasePosX() {
        return basePosX;
    }

    public Integer getBasePosY() {
        return basePosY;
    }

    public Paint getPaint() {
        return paint;
    }
}
