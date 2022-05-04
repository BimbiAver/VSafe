package com.orionsoft.vsafe;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edTxtDOB;

    private TextView txtAccntLogin;

    private Spinner spnBloodGrp;

    final Calendar myCalendar= Calendar.getInstance();
    DatePickerDialog.OnDateSetListener date;

//        -----------------------------------------------------------------------------------------------

    //    Activity wide interface - onClick() method
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.edTxtRegDOB:
                showDatePickerDialog();
                break;
            case R.id.txtAccntLogin:
                finish();
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        // If the user is already logged in, this will redirect the user to the Dashboard
        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, DashboardActivity.class));
        }

//        -----------------------------------------------------------------------------------------------

        // Instantiate the setOnClickListener(s) at runtime
        edTxtDOB = findViewById(R.id.edTxtRegDOB);
        edTxtDOB.setOnClickListener(this);

        txtAccntLogin = findViewById(R.id.txtAccntLogin);
        txtAccntLogin.setOnClickListener(this);
    }

//        -----------------------------------------------------------------------------------------------

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

//        -----------------------------------------------------------------------------------------------

//    View DatePickerDialog
    private void showDatePickerDialog(){
        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                setDate();
            }
        };
        new DatePickerDialog(RegistrationActivity.this,R.style.DatePickerDialog,date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

//    Update editText field with date of birth
    private void setDate(){
        String myFormat="yyyy/MM/dd";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
        edTxtDOB.setText(dateFormat.format(myCalendar.getTime()));
    }
}