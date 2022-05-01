package com.orionsoft.vsafe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;

public class SplashActivity extends AppCompatActivity {

//        -----------------------------------------------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        -----------------------------------------------------------------------------------------------

//        Hide the status bar and make the activity fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

//        -----------------------------------------------------------------------------------------------

//        Change the activity after 2 seconds
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences settings = getSharedPreferences("AppStatus", 0);

                if (settings.getBoolean("firstTime", true)) {
                    // The app is being launched for first time, do something
                    Intent i = new Intent(SplashActivity.this, ExploreActivity.class);
                    startActivity(i);
                    finish();

                    // Record the fact that the app has been started at least once
                    settings.edit().putBoolean("firstTime", false).commit();
                } else {
                    Intent i = new Intent(SplashActivity.this, LoginWithMobActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        }, 1500); // Post back to the main Thread after 2000 Millis (2 seconds)
    }
}