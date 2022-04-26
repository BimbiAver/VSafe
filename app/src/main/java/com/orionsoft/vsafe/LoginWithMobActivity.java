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

public class LoginWithMobActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edTxtMobNumber;
    private EditText edTxtMobVerify;

    private TextView txtEmailLogin;
    private TextView txtAccntReg1;

//        -----------------------------------------------------------------------------------------------

    //    Activity wide interface - onClick() method
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txtEmailLogin:
                Intent intent1 = new Intent(this, LoginWithEmailActivity.class);
                startActivity(intent1);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                break;
            case R.id.txtAccntReg1:
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
        setContentView(R.layout.activity_login_with_mob);

//        -----------------------------------------------------------------------------------------------

        //        Instantiate the setOnClickListener(s) at runtime
        txtEmailLogin = findViewById(R.id.txtEmailLogin);
        txtEmailLogin.setOnClickListener(this);

        txtAccntReg1 = findViewById(R.id.txtAccntReg1);
        txtAccntReg1.setOnClickListener(this);

//        -----------------------------------------------------------------------------------------------

//        Remove focus from edTxtMobNumber when the editing is done
        edTxtMobNumber = findViewById(R.id.edTxtMobNumber);

        edTxtMobNumber.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getWindow().getDecorView().getRootView().getWindowToken(), 0);
                    edTxtMobNumber.setFocusable(false);
                    edTxtMobNumber.setFocusableInTouchMode(true);
                    return true;
                } else {
                    return false;
                }
            }
        });

        //        Remove focus from edTxtMobVerify when the editing is done
        edTxtMobVerify = findViewById(R.id.edTxtMobVerify);

        edTxtMobVerify.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getWindow().getDecorView().getRootView().getWindowToken(), 0);
                    edTxtMobVerify.setFocusable(false);
                    edTxtMobVerify.setFocusableInTouchMode(true);
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