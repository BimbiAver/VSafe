package com.orionsoft.vsafe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.orionsoft.vsafe.model.Case;
import com.orionsoft.vsafe.model.Department;
import com.orionsoft.vsafe.model.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ReportCaseDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    String caseCheckMsg = "";

    private String dateTime;
    private String tempDeparts = "";
    private String tempLatitude = "";
    private String tempLongitude = "";

    private String imgCheck;
    private String frontImage = "";
    private String backImage = "";

    private EditText edTxtRCDetailDate;
    private EditText edTxtRCDetailSituation;
    private EditText edTxtRCDetailDepart;
    private EditText edTxtRCDetailDetails;
    private EditText edTxtRCDetailLocation;

    private Button btnRCDetailFImg;
    private Button btnRCDetailBImg;
    private Button btnRCDetailSubmit;

    Case aCase;
    Department department;
    User user;

    ProgressDialog progressDialog;

    RequestQueue queue; // Volley RequestQueue
    StringRequest stringRequest; // Volley StringRequest

    // Initializing FusedLocationProviderClient object
    FusedLocationProviderClient mFusedLocationClient;
    int PERMISSION_ID = 44;

    private int PICK_IMAGE_REQUEST = 1;
    private Bitmap bitmapImage = null;
    private Uri imagePath;

//        -----------------------------------------------------------------------------------------------

    //    Activity wide interface - onClick() method
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnRCDetailFImg:
                imgCheck = "fImage";
                imageChooser();
                break;
            case R.id.btnRCDetailBImg:
                imgCheck = "bImage";
                imageChooser();
                break;
            case R.id.btnRCDetailSubmit:
                addCase(); // Add case
                progressDialog.show();

                if (frontImage.equals("") && backImage.equals("")) {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            progressDialog.dismiss();
                            if (caseCheckMsg.equals("Case Added Successfully!")) {
                                Toast.makeText(ReportCaseDetailsActivity.this, "Case Added Successfully!", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(getApplicationContext(), DashboardActivity.class);
                                startActivity(i);
                                finish();
                            } else {
                                Toast.makeText(ReportCaseDetailsActivity.this, "Something unexpected happened!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, 3500);
                } else {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            progressDialog.dismiss();
                            if (caseCheckMsg.equals("Case Added Successfully!")) {
                                Toast.makeText(ReportCaseDetailsActivity.this, "Case Added Successfully!", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(getApplicationContext(), DashboardActivity.class);
                                startActivity(i);
                                finish();
                            } else {
                                Toast.makeText(ReportCaseDetailsActivity.this, "Something unexpected happened!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, 7000);
                }

                break;
            default:
                break;
        }
    }

//        -----------------------------------------------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_case_details);

        edTxtRCDetailDate = findViewById(R.id.edTxtRCDetailDate);
        edTxtRCDetailSituation = findViewById(R.id.edTxtRCDetailSituation);
        edTxtRCDetailDepart = findViewById(R.id.edTxtRCDetailDepart);
        edTxtRCDetailDetails = findViewById(R.id.edTxtRCDetailDetails);
        edTxtRCDetailLocation = findViewById(R.id.edTxtRCDetailLocation);

        btnRCDetailFImg = findViewById(R.id.btnRCDetailFImg);
        btnRCDetailBImg = findViewById(R.id.btnRCDetailBImg);
        btnRCDetailSubmit = findViewById(R.id.btnRCDetailSubmit);

        // Instantiate the setOnClickListener(s) at runtime
        btnRCDetailFImg.setOnClickListener(this);
        btnRCDetailBImg.setOnClickListener(this);
        btnRCDetailSubmit.setOnClickListener(this);

        user = SharedPrefManager.getInstance(this).getUser();

        queue = Volley.newRequestQueue(this); // Instantiate the RequestQueue
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Submitting...");

        // Retrieve objects from the previous activity
        aCase = (Case) getIntent().getSerializableExtra("caseObj");
        department = (Department) getIntent().getSerializableExtra("departObj");

        // The current date and time
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateTime = dateFormat.format(Calendar.getInstance().getTime());

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        // Method to get the location
        getLastLocation();

        setDataOnStartup(); // Update fields with data on startup
    }

//        -----------------------------------------------------------------------------------------------

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

//        -----------------------------------------------------------------------------------------------

    // Update fields with data on startup
    private void setDataOnStartup() {
        edTxtRCDetailDate.setText(dateTime);
        edTxtRCDetailSituation.setText(aCase.getSituation());
        edTxtRCDetailDepart.setText(getSelectedDeparts());

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                edTxtRCDetailLocation.setText(tempLatitude + "," + tempLongitude);
            }
        }, 3000);
    }

//        -----------------------------------------------------------------------------------------------

    // Get selected departments to a string
    private String getSelectedDeparts() {
        if (department.getPolice() == 1) {
            tempDeparts += "Police, ";
        }
        if (department.getHospital() == 1) {
            tempDeparts += "Hospital, ";
        }
        if (department.getFireBrigade() == 1) {
            tempDeparts += "Fire Brigade, ";
        }
        if (department.getDmc() == 1) {
            tempDeparts += "DMC, ";
        }
        if (department.getMwca() == 1) {
            tempDeparts += "MWCA, ";
        }

        return tempDeparts.substring(0, tempDeparts.length() - 2);
    }

//        -----------------------------------------------------------------------------------------------
//        -----------------------------------------------------------------------------------------------
//        -----------------------------------------------------------------------------------------------

    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        // Check if permissions are given
        if (checkPermissions()) {

            // Check if location is enabled
            if (isLocationEnabled()) {

                // Getting last location from FusedLocationClient object
                mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        Location location = task.getResult();
                        if (location == null) {
                            requestNewLocationData();
                        } else {
                            tempLatitude = String.valueOf(location.getLatitude());
                            tempLongitude = String.valueOf(location.getLongitude());
                        }
                    }
                });
            } else {
                Toast.makeText(this, "Please turn on" + " your location...", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            // If permissions aren't available, request for permissions
            requestPermissions();
        }
    }

    @SuppressLint("MissingPermission")
    private void requestNewLocationData() {

        // Initializing LocationRequest object with appropriate methods
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(5);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        // setting LocationRequest
        // on FusedLocationClient
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
    }

    private LocationCallback mLocationCallback = new LocationCallback() {

        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            tempLatitude = String.valueOf(mLastLocation.getLatitude());
            tempLongitude = String.valueOf(mLastLocation.getLongitude());
        }
    };

    // Method to check for permissions
    private boolean checkPermissions() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;

        // If we want background location on Android 10.0 and higher, use: ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    // Method to request for permissions
    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ID);
    }

    // Method to check if location is enabled
    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    // If everything is alright then
    @Override
    public void
    onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (checkPermissions()) {
            getLastLocation();
        }
    }

//        -----------------------------------------------------------------------------------------------
//        -----------------------------------------------------------------------------------------------
//        -----------------------------------------------------------------------------------------------

    private void imageChooser() {
//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
        Intent i = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(i, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            imagePath = data.getData();
            try {
                bitmapImage = MediaStore.Images.Media.getBitmap(getContentResolver(), imagePath);

                if (imgCheck.equals("fImage")) {
                    frontImage = getStringImage(bitmapImage);
                    btnRCDetailFImg.setText("front-image.png");
                } else if (imgCheck.equals("bImage")) {
                    backImage = getStringImage(bitmapImage);
                    btnRCDetailBImg.setText("back-image.png");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    //        -----------------------------------------------------------------------------------------------
    //        -----------------------------------------------------------------------------------------------
    //        -----------------------------------------------------------------------------------------------

    // Add Case
    private void addCase() {
        stringRequest = new StringRequest(Request.Method.POST, URLs.addCase,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            caseCheckMsg = jsonObject.getString("message");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("nic", user.getNICNumber());
                params.put("situation", aCase.getSituation());
                params.put("details", edTxtRCDetailDetails.getText().toString());
                params.put("dateTime", edTxtRCDetailDate.getText().toString());
                params.put("location", edTxtRCDetailLocation.getText().toString());
                params.put("frontImg", frontImage);
                params.put("backImg", backImage);
                params.put("police", String.valueOf(department.getPolice()));
                params.put("hospital", String.valueOf(department.getHospital()));
                params.put("fireBrigade", String.valueOf(department.getFireBrigade()));
                params.put("dmc", String.valueOf(department.getDmc()));
                params.put("mwca", String.valueOf(department.getMwca()));

                return params;
            }
        };
        queue.add(stringRequest);
    }
}