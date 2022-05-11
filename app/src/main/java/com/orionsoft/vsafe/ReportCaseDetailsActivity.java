package com.orionsoft.vsafe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.orionsoft.vsafe.model.Case;
import com.orionsoft.vsafe.model.Department;

public class ReportCaseDetailsActivity extends AppCompatActivity {

    Case aCase;
    Department department;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_case_details);

        // Retrieve objects from the previous activity
        aCase = (Case) getIntent().getSerializableExtra("caseObj");
        department = (Department) getIntent().getSerializableExtra("departObj");

        String as = String.valueOf(department.getHospital());
        Toast.makeText(this, as, Toast.LENGTH_SHORT).show();
    }

//        -----------------------------------------------------------------------------------------------

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }
}