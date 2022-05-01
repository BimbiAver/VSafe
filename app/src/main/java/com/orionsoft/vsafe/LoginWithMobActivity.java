package com.orionsoft.vsafe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class LoginWithMobActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edTxtMobNumber;
    private EditText edTxtMobVerify;

    private TextView txtMobOTPMsg;
    private TextView txtEmailLogin;
    private TextView txtAccntReg1;

    private Button btnMobNumLogin;

    GenerateVerificationCode generateVerificationCode = new GenerateVerificationCode();

//        -----------------------------------------------------------------------------------------------

    //    Activity wide interface - onClick() method
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnMobNumLogin:
                String btnText = btnMobNumLogin.getText().toString();
                String mobNumber = edTxtMobNumber.getText().toString().substring(1);
                String mobVerify = edTxtMobVerify.getText().toString();
                if (mobNumber.isEmpty()) {
                    edTxtMobNumber.setError("Please enter your mobile number!");
                } else if (btnText.equals("Next")) {
//                    String verificationCode = generateVerificationCode.generateCode();
//                    // Instantiate the RequestQueue
//                    RequestQueue queue = Volley.newRequestQueue(this);
//                    String url = "https://app.vsafe.care/send-sms/send_sms.php?to=" + mobNumber + "&msg=Your VSafe verification code is: " + verificationCode;
//
//                    // Request a string response from the provided URL
//                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
//                            new Response.Listener<String>() {
//                                @Override
//                                public void onResponse(String response) {
//                                    // Display the first 500 characters of the response string
//                                }
//                            }, new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//                        }
//                    });
//
//                    // Add the request to the RequestQueue.
//                    queue.add(stringRequest);

                    txtMobOTPMsg.setText("Enter the OTP sent to +94" + mobNumber.substring(1));
                    txtMobOTPMsg.setVisibility(View.VISIBLE);
                    edTxtMobVerify.setVisibility(View.VISIBLE);
                    edTxtMobVerify.requestFocus();
                    btnMobNumLogin.setText("Login");
                } else {
                    if (mobVerify.isEmpty()) {
                        edTxtMobVerify.setError("Please enter the verification code!");
                    } else {

                    }
                }
                break;
            case R.id.txtEmailLogin:
                Intent intent2 = new Intent(this, LoginWithEmailActivity.class);
                startActivity(intent2);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                break;
            case R.id.txtAccntReg1:
                Intent intent3 = new Intent(this, RegistrationActivity.class);
                startActivity(intent3);
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

        txtMobOTPMsg = findViewById(R.id.txtMobOTPMsg);

//        -----------------------------------------------------------------------------------------------

        //        Instantiate the setOnClickListener(s) at runtime
        txtEmailLogin = findViewById(R.id.txtEmailLogin);
        txtEmailLogin.setOnClickListener(this);

        txtAccntReg1 = findViewById(R.id.txtAccntReg1);
        txtAccntReg1.setOnClickListener(this);

        btnMobNumLogin = findViewById(R.id.btnMobNumLogin);
        btnMobNumLogin.setOnClickListener(this);

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