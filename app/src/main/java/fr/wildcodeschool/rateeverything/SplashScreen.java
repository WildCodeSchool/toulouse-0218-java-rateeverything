package fr.wildcodeschool.rateeverything;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreen extends AppCompatActivity {
    private static int SPLASH_TIME_OUT=3000;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent iScreen = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(iScreen);
                finish();
            }
        }, SPLASH_TIME_OUT);


    }
}
