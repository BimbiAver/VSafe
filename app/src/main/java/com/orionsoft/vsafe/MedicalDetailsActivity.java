package com.orionsoft.vsafe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MedicalDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnMedAdd;

//        -----------------------------------------------------------------------------------------------

    //    Activity wide interface - onClick() method
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnMedAdd:
                Intent intent = new Intent(this, AddMedicalActivity.class);
                startActivity(intent);
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
        setContentView(R.layout.activity_medical_details);

        btnMedAdd = findViewById(R.id.btnMedAdd);

        // Instantiate the setOnClickListener(s) at runtime
        btnMedAdd.setOnClickListener(this);
    }

//        -----------------------------------------------------------------------------------------------

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }
}