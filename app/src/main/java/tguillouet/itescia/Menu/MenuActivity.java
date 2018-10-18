package tguillouet.itescia.Menu;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import tguillouet.itescia.Game.GameView;
import tguillouet.itescia.Game.TwoPlayersGameActivity;

public class MenuActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MenuView game = new MenuView(getApplicationContext());

        /* Set screen orientation */
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        /* Set Fullscreen */
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        /* The Action Bar / Title Bar is disabled in the styles.xml */
        setContentView(game);
    }

    public static void toGame(Context context) {
        context.startActivity(new Intent(context, TwoPlayersGameActivity.class));
    }
}
