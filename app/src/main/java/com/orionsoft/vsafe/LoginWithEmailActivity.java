package com.orionsoft.vsafe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

public class LoginWithEmailActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edTxtEmail;
    private EditText edTxtEmailVerify;

    private TextView txtMobNumLogin;
    private TextView txtAccntReg2;

//        -----------------------------------------------------------------------------------------------

    //    Activity wide interface - onClick() method
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txtMobNumLogin:
                Intent intent1 = new Intent(this, LoginWithMobActivity.class);
                startActivity(intent1);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                break;
            case R.id.txtAccntReg2:
                Intent intent2 = new Intent(this, RegistrationActivity.class);
                startActivity(intent2);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                break;
            default:
                break;
        }
    }

//        -----------------------------------------------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_with_email);

//        -----------------------------------------------------------------------------------------------

        //        Instantiate the setOnClickListener(s) at runtime
        txtMobNumLogin = findViewById(R.id.txtMobNumLogin);
        txtMobNumLogin.setOnClickListener(this);

        txtAccntReg2 = findViewById(R.id.txtAccntReg2);
        txtAccntReg2.setOnClickListener(this);

//        -----------------------------------------------------------------------------------------------

//        Remove focus from edTxtEmail when the editing is done
        edTxtEmail = findViewById(R.id.edTxtEmail);

        edTxtEmail.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getWindow().getDecorView().getRootView().getWindowToken(), 0);
                    edTxtEmail.setFocusable(false);
                    edTxtEmail.setFocusableInTouchMode(true);
                    return true;
                } else {
                    return false;
                }
            }
        });

        //        Remove focus from edTxtEmailVerify when the editing is done
        edTxtEmailVerify = findViewById(R.id.edTxtEmailVerify);

        edTxtEmailVerify.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getWindow().getDecorView().getRootView().getWindowToken(), 0);
                    edTxtEmailVerify.setFocusable(false);
                    edTxtEmailVerify.setFocusableInTouchMode(true);
                    return true;
                } else {
                    return false;
                }
            }
        });
    }

//        -----------------------------------------------------------------------------------------------

    @Override
    public void onBackPressed() {
        finishAffinity(); // Removes the connection of the existing activity to its stack
        finish(); // The method onDestroy() is executed & exit the application
    }
}