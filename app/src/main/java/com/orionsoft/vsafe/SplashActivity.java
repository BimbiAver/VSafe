package com.orionsoft.vsafe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

public class SplashActivity extends AppCompatActivity {

    private final String sharedPref = "AppStatus";

    CheckNetwork checkNetwork = new CheckNetwork();

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

//        Check to make sure it is connected to a network
        if (checkNetwork.isNetworkConnected(this) == true) {
//        Change the activity after 2 seconds
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    SharedPreferences settings = getSharedPreferences(sharedPref, 0);

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
        } else {
            Toast.makeText(this, "Check your internet connection and try again!", Toast.LENGTH_SHORT).show();

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    finishAffinity(); // Removes the connection of the existing activity to its stack
                    finish(); // The method onDestroy() is executed & exit the application
                }
            }, 2000);
        }
    }
}