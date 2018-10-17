package tguillouet.itescia.Menu;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import tguillouet.itescia.GameObject.Text;

public class MenuView extends View {

    private Text titleText;

    private Paint paint = new Paint();

    private DisplayMetrics metrics;

    public MenuView(Context context) {
        super(context);

        this.setBackgroundColor(Color.BLACK);

        metrics = new DisplayMetrics();
        ((android.view.WindowManager)context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(metrics);

        paint.setColor(Color.WHITE);

        initAssets(context);
    }

    private void initAssets(Context context) {
        titleText = new Text("Pong", getHalfWidth() - 200, 300, 300, context);
    }

    @Override public void onDraw(Canvas canvas) {
        drawText(canvas);
    }

    private void drawText(Canvas canvas) {
        canvas.save();
        canvas.rotate(-20, getHalfWidth(), 300);
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
}
