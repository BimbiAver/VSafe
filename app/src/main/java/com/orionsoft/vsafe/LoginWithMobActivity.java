package com.orionsoft.vsafe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class LoginWithMobActivity extends AppCompatActivity {

    private EditText edTxtMobNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_with_mob);

        edTxtMobNumber = findViewById(R.id.edTxtMobNumber);

//        Remove focus from EditTexts when the editing is done
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
    }

    @Override
    public void onBackPressed() {
        finishAffinity(); // Removes the connection of the existing activity to its stack
        finish(); // The method onDestroy() is executed & exit the application
    }

}