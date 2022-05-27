package com.orionsoft.vsafe;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.orionsoft.vsafe.model.Case;
import com.orionsoft.vsafe.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PreviousCasesActivity extends AppCompatActivity {

    ProgressDialog progressDialog;

    RequestQueue queue; // Volley RequestQueue
    StringRequest stringRequest; // Volley StringRequest
    JSONObject jsonObject;

    User user;

    ListView listView;
    List<Case> caseDetailsList; // The caseDetailsList where we will store all the case objects after parsing the JSON

//        -----------------------------------------------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_cases);

        queue = Volley.newRequestQueue(this); // Instantiate the RequestQueue
        user = SharedPrefManager.getInstance(this).getUser();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");

        // Initializing listview and caseDetailsList
        listView = (ListView) findViewById(R.id.listViewCases);
        caseDetailsList = new ArrayList<>();

        // Fetch case details from the database
        getPreCases(user.getNICNumber());

        progressDialog.show(); // Show ProgressDialog

        // Fetch and load data to the ListView
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                progressDialog.dismiss();

                if (!(jsonObject == null)) {
                    loadPreCasesList(); // Fetch and load data to the ListView
                }
            }
        }, 3000);
    }

//        -----------------------------------------------------------------------------------------------

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

//        -----------------------------------------------------------------------------------------------

    // Get previous cases
    private void getPreCases(String nic) {

        stringRequest = new StringRequest(Request.Method.POST, URLs.getPreCases,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        try {
                            jsonObject = new JSONObject(response);


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
                params.put("nic", nic);

                return params;
            }
        };
        queue.add(stringRequest);
    }

//        -----------------------------------------------------------------------------------------------

    private void loadPreCasesList() {
        try {
            // We have the array inside the JSON object
            // So here we are getting that JSON array
            JSONArray casesArray = jsonObject.getJSONArray("Cases");
            // Now looping through all the elements of the JSON array
            for (int i = 0; i < casesArray.length(); i++) {
                // Getting the JSON object of the particular index inside the array
                JSONObject caseObject = casesArray.getJSONObject(i);
                // Creating a cases object and giving them the values from json object
                Case aCase = new Case(caseObject.getString("id"), caseObject.getString("situation"), caseObject.getString("details"), caseObject.getString("date_time"), caseObject.getString("location"), caseObject.getInt("status"));
                // Adding the cases to caseDetailsList
                caseDetailsList.add(aCase);
            }
            // Creating custom adapter object
            CaseListViewAdapter caseListViewAdapter = new CaseListViewAdapter(caseDetailsList, getApplicationContext());
            // Adding the adapter to the ListView
            listView.setAdapter(caseListViewAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}