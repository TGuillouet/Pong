package tguillouet.itescia.Menu;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

import tguillouet.itescia.GameObject.Asset;
import tguillouet.itescia.GameObject.Button;
import tguillouet.itescia.GameObject.Text;

public class MenuView extends View implements View.OnTouchListener {

    private Text titleText;
    private Button btnStart;

    private Paint paint = new Paint();

    private DisplayMetrics metrics;

    public MenuView(Context context) {
        super(context);

        this.setBackgroundColor(Color.BLACK);

        this.setOnTouchListener(this);

        metrics = new DisplayMetrics();
        ((android.view.WindowManager)context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(metrics);

        paint.setColor(Color.WHITE);

        initAssets(context);
    }

    private void initAssets(Context context) {
        titleText = new Text("Pong", getHalfWidth() - 200, 300, 300, context);

        btnStart = new Button("2 players", this.getHalfWidth(), this.getHalfHeight() + 150, 150, 60, 100, context);
        btnStart.setXPos(this.getHalfWidth() - Asset.getTextWidth(btnStart, btnStart.getPaint()) / 2);
    }

    @Override public void onDraw(Canvas canvas) {
        btnStart.render(canvas);
        drawText(canvas);
    }

    private void drawText(Canvas canvas) {
        canvas.save();
        canvas.rotate(-20, getHalfWidth(), 300);
        titleText.setXPos(this.getHalfWidth() - Asset.getTextWidth(titleText, titleText.getPaint()) / 2);
        titleText.render(canvas);
        canvas.restore();
    }

    public Integer getW() {
        return metrics.widthPixels;
    }

    public Integer getH() {
        return metrics.heightPixels;
    }

    private Integer getHalfWidth() {
        return metrics.widthPixels / 2;
    }

    private Integer getHalfHeight() {
        return metrics.heightPixels / 2;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch(event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                if (btnStart.getTouch(event)) MenuActivity.toGame(getContext());
                break;
        }
        return false;
    }
}
