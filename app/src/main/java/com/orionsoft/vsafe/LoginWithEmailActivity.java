package com.orionsoft.vsafe;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class LoginWithEmailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_with_email);
    }

    @Override
    public void onBackPressed() {
        finishAffinity(); // Removes the connection of the existing activity to its stack
        finish(); // The method onDestroy() is executed & exit the application
    }
}