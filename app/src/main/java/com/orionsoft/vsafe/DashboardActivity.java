package com.orionsoft.vsafe;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Redirect the user to the Login activity, if the user is not logged in
        if(!SharedPrefManager.getInstance(this).isLoggedIn()){
            Intent intent = new Intent(DashboardActivity.this,LoginWithMobActivity.class);
            startActivity(intent);
            finishAffinity(); // Removes the connection of the existing activity to its stack
            finish(); // The method onDestroy() is executed & exit the application
        }
    }

//        -----------------------------------------------------------------------------------------------

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(R.drawable.ic_exit)
                .setTitle("Confirm Exit")
                .setMessage("Are you sure you want to exit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finishAffinity(); // Removes the connection of the existing activity to its stack
                        finish(); // The method onDestroy() is executed & exit the application
                    }

                })
                .setNegativeButton("No", null).show();
    }
}