package tguillouet.itescia.Game;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

/*
* To setup fullscreen
* https://stackoverflow.com/a/2868052
*/

public class TwoPlayersGameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GameView game = new GameView(getApplicationContext());

        /* Set screen orientation */
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        /* Set Fullscreen */
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        /* The Action Bar / Title Bar is disabled in the styles.xml */
        setContentView(game);

    }

}
