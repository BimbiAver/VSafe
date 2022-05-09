package com.orionsoft.vsafe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.orionsoft.vsafe.model.Guardian;
import com.orionsoft.vsafe.model.User;

import java.io.Serializable;

public class EditProfileActivity extends AppCompatActivity {

    User user;
    Guardian guardian;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        // Retrieve objects from the previous activity
        user = (User) getIntent().getSerializableExtra("userObj");
        guardian = (Guardian) getIntent().getSerializableExtra("guardObj");

    }

//        -----------------------------------------------------------------------------------------------

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }
}