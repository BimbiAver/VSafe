package com.orionsoft.vsafe;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.orionsoft.vsafe.model.User;

public class DashboardActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView txtGreet;

    private Button btnLogout;

    User user;

    //    Activity wide interface - onClick() method
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnLogout:
                logout(); // Logout the user
                break;
            default:
                break;
        }
    }

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

        txtGreet = findViewById(R.id.txtGreet);

        // Instantiate the setOnClickListener(s) at runtime
        btnLogout = findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(this);

        user = SharedPrefManager.getInstance(this).getUser();
        txtGreet.setText("Hello, " + user.getFirstName() + " !");
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

    private void logout() {
        new AlertDialog.Builder(this)
                .setIcon(R.drawable.ic_exit)
                .setTitle("Confirm Logout")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPrefManager.getInstance(getApplicationContext()).logout();
                        finishAffinity();
                        finish();
                        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                    }

                })
                .setNegativeButton("No", null).show();
    }
}